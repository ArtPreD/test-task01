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

<div class="container col-10">
    <p align="center">
        Есть сотрудники и департаменты. У департамента может быть много сотрудников. А может и не бюыть.
        Есть список департаментов. И есть кнопки "Добавить\Редактировать\Удалить\Список сотрудников".
        При нажатии на "Список" показываются сотрудники этого департамента с теми же кнопками.
        Список - таблица, страница добавления\редактирования - набор текстфилдов.
    </p>

    <div class="section">
        <div align="center">
        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample"
                aria-expanded="false" aria-controls="collapseExample">
            Добавить департамент
        </button>
        </div>
        <div class="collapse<c:if test="${error}"> show</c:if>" id="collapseExample">
            <form method="post">
                <c:if test="${error}">
                <div class="alert alert-danger mt-3" role="alert">
                    Департамент с таким именем уже существует!
                </div>
                </c:if>
                <label for="name">Название департамента</label>
                <input class="form-control" type="text" name="name" <c:if test="${error}">value="${name}"</c:if>
                       id="name" placeholder="Введите название" required>
                <button class="btn btn-primary mt-2">Добавить</button>
            </form>
        </div>
    </div>

    <div class="section">
        <h1 align="center"><span class="about">Список департаментов</span></h1>
        <c:if test="${depList != null}">
<table class="table">
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Имя</th>
        <th scope="col">Редактировать</th>
        <th scope="col">Удалить</th>
        <th scope="col">Список</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${depList}" var="dep" >
        <tr>
        <td scope="row">${dep.id}</td>
        <td>${dep.name}</td>
        <td class="but"><button class="btn btn-primary" onclick="window.location.href='/edit?q=dep&id=${dep.id}'">Редактировать</button></td>
        <td class="but"><button class="btn btn-danger" onclick="window.location.href='/?action=delete&id=${dep.id}'">Удалить</button></td>
        <td class="but"><button class="btn btn-success" onclick="window.location.href='/list?id=${dep.id}'">Список</button></td>
        </tr>
    </c:forEach>
    </tbody>
    </c:if>
    <c:if test="${depList == null}">
        <h4 align="center">Еще нет ни одного департамента :(</h4>
    </c:if>
</table>
</div>
</div>

</body>
</html>
