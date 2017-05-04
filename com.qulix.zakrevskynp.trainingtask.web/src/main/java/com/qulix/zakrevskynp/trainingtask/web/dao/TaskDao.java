package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;
import com.qulix.zakrevskynp.trainingtask.web.model.TaskStatus;

/**
 * Using DAO pattern for operations with @{{@link Task}} objects
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
    private static final String PERSON = "person";
    private static final String WHERE_ID = " where project_id = ?";
    private int id = 0;

    /**
     * Getting all tasks from the database
     *
     * @return list of all tasks in database
     */
    @Override
    public List<Task> getAll()  {
        return super.getAll(SELECT_QUERY);
    }

    /**
     * Removing project from the database by id
     *
     * @param id project's id
     */
    @Override
    public void remove(int id)  {
        super.remove(id, DELETE_QUERY);
    }

    /**
     * Inserting task in the database
     *
     * @param task task form data
     */

    @Override
    public void add(Task task) {
        Person person = task.getPerson();
        Integer personId = null;
        if (person != null) {
            personId = person.getId();
        }
        super.add(task, INSERT_QUERY, task.getName(), task.getWorkTime().toMinutes(), task.getStartDate(),
            task.getEndDate(), task.getStatus().toString(), task.getProjectId(), personId);
    }
    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    @Override
    public Task get(int id) {
        return super.get(id, SELECT_TASK_QUERY);
    }

    /**
     * Update task in the database
     *
     * @param task task data from the form
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
     * Create Task object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created task object
     */
    @Override
    protected Task resultSetAsObject(ResultSet resultSet) throws SQLException {
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
            String projectShortName = resultSet.getString(SHORTNAME);
            Object person_id = resultSet.getObject(PERSONID);
            Person person = null;
            if (person_id != null) {
                person = new PersonDao().resultSetAsObject(resultSet);
            }
            Task task = new Task(id, name, time, startDate, endDate, status, person);
            task.setProjectId(projectId);
            task.setPerson(person);
            task.setProjectShortName(projectShortName);
            return task;
        }
        catch (SQLException e) {
            throw new DaoException( e);
        }

    }


    /**
     * Convert the ResultSet to a List of objects
     *
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     */
    @Override
    protected List<Task> resultSetToList(ResultSet rs) {
        try {
            List<Task> tasks = new ArrayList<>();
            while (rs.next()) {
                tasks.add(resultSetAsObject(rs));
            }
            return tasks;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }

    }
    public void updateChangedTasks(HttpServletRequest request, Project project) {
        Project newProject = (Project) request.getSession().getAttribute(Attribute.PROJECT_OBJECT_NAME);
        List<Task> resultTasks = newProject.getTasks();
        List<Task> tasksList = project.getTasks();
        Set<Object> t1 = tasksList.stream().map(Task::getId).collect(Collectors.toSet());
        Set<Object> t2 = resultTasks.stream().map(Task::getId).collect(Collectors.toSet());
        for (Task task : tasksList) {
            if (!t2.contains(task.getId())) {
                remove(task.getId());
            }
        }
        for (Task resultTask : resultTasks) {
            if (t1.contains(resultTask.getId())) {
                update(resultTask);
            }
        }
        for (Task resultTask : resultTasks) {
            if (!t1.contains(resultTask.getId())) {
                add(resultTask);
            }
        }
    }
}
