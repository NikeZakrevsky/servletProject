package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;


/**
 * Using DAO pattern for operations with @{{@link Project}} objects
 *
 * @author Q-NZA
 */
public class ProjectDaoImpl extends AbstractDao<Project> {

    private static final String SELECT_QUERY = "select project_id, project_name, short_name, description, task_id, task_name," +
        "work_time, start_date, end_date, status, project_id, person_id, first_name, middle_name, last_name, position as person" +
        "from projects left join tasks on tasks.project_id =projects.project_id left join persons on tasks.person_id = persons." +
         "person_id";
    private static final String INSERT_QUERY = "insert into projects(project_name, short_name, description) values (?, ?, ?)";
    private static final String DELETE_QUERY = "delete from projects where project_id=?";
    private static final String SELECT_PROJECT = SELECT_QUERY + " where project_id =?";
    private static final String UPDATE_QUERY = "update projects set project_name = ?, short_name = ?, description = ? where " +
        "project_id = ?";
    
    private static final String ADD_PROJECT_ERROR = "Ошибка при добавлении проекта";
    private static final String REMOVE_PROJECT_ERROR = "Ошибка при удалении проекта";
    private static final String UPDATE_PROJECT_ERROR = "Ошибка при обновлении проекта";
    private static final String GET_PROJECTS_ERROR = "Ошибка при получении списка проектов";
    private static final String GET_PROJECT_ERROR = "Ошибка при получении проекта";

    private static final String ID = "project_id";
    private static final String NAME = "project_name";
    private static final String SHORTNAME = "short_name";
    private static final String DESCRIPTION = "description";

    /**
     * Update information about project in database
     *
     * @param project project data from form
     */
    @Override
    public void update(Project project) {
        super.update(UPDATE_QUERY, UPDATE_PROJECT_ERROR, project.getName(), project.getShortName(), project.getDescription(),
            project.getId());
    }

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     */
    @Override
    public Project get(int id) {
        return super.get(id, SELECT_PROJECT, GET_PROJECT_ERROR);
    }


    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     */
    @Override
    public List<Project> getAll() {
        return super.getAll(SELECT_QUERY, GET_PROJECTS_ERROR);
    }


    /**
     * Remove project from database by id
     *
     * @param id project id
     */
    @Override
    public void remove(int id) {
        super.remove(id, DELETE_QUERY, REMOVE_PROJECT_ERROR);
    }

    @Override
    public void add(Project project) {
        add(project, INSERT_QUERY, ADD_PROJECT_ERROR, project.getName(), project.getShortName(), project.getDescription());
    }

    /**
     *  Create new project with tasks
     *
     *  @param project new project
     */
    public void add(Project project, String insertQuery, String error, Object... parameters) {
        List<Task> tasks = project.getTasks();
        ResultSet generatedKeys = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            setPreparedStatement(preparedStatement, parameters);
            preparedStatement.execute();
            generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            connection.commit();
            ProjectDaoImpl.this.addProjectTasks(tasks, generatedKeys);
        }
        catch (SQLException e) {
            throw new DaoException(ADD_PROJECT_ERROR, e);
        }
        finally {
            closeResultSet(generatedKeys);
            closeConnection(connection);
            closeStatement(preparedStatement);
        }
    }

    private void addProjectTasks(List<Task> tasks, ResultSet resultSet) throws SQLException {
        TaskDaoImpl tasksDAO = new TaskDaoImpl();
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
                task.setProjectId(resultSet.getInt(1));
                tasksDAO.add(task);
            }
        }
    }

    /**
     * Create object from ResutSet
     *
     * @param resultSet resultSet for converting to object
     * @return created project object
     * @throws SQLException throws while getting data from @{{@link ResultSet}}
     */
    @Override
    public Project resultSetAsObject(ResultSet resultSet) throws SQLException {
        try {
            Integer id = resultSet.getInt(ID);
            String name = resultSet.getString(NAME);
            String shortName = resultSet.getString(SHORTNAME);
            String description = resultSet.getString(DESCRIPTION);
            return new Project(id, name, shortName, description);
        }
        catch (SQLException e) {
            throw new DaoException(RESULT_SET_ERROR, e);
        }
    }

    /**
     * Convert the ResultSet to a List of objects
     *
     * @param resultSet @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    @Override
    public List<Project> resultSetToList(ResultSet resultSet) throws SQLException {
        Map<Integer, Project> projects = new HashMap<>();
        TaskDaoImpl taskDao = new TaskDaoImpl();
        while (resultSet.next()) {
            int id = resultSet.getInt(ID);
            Project project = projects.get(id);
            if (project == null) {
                project = resultSetAsObject(resultSet);
                projects.put(id, project);
            }
            List<Task> tasks = project.getTasks();
            if (tasks == null) {
                project.setTasks(new ArrayList<>());
            }
            if (resultSet.getObject("task_id") != null) {
                Task task = taskDao.resultSetAsObject(resultSet);
                project.getTasks().add(task);
            }

        }
        return projects.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
