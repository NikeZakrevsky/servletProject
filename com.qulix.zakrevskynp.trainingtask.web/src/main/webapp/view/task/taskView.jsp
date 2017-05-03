<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Plugin tester</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/style.css">
</head>
<body>
    <%@ include file="../share/navigationBar.jsp" %>
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="lead">Управление задачами</span>
        </div>
	    <form action = "${requestScope.action}" method = "POST" novalidate>
            <%@ include file="../share/errors.jsp" %>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Идентификатор</label>
                    <div class="col-md-7">
                        <input name = "id" size = "70" maxlength="20" value = "${requestScope.task.id}" readonly/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Название</label>
                    <div class="col-md-7">
                        <input type = "text" name = "name" size = "70" maxlength="20" value = "${requestScope.task.name}"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Работа(часы)</label>
                    <div class="col-md-7">
                        <input type = "number" name = "workTime" size = "70" min = "0" max = "999" step = "0.1" value = "${requestScope.task.workTime != null ? requestScope.task.workTime.toMinutes() / 60 : ""}"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Дата начала</label>
                    <div class="col-md-7">
	                    <input type = "date" name = "startDate" size = "70"  value = "${requestScope.task.startDate}"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Дата окончания</label>
                    <div class="col-md-7">
                        <input type = "date" name = "endDate" size = "70" value = "${requestScope.task.endDate}"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Исполнитель</label>
                    <div class="col-md-7">
                        <select name = "personId">
                            <option value> -- select an option -- </option>
                            <c:forEach items="${requestScope.persons}" var="person">
                                <option value="${person.id}" ${(person.id == requestScope.task.person.id) ? 'selected="selected"' : ''}>${person.firstName} ${person.middleName} ${person.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Статус</label>
                    <div class="col-md-7">
	                    <select name = "taskStatus">
                            <option value="Не начата" ${"Не начата".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>Не начата</option>
                            <option value="В процессе" ${"В процессе".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>В процессе</option>
                            <option value="Завершена" ${"Завершена".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>Завершена</option>
                            <option value="Отложена" ${"Отложена".equals(requestScope.task.status.toString()) ? 'selected="selected"' : ''}>Отложена</option>
                        </select>
                    </div>
                </div>
            </div>
            <input type="hidden" name = "projectId" value = "${requestScope.task.projectId}">
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Проект</label>
                    <div class="col-md-7">
                        <select id = "lol" name="projectId1" ${requestScope.isDisable ? 'disabled="true"' : ''}>
                            <option value="" selected="selected"> -- select an option -- </option>
                            <c:forEach items="${requestScope.projects}" var="project">
                                <option value="${project.id}" ${(project.id == requestScope.task.projectId) ? 'selected="selected"' : ''}>${project.shortName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-success custom-width">Сохранить</button>
            <a href="${sessionScope.path}"><button type="button" class="btn btn-danger custom-width">Отмена</button></a>
        </form>
    </div>
</body>
</html>