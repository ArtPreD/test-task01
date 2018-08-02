<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<style>
    .section {
        background-color: #fff;
        padding: 15px;
        margin-bottom: 10px;
        border-radius: 10px;
    }
</style>

<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</head>
<body style="background:#CCCCFF">
<div style="margin-top: 30px" class="container col-10">

<div class="section">
<h4 align="center">Редактирование <c:if test="${isDep}">департамента "${dep.name}"</c:if><c:if test="${isCoop}">сотрудника - ${coop.name}</c:if></h4>
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
                <button class="btn btn-danger mt-2 ml-2" onclick="window.location.href='/list?id=${depId}'">Отмена</button>
            </c:if>
        </div>
</div>

</div>
</body>
</html>