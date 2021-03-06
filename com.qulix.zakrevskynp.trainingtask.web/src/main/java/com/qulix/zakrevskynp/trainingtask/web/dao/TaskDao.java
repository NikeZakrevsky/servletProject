package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.model.TaskStatus;

/**
 * Using DAO pattern for operations with @{{@link Task}} objects.
 *
 * @author Q-NZA
 */
public class TaskDao extends AbstractDao<Task> {
    private static final String SELECT_QUERY = "select task_id, task_name, work_time, start_date, end_date, status, short_name," +
        "project_id, person_id, first_name, middle_name, last_name, position from tasks left join projects on" +
        " tasks.project_id = projects.project_id left join persons on tasks.person_id = persons.person_id";
    private static final String SELECT_TASK_QUERY = "select task_id, task_name, work_time, start_date, end_date, status, " +
        "short_name, project_id, person_id, first_name, middle_name, last_name, position as person from tasks left join " +
        "projects on tasks.project_id = projects.project_id left join persons on tasks.person_id = persons.person_id where " +
        "task_id = ?";
    private static final String DELETE_QUERY = "delete from tasks where task_id=?";
    private static final String INSERT_QUERY = "insert into tasks(task_name, work_time, start_date, end_date, status, " +
        "project_id, person_id) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update tasks set task_name = ?, work_time = ?, start_date = ?, end_date = ?, " +
        "status = ?, project_id = ?, person_id = ? where task_id = ?";
    private static final String ID = "task_id";
    private static final String NAME = "task_name";
    private static final String TIME = "work_time";
    private static final String STARTDATE = "start_date";
    private static final String ENDDATE = "end_date";
    private static final String STATUS = "status";
    private static final String PROJECTID = "project_id";
    private static final String PERSONID = "person_id";
    private static final String SHORTNAME = "short_name";

    /**
     * Gets all tasks from the database.
     *
     * @return list of all tasks in database.
     */
    @Override
    public List<Task> getAll()  {
        return super.getAll(SELECT_QUERY);
    }

    /**
     * Removes project from the database by id.
     *
     * @param id project's id.
     */
    @Override
    public void remove(int id)  {
        super.remove(id, DELETE_QUERY);
    }

    /**
     * Inserts task in the database.
     *
     * @param task task form data.
     */
    @Override
    public void add(Task task) {
        Person person = task.getPerson();
        Integer personId = null;
        if (person != null) {
            personId = person.getId();
        }
        super.add(INSERT_QUERY, task.getName(), task.getWorkTime().toMinutes(), task.getStartDate(),
            task.getEndDate(), task.getStatus().toString(), task.getProjectId(), personId);
    }

    /**
     * Gets task by id.
     *
     * @param id task's id.
     * @return Task object.
     */
    @Override
    public Task get(int id) {
        return super.get(id, SELECT_TASK_QUERY);
    }

    /**
     * Updates task in the database.
     *
     * @param task task data from the form.
     */
    @Override
    public void update(Task task)  {
        Person person = task.getPerson();
        Integer personId = null;
        if (person != null) {
            personId = person.getId();
        }
        super.update(UPDATE_QUERY, task.getName(), task.getWorkTime().toMinutes(), task.getStartDate(),
            task.getEndDate(), task.getStatus().toString(), task.getProjectId(), personId, task.getId());
    }

    /**
     * Creates Task object from ResultSet.
     *
     * @param resultSet resultSet for converting to object.
     * @return created task object.
     */
    protected Task resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        Duration workTime = Duration.ofMinutes(resultSet.getInt(TIME));
        Date startDate = resultSet.getDate(STARTDATE);
        Date endDate = resultSet.getDate(ENDDATE);
        String stringStatus = resultSet.getString(STATUS);
        TaskStatus status = null;
        if (stringStatus != null) {
            status = TaskStatus.fromString(stringStatus);
        }
        Integer projectId = resultSet.getInt(PROJECTID);
        String projectShortName = resultSet.getString(SHORTNAME);
        Object personId = resultSet.getObject(PERSONID);
        Person person = null;
        if (personId != null) {
            person = new PersonDao().resultSetAsObject(resultSet);
        }
        Task task = new Task(id, name, workTime, startDate, endDate, status, person);
        task.setProjectId(projectId);
        task.setPerson(person);
        task.setProjectShortName(projectShortName);

        return task;
    }

    /**
     * Converts the ResultSet to a List of objects.
     *
     * @param resultSet @{{@link ResultSet}} object converted to list.
     * @return tasks list.
     */
    @Override
    protected List<Task> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            tasks.add(resultSetAsObject(resultSet));
        }

        return tasks;
    }
}
