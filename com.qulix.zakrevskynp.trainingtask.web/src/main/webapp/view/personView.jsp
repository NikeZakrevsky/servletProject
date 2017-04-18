<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta charset=utf-8">
	<title>Plugin tester</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>

<body>
    <%@ include file="navigationBar.jsp" %>
	<div class="generic-container">
    <div class="panel panel-default">
    <div class="panel-heading"><span class="lead">Управление сотрудниками</span></div>
    <div class="formcontainer">
	<form action = "${action}" method = "POST">
        <%@ include file="errors.jsp" %>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Имя</label>
                <div class="col-md-7">
                    <input type = "text" name = "id" size = "70" value = "${person.id}" readonly/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Имя</label>
                <div class="col-md-7">
                    <input type = "text" name = "firstName" size = "70" maxlength="20" value="${person.firstName}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Фамилия</label>
                <div class="col-md-7">
                    <input type = "text" name = "middleName" size = "70" maxlength="20" value="${person.middleName}"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable">Отчество</label>
                <div class="col-md-7">
                    <input type = "text" name = "lastName" size = "70" maxlength="20" value="${person.lastName}"/>
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