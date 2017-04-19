package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.AbstractDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;


/**
 * Implementation of {@link ProjectDAO} interface
 * @author Q-NZA
 */
public class ProjectDAOImpl extends AbstractDAO<Project> implements ProjectDAO {

    private static final String SELECT_QUERY = "select id, name, shortname, description from projects";
    private static final String INSERT_QUERY = "insert into projects(name, shortname, description) values (?, ?, ?)";
    private static final String DELETE_QUERY = "delete from projects where id=?";
    private static final String SELECT_BY_ID_QUERY = "select id, name, shortname, description from projects where id =?";
    private static final String UPDATE_QUERY = "update projects set name = ?, shortname = ?, description = ? where id = ?";

    private static final String ADD_PROJECT_ERROR = "Error while adding project";
    private static final String REMOVE_PROJECT_ERROR = "Error while deleting project";
    private static final String UPDATE_PROJECT_ERROR = "Error while updating project";
    private static final String GET_PROJECTS_LIST_ERROR = "Error while getting projects list";
    private static final String GET_PROJECT_BY_ID_ERROR = "Error while getting project";
    private static final String IDENTITY_QUERY = "call identity()";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORTNAME = "shortName";
    private static final String DESCRIPTION = "description";

    /**
     * Update information about project in database
     * @param project form data
     */
    @Override
    public void updateProject(Project project) {
        super.update(project, project.getId(), UPDATE_QUERY, UPDATE_PROJECT_ERROR);
    }

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     */
    @Override
    public Project getProjectById(int id) {
        return super.getById(id, SELECT_BY_ID_QUERY, GET_PROJECT_BY_ID_ERROR);
    }

    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     */
    @Override
    public List getProjectsList() {
        return super.getList(SELECT_QUERY, GET_PROJECTS_LIST_ERROR);
    }


    /**
     * Remove project from database by id
     * @param id project id
     */
    @Override
    public void removeProject(int id) {
        super.remove(id, DELETE_QUERY, REMOVE_PROJECT_ERROR);
    }

    /**
     *  Create new project with tasks
     *  @param project new project
     *  @param tasks tasks list for adding to new project
     */
    public void addProject(Project project, List<Task> tasks) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            setPreparedStatement(preparedStatement, project);
            preparedStatement.execute();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(IDENTITY_QUERY);
            resultSet.next();
            connection.commit();
            ProjectDAOImpl.this.addProjectTasks(tasks, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeConnection(connection);
            closeStatement(preparedStatement);
            closeStatement(statement);
        }
    }

    private void addProjectTasks(List<Task> tasks, ResultSet resultSet) throws SQLException {
        TasksDAOImpl tasksDAO = new TasksDAOImpl();
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
                task.setProjectId(resultSet.getInt(1));
                tasksDAO.addTask(task);
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
    public Project resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        String shortName = resultSet.getString(SHORTNAME);
        String description = resultSet.getString(DESCRIPTION);
        return new Project(id, name, shortName, description);
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param project Project object
     * @throws SQLException throws while setting parameters in @{{@link PreparedStatement}}
     */
    public int setPreparedStatement(PreparedStatement preparedStatement, Project project) throws SQLException {
        preparedStatement.setString(1, project.getName());
        preparedStatement.setString(2, project.getShortName());
        preparedStatement.setString(3, project.getDescription());
        return 4;
    }

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    public List<Project> resultSetToList(ResultSet rs) throws SQLException {
        List<Project> projects = new ArrayList<>();
        while (rs.next()) {
            projects.add(resultSetAsObject(rs));
        }
        return projects;
    }
}
