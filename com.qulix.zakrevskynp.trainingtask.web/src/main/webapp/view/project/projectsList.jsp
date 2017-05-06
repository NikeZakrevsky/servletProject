<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="../share/head.jsp" %>
<body>
    <%@ include file="../share/navigationBar.jsp" %>
    <div class="panel-heading">
        <span class="lead">Список проектов</span>
    </div>
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
    <a href="addProject"><button type="button" class="btn btn-success custom-width">Добавить</button></a>
</body>
</html>