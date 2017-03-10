<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Plugin tester</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style.css">

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
    <div class="generic-container">
    <div class="panel panel-default">
    <div class="panel-heading"><span class="lead">Управление задачами</span></div>
	<form onsubmit="document.getElementById('lol').disabled = false;" action = "${action}" method = "POST">
        <div class="form-group col-md-12">
            <c:forEach items="${errors}" var="error">
                <span style="color: red; ">${error}</span></br>
            </c:forEach>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Идентификатор</label>
                <div class="col-md-7">
                    <input name = "id" size = "70" maxlength="20" value = "${task.id}" readonly/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
            <label class="col-md-2 control-lable">Название</label>
                <div class="col-md-7">
                    <input type = "text" name = "name" size = "70" maxlength="20" value = "${task.name}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
            <label class="col-md-2 control-lable">Работа(часы)</label>
                <div class="col-md-7">
	                <input type = "number" name = "time" size = "70" value = "${task.time}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
            <label class="col-md-2 control-lable">Дата начала</label>
                <div class="col-md-7">
	                <input type = "date" name = "startDate" size = "70"  value = "${task.startDate}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
            <label class="col-md-2 control-lable">Дата окончания</label>
                <div class="col-md-7">
	                <input type = "date" name = "endDate" size = "70" value = "${task.endDate}"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Исполнитель</label>
                <div class="col-md-7">
                <select name = "personId">
                    <option value> -- select an option -- </option>
                    <c:forEach items="${personsList}" var="person">
                        <option value="${person.id}" ${(person.id == task.personId) ? 'selected="selected"' : ''}>${person.fname} ${person.sname} ${person.lname}</option>
                    </c:forEach>
                </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
            <label class="col-md-2 control-lable">Статус</label>
                <div class="col-md-7">
	                <select name = "status">
                        <option value="Not started" ${"Not started".equals(task.status) ? 'selected="selected"' : ''}>Not started</option>
                        <option value="In process" ${"In process".equals(task.status) ? 'selected="selected"' : ''}>In process</option>
                        <option value="Completed" ${"Completed".equals(task.status) ? 'selected="selected"' : ''}>Completed</option>
                        <option value="Delayed" ${"Delayed".equals(task.status) ? 'selected="selected"' : ''}>Delayed</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
            <label class="col-md-2 control-lable">Проект</label>
                <div class="col-md-7">
                    <select id = "lol" name="projectId" ${isDisable ? 'disabled="true"' : ''}>
                        <option value selected="selected"> -- select an option -- </option>
                        <c:forEach items="${projectsList}" var="project">
                            <option value="${project.id}" ${(project.id == task.projectId) ? 'selected="selected"' : ''}>${project.shortName}</option>
                        </c:forEach>
                    </select>

                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-success custom-width">Сохранить</button>
        <a href="${path}"><button type="button" class="btn btn-danger custom-width">Отмена</button></a>
    </form>
</div>
</div>
</body>
</html>