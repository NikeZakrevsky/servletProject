package com.qulix.zakrevskynp.trainingtask.web.project.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.project.Project;
import com.qulix.zakrevskynp.trainingtask.web.task.dao.TasksDAOImpl;


/**
 * Implementation of {@link ProjectDAO} interface
 * @author Q-NZA
 */
public class ProjectDAOImpl implements ProjectDAO {

    private static final String SELECT_QUERY = "SELECT \"id\", \"name\", \"shortname\", \"description\" FROM \"projects\"";
    private static final String INSERT_QUERY = "insert into \"projects\"(\"name\", \"shortname\", \"description\") values (?, ?, ?)";
    private static final String DELETE_QUERY = "delete from \"projects\" where \"id\"=?";
    private static final String SELECT_BY_ID_QUERY = "select \"id\", \"name\", \"shortname\", \"description\" FROM \"projects\" where \"id\" =?";
    private static final String UPDATE_QUERY = "update \"projects\" set \"name\" = ?, \"shortname\" = ?, \"description\" = ? where \"id\" = ?";

    private ProjectUtil projectUtil = new ProjectUtil();
    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     * @throws CustomException
     */
    public List<Project> getProjectsList() throws CustomException {
        List<Project> projects = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            while (resultSet.next()) {
                projects.add(projectUtil.resultSetAsObject(resultSet));
            }
        } catch (SQLException e) {
            throw new CustomException("Error while getting projects list", e);
        }
        return projects;
    }

    /**
     *
     * @param parameters project form data
     * @throws CustomException
     */
    public void addProject(Map<String, Object> parameters, List<Map<String, Object>> tasks) throws CustomException {
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
        } catch (SQLException e) {
            throw new CustomException("Error while adding project", e);
        }
    }

    /**
     * Remove project from database by id
     *
     * @param id project id
     * @throws CustomException
     */
    public void removeProject(int id) throws CustomException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            throw new CustomException("Error while deleting project", e);
        }
    }

    /**
     * Update information about project in database
     *
     * @param parameters project form data
     * @throws CustomException
     */
    public void updateProject(Map<String, Object> parameters) throws CustomException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            projectUtil.setPreparedStatement(preparedStatement, parameters);
            preparedStatement.setInt(4, Integer.parseInt(parameters.get("id").toString()));
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new CustomException("Error while updating project", e);
        }
    }

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     * @throws CustomException
     */
    public Project getProjectById(int id) throws CustomException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return projectUtil.resultSetAsObject(resultSet);
        } catch (SQLException e) {
            throw new CustomException("Error while getting project", e);
        }
    }
}
