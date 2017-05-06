<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="../share/head.jsp" %>
<body>
    <%@ include file="../share/navigationBar.jsp" %>
    <span class="lead panel-heading">Управление сотрудниками</span>
    <form action = "${requestScope.action}" method = "POST">
        <%@ include file="../share/errors.jsp" %>

        <label class="col-md-1">Идентификатор</label>
        <div class="row">
            <div class="col-md-3">
                <input class="form-group col-md-12" type = "text" name = "id" size = "70" value = "${requestScope.person.id}" readonly/>
            </div>
        </div>

        <label class="col-md-1">Имя</label>
        <div class="row">
            <div class="col-md-3">
                <input class="form-group col-md-12" type = "text" name = "firstName" size = "70" maxlength="20" value="${requestScope.person.firstName}"/>
            </div>
        </div>

        <label class="col-md-1">Фамилия</label>
        <div class="row">
            <div class="col-md-3">
                <input class="form-group col-md-12" type = "text" name = "middleName" size = "70" maxlength="20" value="${requestScope.person.middleName}"/>
            </div>
        </div>

        <label class="col-md-1">Отчество</label>
        <div class="row">
            <div class="col-md-3">
                <input class="form-group col-md-12" type = "text" name = "lastName" size = "70" maxlength="20" value="${requestScope.person.lastName}"/>
            </div>
        </div>

        <label class="col-md-1">Должность</label>
        <div class="row">
            <div class="col-md-3">
                <input class="form-group col-md-12" type = "text" name = "position" size = "70" maxlength="60" value="${requestScope.person.position}"/>
            </div>
        </div>

        <div class="row">
            <button type="submit" class="btn btn-success col-md-2">Сохранить</button>
            <a href="personsList"><button type="button" class="btn btn-danger col-md-2">Отмена</button></a>
        </div>
    </form>
</body>
</html>