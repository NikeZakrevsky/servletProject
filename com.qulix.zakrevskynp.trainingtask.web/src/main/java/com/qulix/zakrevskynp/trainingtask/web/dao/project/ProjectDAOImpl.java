package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.ExecuteDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;


/**
 * Implementation of {@link ProjectDAO} interface
 * @author Q-NZA
 */
public class ProjectDAOImpl implements ProjectDAO {

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

    private ProjectUtil projectUtil = new ProjectUtil();

    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     */
    @SuppressWarnings("unchecked")
    public List<Project> getProjectsList() {
        return (List<Project>) ExecuteDAO.execute(GET_PROJECTS_LIST_ERROR, (connection) -> {
                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
                    return projectUtil.resultSetToList(resultSet);
                }
            });
    }

    /**
     *  Create new project with tasks
     *  @param project new project
     *  @param tasks tasks list for adding to new project
     */
    public void addProject(Project project, List<Task> tasks) {
        ExecuteDAO.execute(ADD_PROJECT_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
                    projectUtil.setPreparedStatement(preparedStatement, project);
                    preparedStatement.execute();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("call identity()");
                    resultSet.next();
                    connection.commit();
                    addProjectTasks(tasks, resultSet);
                }
                return true;
            });
    }

    private void addProjectTasks(List<Task> tasks, ResultSet resultSet) throws SQLException {
        TasksDAOImpl tasksDAO = new TasksDAOImpl();
        if (tasks != null && tasks.size() != 0) {
            for (Task task : tasks) {
                task.setProjectId(resultSet.getInt(1));
                tasksDAO.addTask(task);
            }
        }
    }

    /**
     * Remove project from database by id
     * @param id project id
     */
    public void removeProject(int id) {
        ExecuteDAO.execute(REMOVE_PROJECT_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.execute();
                    connection.commit();
                }
                return true;
            });
    }

    /**
     * Update information about project in database
     * @param project form data
     */
    public void updateProject(Project project) {
        ExecuteDAO.execute(UPDATE_PROJECT_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
                    projectUtil.setPreparedStatement(preparedStatement, project);
                    preparedStatement.setInt(4, project.getId());
                    preparedStatement.execute();
                    connection.commit();
                }
                return true;
            });
    }

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     */
    public Project getProjectById(int id) {
        return (Project) ExecuteDAO.execute(GET_PROJECT_BY_ID_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    return projectUtil.resultSetAsObject(resultSet);
                }
            });
    }

}
