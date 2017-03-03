<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <title>Simple jsp page</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">TaskManager</a>                     
        </div>
        <ul class="nav navbar-nav">
            <li><a href="projectsList">Проекты</a></li>
            <li><a href="tasksList">Задачи</a></li>
            <li><a href="personsList">Персоны</a></li>
        </ul>
    </div>
</nav>
<span style="color: red; ">${error}</span></br>
<div class="generic-container">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Список сотрудников</span></div>
            <div class="tablecontainer">
                <table class="table table-hover">
                    <thead>
                          <tr>
                              <th>Идентификатор</th>
                              <th>Имя</th>
                              <th>Фамилия</th>
                              <th>Отчество</th>
                              <th>Должность</th>
                              <th width="20%"></th>
                          </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${persons}" var="person">
                        <tr>
                            <td>${person.id}</td>
                            <td>${person.fname}</td>
                            <td>${person.sname}</td>
                            <td>${person.lname}</td>
                            <td>${person.position}</td>
                            <td>
                                <a href="editPerson?id=${person.id}"><button type="button" class="btn btn-success custom-width">Изменить</button></a>  <a href="removePerson?id=${person.id}"><button type="button" class="btn btn-danger custom-width">Удалить</button></a>
                            </td>
                        </tr>
                    <tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
    <a href="addPerson"><button type="button" class="btn btn-success custom-width">Добавить</button></a>
    </div>
</div>
</body>
</html>