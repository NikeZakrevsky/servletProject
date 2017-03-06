<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Plugin tester</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
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
    <div class="panel-heading"><span class="lead">Управление сотрудниками</span></div>
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
                    <input type = "text" name = "id" size = "70" value = "${person.id}" readonly/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Имя</label>
                <div class="col-md-7">
                    <input type = "text" name = "fname" size = "70" maxlength="20" value="${person.fname}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Фамилия</label>
                <div class="col-md-7">
                    <input type = "text" name = "sname" size = "70" maxlength="20" value="${person.sname}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Отчество</label>
                <div class="col-md-7">
                    <input type = "text" name = "lname" size = "70" maxlength="20" value="${person.lname}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
            <label class="col-md-2 control-lable">Должность</label>
                <div class="col-md-7">
	                <input type = "text" name = "position" size = "70" maxlength="20" value="${person.position}"/>
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-success custom-width">Сохранить</button>
        <a href="personsList"><button type="button" class="btn btn-danger custom-width">Отмена</button></a>
	</form>
</div>
</div>
</div>
</body>
</html>