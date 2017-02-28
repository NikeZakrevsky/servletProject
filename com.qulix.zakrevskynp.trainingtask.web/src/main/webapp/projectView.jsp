<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Plugin tester</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>

<body>
    <nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">TrainingWebsite</a>
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
    <div class="panel-heading"><span class="lead">Ввод проекта</span></div>
    <br/>
    <div class="formcontainer">
	    <form action = "${action}" method = "POST">
            <div class="form-group col-md-12">
            <c:forEach items="${errors}" var="error">
                <span style="color: red; ">${error}</span></br>
            </c:forEach>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Идентификатор</label>
                    <div class="col-md-7">
                        <input type = "text" name = "id" size = "65" value = "${project.id}" readonly/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Название</label>
                    <div class="col-md-7">
                        <input type = "text" name = "name" size = "65" maxlength="20" value = "${project.name}"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Сокращенное название</label>
                    <div class="col-md-7">
                        <input type = "text" name = "shortName" size = "65" maxlength="20" value = "${project.shortName}"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable">Описание</label>
                    <div class="col-md-7">
	                    <input type = "text" name = "description" size = "65" maxlength="20" value = "${project.description}"/>
                    </div>
                </div>
            </div>
            <input type = "submit"/>
	    </form>
</div>
</div>
</div>
<div class="generic-container">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Список задач</span></div>
            <div class="tablecontainer">
                <table class="table table-hover">
                    <thead>
                          <tr>
                              <th>Идентификатор</th>
                              <th>Название</th>
                              <th>Дата начала</th>
                              <th>Дата окончания</th>
                              <th>Исполнитель</th>
                              <th>Статус</th>
                              <th width="20%"></th>
                          </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${tasks}" var="task">
                        <tr>
                            <td>${task.id}</td>
                            <td>${task.name}</td>
                            <td>${task.startDate}</td>
                            <td>${task.endDate}</td>
                            <td>${task.performer}</td>
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
</div>
</body>
</html>