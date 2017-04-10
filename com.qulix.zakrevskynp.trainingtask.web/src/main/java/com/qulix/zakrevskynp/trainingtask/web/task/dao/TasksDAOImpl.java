package com.qulix.zakrevskynp.trainingtask.web.task.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.Executable;
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

    private static int id = 0;

    private TaskUtil taskUtil = new TaskUtil();

    /**
     * Get all task from database
     *
     * @return list of all tasks in database
     * @
     */
    public List<Task> getTasksList()  {
        return (List<Task>) execute(GET_TASKS_LIST_ERROR, () -> {
            List<Task> tasks = new ArrayList<>();
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    Statement statement = connection.createStatement()
            ) {
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
     *
     * @param id project's id
     */
    public boolean removeTask(int id)  {
        return (boolean) execute(REMOVE_TASKS_ERROR, () -> {
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
     * Insert task in database
     *
     * @param parameters task form data
     * @
     */
    public boolean addTask(Task task)  {
        return (boolean) execute(ADD_TASK_ERROR, () -> {
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)
            ) {
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
     * @param parameters data from add task form
     * @param session {@link HttpSession} object
     * @return list of tasks with added new task
     */
    @Override
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
            task.setPerformer(person.getFname() + " " + person.getSname() + " " + person.getLname());
        }
        tasks.add(task);
        return tasks;
    }

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    public Task getTaskById(int id)  {
        return (Task) execute(GET_TASKS_BY_ID_ERROR, () -> {
            Task task;
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + " where id = ?")
            ) {
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
     *
     * @param parameters task form data
     * @
     */
    public boolean updateTask(Task task)  {
        return (boolean) execute(UPDATE_TASKS_ERROR, () -> {
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
            ) {
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
     * @param parameters data from update task form for getting tasks list
     * @param session {@link HttpSession} object
     * @param id task's id
     */
    @Override
    public void updateTask(Task task, HttpSession session, int id)  {
        List<Task> tasks = (List<Task>) session.getAttribute("resultTasks");
        Iterator iterator = tasks.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Task task1 = (Task) iterator.next();
            if (task1.getId() == id) {
                if (task.getPersonId() != null) {
                    Person person = new PersonDAOImpl().getPersonById(task.getPersonId());
                    task.setPerformer(person.getFname() + " " + person.getSname() + " " + person.getLname());
                }
                tasks.set(i, task);
                break;
            }
            i++;
        }
        session.setAttribute("resultTasks", tasks);
    }

    public void removeTask(int id, HttpSession session) {
        if (id > this.id) {
            this.id = id;
        }
        List<Task> tasks = (List<Task>) session.getAttribute("resultTasks");
        tasks.removeIf(task -> (Integer) task.getId() == id);
        session.setAttribute("resultTasks", tasks);
    }

    /**
     *
     * @param id project id
     * @return List of tasks with specified project id
     * @
     */
    public List<Task> getTasksByProjectId(int id)  {
        return (List<Task>) execute(GET_TASKS_LIST_ERROR, () -> {
            List<Task> tasks = new ArrayList<>();
            try (
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + " where projectId = ?")
            ) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                return taskUtil.resultSetToList(resultSet);
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

    public static int getId() {
        return id++;
    }
}
