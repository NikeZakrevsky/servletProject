<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <title>Simple jsp page</title>
</head>
<body>
<%@ include file="../share/navigationBar.jsp" %>
<span style="color: red; ">${error}</span></br>
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
                <td>${person.firstName}</td>
                <td>${person.middleName}</td>
                <td>${person.lastName}</td>
                <td>${person.position}</td>
                <td>
                    <a href="editPerson?id=${person.id}"><button type="button" class="btn btn-success custom-width">Изменить</button></a>  <a href="removePerson?id=${person.id}"><button type="button" class="btn btn-danger custom-width">Удалить</button></a>
                </td>
            </tr>
            </c:forEach>
            <tbody>
        </table>
    </div>
</div>
<a href="addPerson"><button type="button" class="btn btn-success custom-width">Добавить</button></a>
</body>
</html>