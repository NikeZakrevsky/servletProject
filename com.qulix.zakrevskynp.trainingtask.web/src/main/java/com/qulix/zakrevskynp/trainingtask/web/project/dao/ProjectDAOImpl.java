package com.qulix.zakrevskynp.trainingtask.web.project.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.Executable;
import com.qulix.zakrevskynp.trainingtask.web.project.Project;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;


/**
 * Implementation of {@link ProjectDAO} interface
 * @author Q-NZA
 */
public class ProjectDAOImpl implements ProjectDAO {

    private static final String SELECT_QUERY = "SELECT \"id\", \"name\", \"shortname\", " +
            "\"description\" FROM \"projects\"";
    private static final String INSERT_QUERY = "insert into \"projects\"(\"name\", \"shortname\", " +
            "\"description\") values (?, ?, ?)";
    private static final String DELETE_QUERY = "delete from \"projects\" where \"id\"=?";
    private static final String SELECT_BY_ID_QUERY = "select \"id\", \"name\", \"shortname\", " +
            "\"description\" FROM \"projects\" where \"id\" =?";
    private static final String UPDATE_QUERY = "update \"projects\" set \"name\" = ?, \"shortname\" = ?, " +
            "\"description\" = ? where \"id\" = ?";

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
     * @throws CustomException
     */
    public List<Project> getProjectsList() {
        return (List<Project>) execute(GET_PROJECTS_LIST_ERROR, () -> {
            List<Project> projects = new ArrayList<>();
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    Statement statement = connection.createStatement()
            ) {
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
                while (resultSet.next()) {
                    projects.add(projectUtil.resultSetAsObject(resultSet));
                }
            }
            return projects;
        });
    }

    /**
     *
     * @param parameters project form data
     * @throws CustomException
     */
    public boolean addProject(Map<String, Object> parameters, List<Map<String, Object>> tasks) {
        return (boolean) execute(ADD_PROJECT_ERROR, () -> {
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)
            ) {
                projectUtil.setPreparedStatement(preparedStatement, parameters);
                preparedStatement.execute();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("CALL IDENTITY()");
                resultSet.next();
                connection.commit();
                TasksDAOImpl tasksDAO = new TasksDAOImpl();
                if (tasks != null) {
                    Iterator<Map<String, Object>> iterator = tasks.iterator();
                    if (tasks.size() != 0) {
                        while (iterator.hasNext()) {
                            Map<String, Object> task = iterator.next();
                            task.put("projectId", resultSet.getInt(1));
                            tasksDAO.addTask(task);
                        }
                    }
                }
            }
            return true;
        });
    }

    /**
     * Remove project from database by id
     *
     * @param id project id
     * @throws CustomException
     */
    public boolean removeProject(int id) {
        return (boolean) execute(REMOVE_PROJECT_ERROR, () -> {
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)
            ) {
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                connection.commit();
            }
            return true;
        });
    }

    /**
     * Update information about project in database
     *
     * @param parameters project form data
     * @throws CustomException
     */
    public boolean updateProject(Map<String, Object> parameters) {
        return (boolean) execute(UPDATE_PROJECT_ERROR, () -> {
            try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
            ) {
                projectUtil.setPreparedStatement(preparedStatement, parameters);
                preparedStatement.setInt(4, Integer.parseInt(parameters.get("id").toString()));
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
     * @throws CustomException
     */
    public Project getProjectById(int id) {
        return (Project) execute(GET_PROJECT_BY_ID_ERROR, () -> {
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)
            ) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return projectUtil.resultSetAsObject(resultSet);
            }
        });
    }

    private Object execute(String message, Executable ex) {
        try {
            return ex.exec();
        } catch (SQLException e) {
            throw new RuntimeException(message, e);
        }
    }
}
