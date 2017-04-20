package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.model.TaskStatus;

/**
 * Using DAO pattern for operations with @{{@link Task}} objects
 * @author Q-NZA
 */
public class TaskDAOImpl extends AbstractDAO<Task> {
    
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

    private static final String GET_TASKS_LIST_ERROR = "Ошибка при получении списка задач";
    private static final String REMOVE_TASKS_ERROR = "Ошибка при удалении задачи";
    private static final String ADD_TASK_ERROR = "Ошибка при добавлении задачи";
    private static final String UPDATE_TASKS_ERROR = "Ошибка при обновлении задачи";
    private static final String GET_TASKS_BY_ID_ERROR = "Ошибка при получении задачи";

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
    private static final String WHERE_ID = " where projectId = ?";

    private int id = 0;

    /**
     * Getting all tasks from the database
     * @return list of all tasks in database
     */
    public List<Task> getAll()  {
        return super.getAll(SELECT_QUERY, GET_TASKS_LIST_ERROR);
    }

    /**
     * Removing project from the database by id
     * @param id project's id
     */
    public void remove(int id)  {
        super.remove(id, DELETE_QUERY, REMOVE_TASKS_ERROR);
    }

    /**
     * Inserting task in the database
     * @param task task form data
     */
    public void add(Task task)  {
        super.add(task, INSERT_QUERY, ADD_TASK_ERROR);
    }

    /**
     * Get task by id
     * @param id task's id
     * @return Task object
     */
    public Task getById(int id) {
        return super.getById(id, SELECT_BY_ID_QUERY, GET_TASKS_BY_ID_ERROR);
    }

    /**
     * Update task in the database
     * @param task task data from the form
     */
    public void update(Task task)  {
        super.update(task, task.getId(), UPDATE_QUERY, UPDATE_TASKS_ERROR);
    }

    /**
     * Insert task in database
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
            Person person = new PersonDAOImpl().getById(task.getPersonId());
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
    public List<Task> updateTask(Task task, List<Task> tasks, int id) {
        int index = 0;
        for (Task task1 : tasks) {
            if (task1.getId() == id) {
                if (task.getPersonId() != null) {
                    Person person = new PersonDAOImpl().getById(task.getPersonId());
                    task.setPerformer(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName());
                }
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
            preparedStatement = connection.prepareStatement(SELECT_QUERY + WHERE_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(GET_TASKS_LIST_ERROR, e);
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
    @Override
    public Task resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        Duration time = Duration.ofMinutes(resultSet.getInt(TIME));
        Date startDate = resultSet.getDate(STARTDATE);
        Date endDate = resultSet.getDate(ENDDATE);
        TaskStatus status = TaskStatus.valueOf(resultSet.getString(STATUS));
        Integer projectId = resultSet.getInt(PROJECTID);
        Integer personId = (Integer) resultSet.getObject(PERSONID);
        String projectShortName = resultSet.getString(SHORTNAME);
        String performer = resultSet.getString(PERSON);
        Task task = new Task(id, name, time, startDate, endDate, status, performer);
        task.setProjectId(projectId);
        task.setPersonId(personId);
        task.setProjectShortName(projectShortName);
        return task;
    }

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    @Override
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
    @Override
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
