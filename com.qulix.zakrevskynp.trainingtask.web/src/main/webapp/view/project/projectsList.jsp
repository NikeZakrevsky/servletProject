<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <title>Simple jsp page</title>
</head>
<body>
<%@ include file="../share/navigationBar.jsp" %>
<span style="color: red; ">${requestScope.error}</span>
<div class="panel panel-default">
    <div class="panel-heading"><span class="lead">Список проектов</span></div>
    <div class="tablecontainer">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Идентификатор</th>
                <th>Название</th>
                <th>Сокращенное название</th>
                <th>Описание</th>
                <th width="20%"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.projects}" var="project">
            <tr>
                <td>${project.id}</td>
                <td>${project.name}</td>
                <td>${project.shortName}</td>
                <td>${project.description}</td>
                <td>
                    <a href="editProject?id=${project.id}"><button type="button" class="btn btn-success custom-width">Изменить</button></a>  <a href="removeProject?id=${project.id}"><button type="button" class="btn btn-danger custom-width">Удалить</button></a>
                </td>
            </tr>
            </c:forEach>
            <tbody>
        </table>
    </div>
</div>
<a href="addProject"><button type="button" class="btn btn-success custom-width">Добавить</button></a>
</body>
</html>