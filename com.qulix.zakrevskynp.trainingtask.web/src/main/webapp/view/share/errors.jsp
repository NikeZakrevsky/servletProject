<div class="form-group col-md-12">
    <c:forEach items="${requestScope.errors}" var="error">
        ${error}<br>
    </c:forEach>
</div>