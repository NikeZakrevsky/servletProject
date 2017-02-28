<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <title>Simple jsp page</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">TestTaskWebsite</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="projectsList">Проекты</a></li>
            <li><a href="tasksList">Задачи</a></li>
            <li><a href="personsList">Персоны</a></li>
        </ul>
    </div>
</nav>
<div class="generic-container">
    <span style="color: red; ">${error}</span></br>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Список задач</span></div>
            <div class="panel-body">
                <form action = "tasksList" method = "POST">
                    <select name = "sortField">
                        <option value = "name" ${"name".equals(sortF) ? 'selected="selected"' : ''}>Название</option>
                        <option value = "projectShortName" ${"projectShortName".equals(sortF) ? 'selected="selected"' : ''}>Проект</option>
                        <option value = "startDate" ${"startDate".equals(sortF) ? 'selected="selected"' : ''}>Дата начала</option>
                        <option value = "endDate" ${"endDate".equals(sortF) ? 'selected="selected"' : ''}>Дата окончания</option>
                        <option value = "performer" ${"performer".equals(sortF) ? 'selected="selected"' : ''}>Исполнитель</option>
                        <option value = "status" ${"status".equals(sortF) ? 'selected="selected"' : ''}>Статус</option>
                    </select>
                    <button type = "submit" class="btn btn-primary btn-xs">Сортировать</button>
                </form>
            </div>
            <div class="tablecontainer">
                <table class="table table-hover">
                    <thead>
                          <tr>
                              <th>Идентификатор</th>
                              <th>Проект</th>
                              <th>Название</th>
                              <th>Дата начала</th>
                              <th>Дата окончания</th>
                              <th>Исполнитель</th>
                              <th>Статус</th>
                              <th width="20%"></th>
                          </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${tasks}" var="task">
                        <tr>
                            <td>${task.id}</td>
                            <td>${task.projectShortName}</td>
                            <td>${task.name}</td>
                            <td>${task.startDate}</td>
                            <td>${task.endDate}</td>
                            <td>${task.performer}</td>
                            <td>${task.status}</td>
                            <td>
                                <a href="editTask?id=${task.id}"><button type="button" class="btn btn-success custom-width">Изменить</button></a>  <a href="removeTask?id=${task.id}"><button type="button" class="btn btn-danger custom-width">Удалить</button></a>
                            </td>
                        </tr>
                    <tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
    <a href="addTask"><button type="button" class="btn btn-success custom-width">Добавить</button></a>
    </div>
</div>
</body>
</html>