<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../share/head.jsp" %>
<body>
    <%@ include file="../share/navigationBar.jsp" %>
    ${requestScope.error}
    <div class="panel-heading">
        <span class="lead">Список сотрудников</span>
    </div>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Идентификатор</th>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Отчество</th>
                <th>Должность</th>
                <th width="20%"></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${requestScope.persons}" var="person">
                <tr>
                    <td>${person.id}</td>
                    <td>${person.firstName}</td>
                    <td>${person.middleName}</td>
                    <td>${person.lastName}</td>
                    <td>${person.position}</td>
                    <td>
                        <a href="editPerson?id=${person.id}">
                            <button type="button" class="btn btn-success custom-width">Изменить</button>
                        </a>
                        <a href="removePerson?id=${person.id}">
                            <button type="button" class="btn btn-danger custom-width">Удалить</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        <tbody>
    </table>
    <a href="addPerson"><button type="button" class="btn btn-success custom-width">Добавить</button></a>
</body>