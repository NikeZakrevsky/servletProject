package com.qulix.zakrevskynp.trainingtask.web.taskProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.util.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.task.TaskDataValidator;

@WebServlet("/addTaskProject")
public class AddTaskProjectServlet extends HttpServlet {
    private int id = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("personsList", new PersonDAOImpl().getPersonsList());
            request.getRequestDispatcher("taskView.jsp").forward(request, response);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("tasks") == null) {
            List<Map<String, Object>> tasks = new ArrayList<>();
            session.setAttribute("tasks", tasks);
        }

        List<Map<String, Object>> tasks = (List<Map<String, Object>>)session.getAttribute("tasks");

        List<String> parametersNames = Collections.list(request.getParameterNames());
        Map<String, Object> parameters = parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));

        TaskDataValidator taskDataValidator = new TaskDataValidator();

        parameters.put("id", id);
        Person person = new Person();
        taskDataValidator.validate(parameters);
        try {
            person = new PersonDAOImpl().getPersonById((int)parameters.get("personId"));
        } catch (CustomException e) {
            e.printStackTrace();
        }
        parameters.put("performer", person.getFname() + " " +  person.getSname() + " " + person.getLname());
        id++;
        tasks.add(parameters);

        session.setAttribute("tasks", tasks);

        request.setAttribute("action", "addProject");
        request.setAttribute("tasks", tasks);
        response.sendRedirect("addProject");
    }
}
