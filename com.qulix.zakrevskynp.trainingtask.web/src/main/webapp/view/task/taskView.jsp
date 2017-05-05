<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="../share/head.jsp" %>
<body>
    <%@ include file="../share/navigationBar.jsp" %>
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="lead">Управление задачами</span>
        </div>
	    <form action = "${requestScope.action}" method = "POST" novalidate>
            <%@ include file="../share/errors.jsp" %>
            
            <label class="col-md-2">Идентификатор</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" name = "id" size = "70" maxlength="20" value = "${requestScope.task.id}" readonly/>
                </div>
            </div>

            <label class="col-md-2">Название</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "text" name = "name" size = "70" maxlength="20" value = "${requestScope.task.name}"/>
                </div>
            </div>

            <label class="col-md-2">Работа(часы)</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "number" name = "workTime" size = "70" min = "0" max = "999" step = "0.1" value = "${requestScope.task.workTime != null ? requestScope.task.workTime.toMinutes() / 60 : ""}"/>
                </div>
            </div>

            <label class="col-md-2">Дата начала</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "date" name = "startDate" size = "70"  value = "${requestScope.task.startDate}"/>
                </div>
            </div>

            <label class="col-md-2">Дата окончания</label>
            <div class="row">
                <div class="col-md-3">
                    <input class="form-group col-md-12" type = "date" name = "endDate" size = "70" value = "${requestScope.task.endDate}"/>
                </div>
            </div>

            <label class="col-md-2">Исполнитель</label>
            <div class="row form-group">
                <div class="col-md-3">
                    <select name = "personId">
                        <option value> -- select an option -- </option>
                        <c:forEach items="${requestScope.persons}" var="person">
                            <option value="${person.id}" ${(person.id == requestScope.task.person.id) ? 'selected="selected"' : ''}>${person.firstName} ${person.middleName} ${person.lastName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <label class="col-md-2">Статус</label>
            <div class="row form-group">
                <div class="col-md-3">
                    <select name = "taskStatus">
                        <option value="Не начата" ${"Не начата".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>Не начата</option>
                        <option value="В процессе" ${"В процессе".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>В процессе</option>
                        <option value="Завершена" ${"Завершена".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>Завершена</option>
                        <option value="Отложена" ${"Отложена".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>Отложена</option>
                    </select>
                </div>
            </div>
            <input type="hidden" name = "projectId" value = "${requestScope.task.projectId}">
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2">Проект</label>
                    <div class="col-md-3">
                        <select id = "lol" name="projectId1" ${requestScope.isDisable ? 'disabled="true"' : ''}>
                            <option value="" selected="selected"> -- select an option -- </option>
                            <c:forEach items="${requestScope.projects}" var="project">
                                <option value="${project.id}" ${(project.id == requestScope.task.projectId) ? 'selected="selected"' : ''}>${project.shortName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <button type="submit" class="btn btn-success col-md-2">Сохранить</button>
                <a href="${sessionScope.path}"><button type="button" class="btn btn-danger col-md-2">Отмена</button></a>
            </div>
        </form>
    </div>
</body>
</html>