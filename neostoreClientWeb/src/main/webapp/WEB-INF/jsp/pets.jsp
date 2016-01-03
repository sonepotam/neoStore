<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Животинка</title>
</head>
<body>
<section>
    <h3>Животинка пользователя <c:out value="${username}" /> </h3>
<hr>
    <table border="0" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Кличка</th>
            <th>Порода</th>
            <th>Дата рождения</th>
            <th>пол</th>
        </tr>
        </thead>
        <c:forEach items="${pets}" var="pet">
            <jsp:useBean id="pet" scope="page" type="ru.pavel2107.neostoreRest.model.Pet"/>
            <tr>
                <td><a href="pet?action=update&id=${pet.id}&userid=${userid}">${pet.name}</a></td>
                <td>
                    <c:forEach items="${breeds}" var="br">
                        <jsp:useBean id="br" scope="page" type="ru.pavel2107.neostoreRest.model.Breed"/>
                        <c:if test="${br.id == pet.breed}">
                            ${br.name}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${pet.birthDate}</td>
                <td>${pet.sex}</td>
                <td><a href="pets?action=delete&id=${pet.id}&userid=${userid}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
    <hr>
    <a href="pet?action=create&userid=${userid}">Купить скотинку</a>
    <hr>
</section>
</body>
</html>