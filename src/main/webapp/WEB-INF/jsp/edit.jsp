<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<%@ include file="/WEB-INF/jsp/template/head.jsp" %>

<body style="background:#CCCCFF">
<div style="margin-top: 30px" class="container col-10">

    <div class="section">
        <h4 align="center">Редактирование <c:if test="${isDep}">департамента "${dep.name}"</c:if><c:if
                test="${isCoop}">сотрудника - ${coop.name}</c:if></h4>
    </div>

    <div class="section">
        <c:if test="${error}">
            <div class="alert alert-danger mt-3" role="alert">
                    ${errorMessage}
            </div>
        </c:if>
        <form id="data" method="post">
            <c:if test="${isDep}">
                <label for="name">Введите новое название департамента</label>
                <input class="form-control" type="name" name="name" id="name" value="${dep.name}" required>
            </c:if>
            <c:if test="${isCoop}">
                <label for="name">Введите новое данные сотрудника</label>
                <input class="form-control" type="name" name="name" id="name" value="${name}" required>
                <label for="email">Email</label>
                <input class="form-control" type="email" name="email" value="${email}" id="email" required>
                <label for="salary">Оклад</label>
                <input class="form-control" type="text" name="salary" value="${salary}" id="salary" required>
                <label for="date">Дата приема на работу</label>
                <input class="form-control" type="date" name="date" id="date" value="${date}" placeholder="" required>
                <input type="hidden" name="depid" id="depid" value="${dep.id}">
            </c:if>
        </form>
        <div class="row ml-1">
            <button class="btn btn-success mt-2" form="data">Сохранить</button>
            <c:if test="${isDep}">
                <button class="btn btn-danger mt-2 ml-2" onclick="window.location.href='/'">Отмена</button>
            </c:if>
            <c:if test="${isCoop}">
                <button class="btn btn-danger mt-2 ml-2" onclick="window.location.href='/list?id=${depId}'">Отмена
                </button>
            </c:if>
        </div>
    </div>

</div>
</body>
</html>