package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import java.sql.*;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.AbstractDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.Executable;
import com.qulix.zakrevskynp.trainingtask.web.dao.ExecuteDAO;
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

    private ProjectUtil projectUtil = new ProjectUtil();

    /**
     * Update information about project in database
     * @param project form data
     */
    @Override
    public void updateProject(Project project) {
        super.update(projectUtil, project, project.getId(), UPDATE_QUERY, UPDATE_PROJECT_ERROR);
    }

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     */
    @Override
    public Project getProjectById(int id) {
        return super.getById(projectUtil, id, SELECT_BY_ID_QUERY, GET_PROJECT_BY_ID_ERROR);
    }

    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     */
    @Override
    public List<Project> getProjectsList() {
        return super.getList(projectUtil, SELECT_QUERY, GET_PROJECTS_LIST_ERROR);
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
        ExecuteDAO.execute(ADD_PROJECT_ERROR, new Executable() {
            @Override
            public Object exec(Connection connection) throws SQLException {
                ResultSet resultSet = null;
                Statement statement = null;
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
                    projectUtil.setPreparedStatement(preparedStatement, project);
                    preparedStatement.execute();
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(IDENTITY_QUERY);
                    resultSet.next();
                    connection.commit();
                    ProjectDAOImpl.this.addProjectTasks(tasks, resultSet);
                }
                finally {
                    closeResultSet(resultSet);
                    closeStatement(statement);
                }
                return true;
            }
        });
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
}
