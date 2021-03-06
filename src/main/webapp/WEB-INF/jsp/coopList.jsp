<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<%@ include file="/WEB-INF/jsp/template/head.jsp" %>

<body style="background:#CCCCFF">

<div style="margin-top: 30px" class="container col-10">

    <div class="section">
        <div align="center">
            <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample"
                    aria-expanded="false" aria-controls="collapseExample">
                Добавить сотрудника
            </button>
        </div>
        <div class="collapse<c:if test="${error}"> show</c:if>" id="collapseExample">
            <form method="post">
                <c:if test="${error}">
                    <div class="alert alert-danger mt-3" role="alert">
                            ${errorMessage}
                    </div>
                </c:if>
                <label for="name">Имя сотрудника</label>
                <input class="form-control" type="text" name="name" id="name"
                       <c:if test="${error}">value="${name}"</c:if>
                       placeholder="Введите имя" required>
                <label for="email">Email</label>
                <input class="form-control" type="email" name="email"
                       <c:if test="${error}">value="${email}"</c:if>
                       id="email" placeholder="Введите email" required>
                <label for="salary">Оклад</label>
                <input class="form-control" type="text" name="salary"
                       <c:if test="${error}">value="${salary}"</c:if>
                       id="salary" placeholder="Введите оклад" required>
                <label for="date">Дата приема на работу</label>
                <input class="form-control" type="date" name="date" id="date" value="${date}" required>
                <input type="hidden" name="depid" id="depid" value="${dep.id}">
                <button class="btn btn-primary mt-2">Добавить</button>
            </form>
        </div>
    </div>

    <div class="section">
        <h1 align="center"><span class="about">Список сотрудников департамента "${dep.name}"</span></h1>
        <c:if test="${coopList != null}">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Имя</th>
                <th scope="col">Email</th>
                <th scope="col">Оклад</th>
                <th scope="col">Дата найма</th>
                <th scope="col">Редактировать</th>
                <th scope="col">Удалить</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${coopList}" var="coop">
                <tr>
                    <td scope="row">${coop.id}</td>
                    <td>${coop.name}</td>
                    <td>${coop.email}</td>
                    <td>${coop.salary}</td>
                    <td>${coop.date}</td>
                    <td class="but">
                        <button class="btn btn-primary"
                                onclick="window.location.href='/edit?q=coop&id=${coop.id}&depid=${dep.id}'">
                            Редактировать
                        </button>
                    </td>
                    <td class="but">
                        <button class="btn btn-danger"
                                onclick="window.location.href='/list?action=delete&id=${coop.id}&depid=${dep.id}'">
                            Удалить
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            </c:if>
            <c:if test="${coopList == null}">
                <h4 align="center">В департаменте нет ни одного сотрудника :(</h4>
            </c:if>
        </table>
    </div>

    <div class="section">
        <div align="center">
            <button class="btn btn-success" type="button" onclick="window.location.href='/'">Вернуться назад</button>
        </div>
    </div>
</div>

</body>
</html>
