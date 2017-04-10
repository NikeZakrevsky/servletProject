package com.qulix.zakrevskynp.trainingtask.web.task.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.ExecuteDAO;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;

/**
 * Implementation of {@link TasksDAO} interface
 * @author Q-NZA
 */
public class TasksDAOImpl implements TasksDAO {
    
    private static final String SELECT_QUERY = "SELECT id, name, time, startDate, endDate, " +
            "status, shortname, projectId, personId, fname + ' ' + sname + ' ' + lname " +
            "as person FROM tasks left join projects on tasks.projectId = projects.id left join " +
            "persons on tasks.personId = persons.id";
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

    private int id = 0;

    private TaskUtil taskUtil = new TaskUtil();

    /**
     * Get all task from database
     * @return list of all tasks in database
     */
    @SuppressWarnings("unchecked")
    public List<Task> getTasksList()  {
        return (List<Task>) ExecuteDAO.execute(GET_TASKS_LIST_ERROR, (connection) -> {
                List<Task> tasks = new ArrayList<>();
                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
                    while (resultSet.next()) {
                        Task task = taskUtil.resultSetAsObject(resultSet);
                        tasks.add(task);
                    }
                }
                return tasks;
            });
    }

    /**
     * Remove project from database by id
     * @param id project's id
     */
    public void removeTask(int id)  {
        ExecuteDAO.execute(REMOVE_TASKS_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.execute();
                    connection.commit();
                }
                return true;
            });
    }

    /**
     * Insert task in database
     * @param task task form data
     */
    public void addTask(Task task)  {
        ExecuteDAO.execute(ADD_TASK_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
                    taskUtil.setPreparedStatement(preparedStatement, task);
                    preparedStatement.execute();
                    connection.commit();
                }
                return true;
            });
    }

    /**
     * Insert task in database
     *
     * @param task data from add task form
     * @param session {@link HttpSession} object
     * @return list of tasks with added new task
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Task> addTask(Task task, HttpSession session)  {
        List<Task> tasks = (List<Task>) session.getAttribute("resultTasks");
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
     * Get task by id
     * @param id task's id
     * @return Task object
     */
    public Task getTaskById(int id)  {
        return (Task) ExecuteDAO.execute(GET_TASKS_BY_ID_ERROR, (connection) -> {
                Task task;
                try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + " where id = ?")) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    task = taskUtil.resultSetAsObject(resultSet);
                    connection.commit();
                }
                return task;
            });
    }

    /**
     * Update task in database
     * @param task task form data
     */
    public void updateTask(Task task)  {
        ExecuteDAO.execute(UPDATE_TASKS_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
                    taskUtil.setPreparedStatement(preparedStatement, task);
                    preparedStatement.setInt(8, task.getId());
                    preparedStatement.execute();
                    connection.commit();
                }
                return true;
            });
    }

    /**
     * Update task in session
     * @param task data from update task form for getting tasks list
     * @param session {@link HttpSession} object
     * @param id task's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public void updateTask(Task task, HttpSession session, int id)  {
        List<Task> tasks = (List<Task>) session.getAttribute("resultTasks");
        Iterator iterator = tasks.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Task task1 = (Task) iterator.next();
            if (task1.getId() == id) {
                if (task.getPersonId() != null) {
                    Person person = new PersonDAOImpl().getPersonById(task.getPersonId());
                    task.setPerformer(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName());
                }
                tasks.set(i, task);
                break;
            }
            i++;
        }
        session.setAttribute("resultTasks", tasks);
    }

    /**
     * Remove task from session
     * @param id task id
     * @param session @{{@link HttpSession}} object
     */
    @SuppressWarnings("unchecked")
    public void removeTask(int id, HttpSession session) {
        if (id > this.id) {
            this.id = id;
        }
        List<Task> tasks = (List<Task>) session.getAttribute("resultTasks");
        tasks.removeIf(task -> task.getId() == id);
        session.setAttribute("resultTasks", tasks);
    }

    /**
     * Get tasks list for project
     * @param id project id
     * @return List of tasks with specified project id
     */
    @SuppressWarnings("unchecked")
    public List<Task> getTasksByProjectId(int id)  {
        return (List<Task>) ExecuteDAO.execute(GET_TASKS_LIST_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + " where projectId = ?")) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return taskUtil.resultSetToList(resultSet);
                }
            });

    }
}
