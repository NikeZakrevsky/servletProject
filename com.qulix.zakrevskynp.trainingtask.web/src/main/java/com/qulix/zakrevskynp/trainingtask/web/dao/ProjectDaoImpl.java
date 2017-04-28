package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;


/**
 * Using DAO pattern for operations with @{{@link Project}} objects
 *
 * @author Q-NZA
 */
public class ProjectDaoImpl extends AbstractDao<Project> {

    private static final String SELECT_QUERY = "select id, name, short_name, description from projects";
    private static final String INSERT_QUERY = "insert into projects(name, short_name, description) values (?, ?, ?)";
    private static final String DELETE_QUERY = "delete from projects where id=?";
    private static final String SELECT_BY_ID_QUERY = "select id, name, short_name, description from projects where id =?";
    private static final String UPDATE_QUERY = "update projects set name = ?, short_name = ?, description = ? where id = ?";
    private static final String ADD_PROJECT_ERROR = "Ошибка при добавлении проекта";
    private static final String REMOVE_PROJECT_ERROR = "Ошибка при удалении проекта";
    private static final String UPDATE_PROJECT_ERROR = "Ошибка при обновлении проекта";
    private static final String GET_PROJECTS_LIST_ERROR = "Ошибка при получении списка проектов";
    private static final String GET_PROJECT_BY_ID_ERROR = "Ошибка при получении проекта";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORTNAME = "short_name";
    private static final String DESCRIPTION = "description";

    /**
     * Update information about project in database
     *
     * @param project project data from form
     */
    public void update(Project project) {
        super.update(UPDATE_QUERY, UPDATE_PROJECT_ERROR, project.getName(), project.getShortName(), project.getDescription(), project.getId());
    }

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     */
    public Project getById(int id) {
        return super.getById(id, SELECT_BY_ID_QUERY, GET_PROJECT_BY_ID_ERROR);
    }


    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     */
    public List<Project> getAll() {
        return super.getAll(SELECT_QUERY, GET_PROJECTS_LIST_ERROR);
    }


    /**
     * Remove project from database by id
     *
     * @param id project id
     */
    public void remove(int id) {
        super.remove(id, DELETE_QUERY, REMOVE_PROJECT_ERROR);
    }

    public void add(Project project) {
        add(project, INSERT_QUERY, ADD_PROJECT_ERROR, project.getName(), project.getShortName(), project.getDescription());
    }

    /**
     *  Create new project with tasks
     *
     *  @param project new project
     */
    @Override
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
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    @Override
    public List<Project> resultSetToList(ResultSet rs) {
        try {
            List<Project> projects = new ArrayList<>();
            while (rs.next()) {
                projects.add(resultSetAsObject(rs));
            }
            return projects;
        }
        catch (SQLException e) {
            throw new DaoException(RESULT_SET_ERROR, e);
        }
    }
}
