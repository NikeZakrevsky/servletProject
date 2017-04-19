package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.AbstractDAO;
import com.qulix.zakrevskynp.trainingtask.web.dao.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.person.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.model.TaskStatus;

/**
 * Implementation of {@link TasksDAO} interface
 * @author Q-NZA
 */
public class TasksDAOImpl extends AbstractDAO<Task> implements TasksDAO {
    
    private static final String SELECT_QUERY = "select id, name, time, startDate, endDate, " +
            "status, shortname, projectId, personId, fname + ' ' + sname + ' ' + lname " +
            "as person from tasks left join projects on tasks.projectId = projects.id left join " +
            "persons on tasks.personId = persons.id";
    private static final String SELECT_BY_ID_QUERY = "select id, name, time, startDate, endDate, " +
                "status, shortname, projectId, personId, fname + ' ' + sname + ' ' + lname " +
                "as person from tasks left join projects on tasks.projectId = projects.id left join " +
                "persons on tasks.personId = persons.id where id = ?";
    private static final String DELETE_QUERY = "delete from tasks where id=?";
    private static final String INSERT_QUERY = "insert into tasks(name, time, startDate, endDate, " +
            "status, projectId, personId) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update tasks set name = ?, time = ?, startDate = ?, " +
            "endDate = ?, status = ?, projectId = ?, personId = ? where id = ?";

    private static final String GET_TASKS_LIST_ERROR = "Error while getting tasks list";
    private static final String REMOVE_TASKS_ERROR = "Error while removing task";
    private static final String ADD_TASK_ERROR = "Error while adding task";
    private static final String UPDATE_TASKS_ERROR = "Error while updating task";
    private static final String GET_TASKS_BY_ID_ERROR = "Error while getting task";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String STARTDATE = "startDate";
    private static final String ENDDATE = "endDate";
    private static final String STATUS = "status";
    private static final String PROJECTID = "projectId";
    private static final String PERSONID = "personId";
    private static final String SHORTNAME = "shortname";
    private static final String PERSON = "person";

    private int id = 0;

    /**
     * Get all task from database
     * @return list of all tasks in database
     */
    @Override
    public List<Task> getTasksList()  {
        return super.getList(SELECT_QUERY, GET_TASKS_LIST_ERROR);
    }

    /**
     * Remove project from database by id
     * @param id project's id
     */
    @Override
    public void removeTask(int id)  {
        super.remove(id, DELETE_QUERY, REMOVE_TASKS_ERROR);
    }

    /**
     * Insert task in database
     * @param task task form data
     */
    @Override
    public void addTask(Task task)  {
        super.add(task, INSERT_QUERY, ADD_TASK_ERROR);
    }

    /**
     * Get task by id
     * @param id task's id
     * @return Task object
     */
    public Task getTaskById(int id) {
        return super.getById(id, SELECT_BY_ID_QUERY, GET_TASKS_BY_ID_ERROR);
    }

    /**
     * Update task in database
     * @param task task form data
     */
    @Override
    public void updateTask(Task task)  {
        super.update(task, task.getId(), UPDATE_QUERY, UPDATE_TASKS_ERROR);
    }

    /**
     * Insert task in database
     *
     * @param task data from add task form
     * @return list of tasks with added new task
     */

    public List<Task> addTask(Task task, List<Task> tasks) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        for (Task task1 : tasks) {
            if (task1.getId() > id) {
                id = task1.getId();
            }
        }
        task.setId(id + 1);
        if (task.getPersonId() != null) {
            Person person = new PersonDAOImpl().getPersonById(task.getPersonId());
            task.setPerformer(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName());
        }
        tasks.add(task);
        return tasks;
    }

    /**
     * Update task in session
     * @param task data from update task form for getting tasks list
     * @param id task's id
     */
    @Override
    public List<Task> updateTask(Task task, List<Task> tasks, int id) {
        int index = 0;
        for (Task task1 : tasks) {
            if (task1.getId() == id && task.getPersonId() != null) {
                Person person = new PersonDAOImpl().getPersonById(task.getPersonId());
                task.setPerformer(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName());
                index = tasks.indexOf(task1);
                break;
            }
        }
        tasks.set(index, task);
        return tasks;
    }

    /**
     * Remove task from session
     * @param id task id
     */
    public List<Task> removeTask(int id, List<Task> tasks) {
        if (id > this.id) {
            this.id = id;
        }
        tasks.removeIf(task -> task.getId() == id);
        return tasks;
    }

    /**
     * Get tasks list for project
     * @param id project id
     * @return List of tasks with specified project id
     */
    public List<Task> getTasksByProjectId(int id)  {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_QUERY + " where projectId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeConnection(connection);
            closeStatement(preparedStatement);
        }
    }

    /**
     * Create Task object from ResultSet
     * @param resultSet resultSet for converting to object
     * @return created task object
     */
    public Task resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        Duration time = Duration.ofMinutes(resultSet.getInt(TIME));
        Date startDate = resultSet.getDate(STARTDATE);
        Date endDate = resultSet.getDate(ENDDATE);
        TaskStatus status = TaskStatus.valueOf(resultSet.getString(STATUS));
        Integer projectId = resultSet.getInt(PROJECTID);
        Integer personId = resultSet.getInt(PERSONID);
        String projectShortName = resultSet.getString(SHORTNAME);
        String performer = resultSet.getString(PERSON);
        Task task = new Task(id, name, time, startDate, endDate, status, performer);
        task.setProjectId(projectId);
        task.setPersonId(personId);
        task.setProjectShortName(projectShortName);
        return new Task(id, name, time, startDate, endDate, status, performer);
    }

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    public List<Task> resultSetToList(ResultSet rs) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        while (rs.next()) {
            tasks.add(resultSetAsObject(rs));
        }
        return tasks;
    }
    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param task task form data
     * @throws SQLException throws while setting parameters to prepared statement
     */
    public int setPreparedStatement(PreparedStatement preparedStatement, Task task) throws SQLException {
        preparedStatement.setString(1, task.getName());
        preparedStatement.setLong(2, task.getWorkTime().toMinutes());
        preparedStatement.setDate(3, task.getStartDate());
        preparedStatement.setDate(4, task.getEndDate());
        preparedStatement.setString(5, task.getTaskStatus().toString());
        preparedStatement.setObject(6, task.getProjectId());
        preparedStatement.setObject(7, task.getPersonId());
        return 8;
    }
}
