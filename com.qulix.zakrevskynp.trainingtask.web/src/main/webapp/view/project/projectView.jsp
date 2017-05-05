<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="../share/head.jsp" %>
<body>
    <%@ include file="../share/navigationBar.jsp" %>
    <span class="lead panel-heading">Управление проектами</span>
    <div class="formcontainer">
        <form action = "${requestScope.action}" method = "POST">
            <%@ include file="../share/errors.jsp" %>

            <label class="col-md-2">Идентификатор</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "text" name = "id" size = "65" value = "${requestScope.project.id}" readonly/>
                </div>
            </div>

            <label class="col-md-2">Название</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "text" name = "name" size = "65" maxlength="20" value = "${requestScope.project.name}"/>
                </div>
            </div>

            <label class="col-md-2">Сокращенное название</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "text" name = "shortName" size = "65" maxlength="20" value = "${requestScope.project.shortName}"/>
                </div>
            </div>

            <label class="col-md-2">Описание</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "text" name = "description" size = "65" maxlength="20" value = "${requestScope.project.description}"/>
                </div>
            </div>

             <div class="tablecontainer">
                 <span class="lead panel-heading">Список задач</span>
                 <table class="table table-hover">
                     <%@ include file="../share/tasksHeader.jsp" %>
                     <tbody>
                        <c:forEach items="${requestScope.project.tasks}" var="task">
                            <tr>
                                <%@ include file="../share/tasksContent.jsp" %>
                                <td>
                                    <button onclick="this.form.action = 'removeTaskProject?taskId=${task.id}&id=${requestScope.project.id}'" type="submit" class="btn btn-danger custom-width">Удалить</button>
                                    <button onclick = "this.form.action = 'editTaskProject1?taskId=${task.id}'" type="submit" class="btn btn-success custom-width">Изменить</button>
                                </td>
                            </tr>
                        </c:forEach>
                     <tbody>
                 </table>
             </div>
            <div style = "margin-bottom: 20px;">
                <c:choose>
                    <c:when test="${requestScope.action.equals('editProject')}">
                        <button onclick = "this.form.action = 'taskProject1?projectId=${requestScope.project.id}'" type="submit" class="btn btn-success custom-width">Добавить</button>
                    </c:when>
                    <c:otherwise>
                        <button onclick = "this.form.action = 'taskProject1'" type="submit" class="btn btn-success custom-width">Добавить</button>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="row">
                <button type="submit" class="btn btn-success col-md-1">Сохранить</button>
                <a href="projectsList"><button type="button" class="btn btn-danger col-md-1">Отмена</button></a>
            </div>
        </form>
    </div>
</body>
</html>