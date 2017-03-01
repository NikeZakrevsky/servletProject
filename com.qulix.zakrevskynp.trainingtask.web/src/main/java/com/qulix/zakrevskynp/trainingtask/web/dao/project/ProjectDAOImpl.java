package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.zakrevskynp.trainingtask.web.dao.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;


/**
 * Implementation of {@link ProjectDAO} interface
 * @author Q-NZA
 */
public class ProjectDAOImpl implements ProjectDAO {

    private static final String SELECT_QUERY = "SELECT id, name, shortname, description FROM projects";
    private static final String INSERT_QUERY = "insert into projects(name, shortname, description) values (?, ?, ?)";
    private static final String DELETE_QUERY = "delete from projects where id=?";
    private static final String SELECT_BY_ID_QUERY = "select id, name, shortname, description FROM projects where id=?";
    private static final String UPDATE_QUERY = "update projects set name = ?, shortname = ?, description = ? where id = ?";

    private ProjectUtil projectUtil = new ProjectUtil();
    private Logger logger = Logger.getLogger(ProjectDAOImpl.class.getName());
    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     * @throws DAOException
     */
    public List<Project> getProjectsList() throws DAOException {
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
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
        return projects;
    }

    /**
     *
     * @param project {@link Project} object
     * @throws DAOException
     */
    public void addProject(Project project) throws DAOException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)
        ) {
            projectUtil.setPreparedStatement(preparedStatement, project);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Remove project from database by id
     *
     * @param id
     * @throws DAOException
     */
    public void removeProject(int id) throws DAOException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Update information about project in database
     *
     * @param project {@link Project} object
     * @throws DAOException
     */
    public void updateProject(Project project) throws DAOException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            projectUtil.setPreparedStatement(preparedStatement, project);
            preparedStatement.setInt(4, project.getId());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     * @throws DAOException
     */
    public Project getProjectById(int id) throws DAOException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return projectUtil.resultSetAsObject(resultSet);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }
}
