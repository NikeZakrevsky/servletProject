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
 *
 * @author Q-NZA
 */
public class TaskDaoImpl extends AbstractDao<Task> {
    
    private static final String SELECT_QUERY = "select task_id, task_name, work_time, start_date, end_date, status, short_name, " +
        "project_id, person_id, first_name, middle_name, last_name, position from tasks left join projects on" +
        " tasks.project_id = projects.project_id left join persons on tasks.person_id = persons.person_id";
    private static final String SELECT_TASK_QUERY = "select task_id, task_name, work_time, start_date, end_date, status, short_name, " +
        "project_id, person_id, first_name, middle_name, last_name, position as person from tasks left join projects on" +
        " tasks.project_id = projects.project_id left join persons on tasks.person_id = persons.person_id where task_id = ?";
    private static final String DELETE_QUERY = "delete from tasks where task_id=?";
    private static final String INSERT_QUERY = "insert into tasks(task_name, work_time, start_date, end_date, status, project_id, " +
        "person_id) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update tasks set task_name = ?, work_time = ?, start_date = ?, end_date = ?, " +
        "status = ?, project_id = ?, person_id = ? where task_id = ?";
    private static final String GET_TASKS_ERROR = "Ошибка при получении списка задач";
    private static final String REMOVE_TASKS_ERROR = "Ошибка при удалении задачи";
    private static final String ADD_TASK_ERROR = "Ошибка при добавлении задачи";
    private static final String UPDATE_TASKS_ERROR = "Ошибка при обновлении задачи";
    private static final String GET_TASK_ERROR = "Ошибка при получении задачи";
    private static final String ID = "task_id";
    private static final String NAME = "task_name";
    private static final String TIME = "work_time";
    private static final String STARTDATE = "start_date";
    private static final String ENDDATE = "end_date";
    private static final String STATUS = "status";
    private static final String PROJECTID = "project_id";
    private static final String PERSONID = "person_id";
    private static final String SHORTNAME = "short_name";
    private static final String PERSON = "person";
    private static final String WHERE_ID = " where project_id = ?";
    private int id = 0;

    /**
     * Getting all tasks from the database
     *
     * @return list of all tasks in database
     */
    public List<Task> getAll()  {
        return super.getAll(SELECT_QUERY, GET_TASKS_ERROR);
    }

    /**
     * Removing project from the database by id
     *
     * @param id project's id
     */
    public void remove(int id)  {
        super.remove(id, DELETE_QUERY, REMOVE_TASKS_ERROR);
    }

    /**
     * Inserting task in the database
     *
     * @param task task form data
     */
    public void add(Task task)  {
        super.add(task, INSERT_QUERY, ADD_TASK_ERROR, task.getName(), task.getWorkTime().toMinutes(), task.getStartDate(), task.getEndDate(), task.getStatus().toString(), task.getProjectId(), task.getPersonId());
    }

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    public Task get(int id) {
        return super.get(id, SELECT_TASK_QUERY, GET_TASK_ERROR);
    }

    /**
     * Update task in the database
     *
     * @param task task data from the form
     */
    public void update(Task task)  {
        super.update(UPDATE_QUERY, UPDATE_TASKS_ERROR, task.getName(), task.getWorkTime().toMinutes(), task.getStartDate(), task.getEndDate(), task.getStatus().toString(), task.getProjectId(), task.getPersonId(), task.getId());
    }

    /**
     * Insert task in database
     *
     * @param task data from add task form
     * @return list of tasks with added new task
     */
    public List<Task> addTaskToList(Task task, List<Task> tasks) {
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
            Person person = new PersonDaoImpl().get(task.getPersonId());
            task.setPerson(person);
        }
        tasks.add(task);
        return tasks;
    }

    /**
     * Update task in session
     *
     * @param task data from update task form for getting tasks list
     * @param id task's id
     */
    public List<Task> updateTaskInList(Task task, List<Task> tasks, int id) {
        int index = 0;
        for (Task task1 : tasks) {
            if (task1.getId() == id) {
                setPerformer(task);
                index = tasks.indexOf(task1);
                break;
            }
        }
        tasks.set(index, task);
        return tasks;
    }

    private void setPerformer(Task task) {
        if (task.getPersonId() != null) {
            Person person = new PersonDaoImpl().get(task.getPersonId());
            task.setPerson(person);
        }
    }

    /**
     * Remove task from session
     *
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
     * Create Task object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created task object
     */
    @Override
    public Task resultSetAsObject(ResultSet resultSet) throws SQLException {
        try {
            Integer id = resultSet.getInt(ID);
            String name = resultSet.getString(NAME);
            Duration time = Duration.ofMinutes(resultSet.getInt(TIME));
            Date startDate = resultSet.getDate(STARTDATE);
            Date endDate = resultSet.getDate(ENDDATE);
            String stringStatus = resultSet.getString(STATUS);
            TaskStatus status = null;
            if (stringStatus != null) {
                status = TaskStatus.fromString(stringStatus);
            }
            Integer projectId = resultSet.getInt(PROJECTID);
            Integer personId = (Integer) resultSet.getObject(PERSONID);
            String projectShortName = resultSet.getString(SHORTNAME);
            Person person = new PersonDaoImpl().resultSetAsObject(resultSet);
            Task task = new Task(id, name, time, startDate, endDate, status, person);
            task.setProjectId(projectId);
            task.setPersonId(personId);
            task.setProjectShortName(projectShortName);
            return task;
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
     */
    @Override
    public List<Task> resultSetToList(ResultSet rs) {
        try {
            List<Task> tasks = new ArrayList<>();
            while (rs.next()) {
                tasks.add(resultSetAsObject(rs));
            }
            return tasks;
        }
        catch (SQLException e) {
            throw new DaoException(RESULT_SET_ERROR, e);
        }

    }
}
