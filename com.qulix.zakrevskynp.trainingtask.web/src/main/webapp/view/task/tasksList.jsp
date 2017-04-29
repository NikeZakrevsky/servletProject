<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <title>Simple jsp page</title>
</head>
<body>
<%@ include file="../share/navigationBar.jsp" %>
<span style="color: red; ">${requestScope.error}</span>
<div class="panel panel-default">
    <div class="panel-heading"><span class="lead">Список задач</span></div>
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
            <c:forEach items="${requestScope.tasks}" var="task">
            <tr>
                <td>${task.id}</td>
                <td>${task.projectShortName}</td>
                <td>${task.name}</td>
                <td>${task.startDate}</td>
                <td>${task.endDate}</td>
                <td>${task.person.firstName} ${task.person.middleName} ${task.person.lastName}</td>
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
</body>
</html>