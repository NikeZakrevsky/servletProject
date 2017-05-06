<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="../share/head.jsp" %>
<body>
    <%@ include file="../share/navigationBar.jsp" %>
    <span class = "error">${requestScope.error}</span>
    <div class="panel-heading">
        <span class="lead">Список задач</span>
    </div>
    <table class="table table-hover">
        <%@ include file="../share/tasksHeader.jsp" %>
        <tbody>
            <c:forEach items="${requestScope.tasks}" var="task">
                <tr>
                    <%@ include file="../share/tasksContent.jsp" %>
                    <td>
                        <a href="editTask?id=${task.id}"><button type="button" class="btn btn-success custom-width">Изменить</button></a>  <a href="removeTask?id=${task.id}"><button type="button" class="btn btn-danger custom-width">Удалить</button></a>
                    </td>
                </tr>
            </c:forEach>
        <tbody>
    </table>
    <a href="addTask"><button type="button" class="btn btn-success custom-width">Добавить</button></a>
</body>
</html>