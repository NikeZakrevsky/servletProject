package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;


/**
 * Using DAO pattern for operations with @{{@link Project}} objects
 *
 * @author Q-NZA
 */
public class ProjectDao extends AbstractDao<Project> {

    private static final String SELECT_QUERY = "select project_id, project_name, short_name, description, task_id, task_name," +
        "work_time, start_date, end_date, status, project_id, person_id, first_name,middle_name, last_name, position as person " +
        "from projects left join tasks on tasks.project_id =projects.project_id left join persons on tasks.person_id = persons." +
         "person_id";
    private static final String INSERT_QUERY = "insert into projects(project_name, short_name, description) values (?, ?, ?)";
    private static final String DELETE_QUERY = "delete from projects where project_id=?";
    private static final String SELECT_PROJECT = SELECT_QUERY + " where project_id =?";
    private static final String UPDATE_QUERY = "update projects set project_name = ?, short_name = ?, description = ? where " +
        "project_id = ?";
    private static final String ID = "project_id";
    private static final String NAME = "project_name";
    private static final String SHORTNAME = "short_name";
    private static final String DESCRIPTION = "description";
    private static final String ADD_ERROR = "Exception while adding entity";
    private static final String TASK_ID = "task_id";

    /**
     * Updates information about project in database
     *
     * @param newProject project data from form
     */
    @Override
    public void update(Project newProject) {
        super.update(UPDATE_QUERY, newProject.getName(), newProject.getShortName(), newProject.getDescription(),
            newProject.getId());
        Project project = get(newProject.getId());
        updateChangedTasks(newProject, project);
    }

    private void updateChangedTasks(Project newProject, Project project) {
        List<Task> resultTasks = newProject.getTasks();
        List<Task> tasksList = project.getTasks();
        Set<Object> tasksSet = tasksList.stream().map(Task::getId).collect(Collectors.toSet());
        Set<Object> resultTasksSet = resultTasks.stream().map(Task::getId).collect(Collectors.toSet());
        TaskDao taskDao = new TaskDao();
        for (Task task : tasksList) {
            if (!resultTasksSet.contains(task.getId())) {
                taskDao.remove(task.getId());
            }
        }
        for (Task resultTask : resultTasks) {
            if (tasksSet.contains(resultTask.getId())) {
                taskDao.update(resultTask);
            }
        }
        for (Task resultTask : resultTasks) {
            if (!tasksSet.contains(resultTask.getId())) {
                taskDao.add(resultTask);
            }
        }
    }

    /**
     * Gets project id
     *
     * @param id project's id
     * @return Project object
     */
    @Override
    public Project get(int id) {
        return super.get(id, SELECT_PROJECT);
    }

    /**
     * Gets all projects from database
     *
     * @return list of all projects from database
     */
    @Override
    public List<Project> getAll() {
        return super.getAll(SELECT_QUERY);
    }


    /**
     * Removes project from database by id
     *
     * @param id project id
     */
    @Override
    public void remove(int id) {
        super.remove(id, DELETE_QUERY);
    }

    @Override
    public void add(Project project) {
        add(project, INSERT_QUERY, project.getName(), project.getShortName(), project.getDescription());
    }

    /**
     *  Creates new project with tasks
     *
     *  @param project new project
     */
    public void add(Project project, Object... parameters) {
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
            ProjectDao.this.addProjectTasks(tasks, generatedKeys);
        }
        catch (SQLException e) {
            throw new DaoException(ADD_ERROR, e);
        }
        finally {
            closeResultSet(generatedKeys);
            closeConnection(connection);
            closeStatement(preparedStatement);
        }
    }

    private void addProjectTasks(List<Task> tasks, ResultSet resultSet) throws SQLException {
        TaskDao tasksDAO = new TaskDao();
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
                task.setProjectId(resultSet.getInt(1));
                tasksDAO.add(task);
            }
        }
    }

    /**
     * Creates object from ResutSet
     *
     * @param resultSet resultSet for converting to object
     * @return created project object
     * @throws SQLException throws while getting data from @{{@link ResultSet}}
     */
    private Project resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        String shortName = resultSet.getString(SHORTNAME);
        String description = resultSet.getString(DESCRIPTION);

        return new Project(id, name, shortName, description);
    }

    /**
     * Converts the ResultSet to a List of objects
     *
     * @param resultSet @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    @Override
    protected List<Project> resultSetToList(ResultSet resultSet) throws SQLException {
        Map<Integer, Project> projects = new HashMap<>();
        TaskDao taskDao = new TaskDao();
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
            if (resultSet.getObject(TASK_ID) != null) {
                Task task = taskDao.resultSetAsObject(resultSet);
                project.getTasks().add(task);
            }

        }

        return projects.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
