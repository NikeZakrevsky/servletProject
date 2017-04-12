<%@ page language="java" contentType="text/html; charset=windows-1251"
	pageEncoding="windows-1251"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <title>Simple jsp page</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Task manager</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="projectsList">�������</a></li>
            <li><a href="tasksList">������</a></li>
            <li><a href="personsList">�������</a></li>
        </ul>
    </div>
</nav>
<div class="generic-container">
    <span style="color: red; ">${error}</span></br>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">������ ��������</span></div>
            <div class="tablecontainer">
                <table class="table table-hover">
                    <thead>
                          <tr>
                              <th>�������������</th>
                              <th>��������</th>
                              <th>����������� ��������</th>
                              <th>��������</th>
                              <th width="20%"></th>
                          </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${projects}" var="project">
                        <tr>
                            <td>${project.id}</td>
                            <td>${project.name}</td>
                            <td>${project.shortName}</td>
                            <td>${project.description}</td>
                            <td>
                                <a href="editProject?id=${project.id}"><button type="button" class="btn btn-success custom-width">��������</button></a>  <a href="removeProject?id=${project.id}"><button type="button" class="btn btn-danger custom-width">�������</button></a>
                            </td>
                        </tr>
                    <tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
    <a href="addProject"><button type="button" class="btn btn-success custom-width">��������</button></a>
    </div>
</div>
</body>
</html>