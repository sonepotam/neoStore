<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>животное</title>
</head>
<body>
<section>
    <h2><a href="pets">назад</a></h2>
    <h3>Редактирование данных о животных. Владелец <c:out value="${username}"/> </h3>
    <hr>
    <jsp:useBean id="pet" type="ru.pavel2107.neostoreRest.model.Pet" scope="request"/>

    <form method="post" action="pets">
    <input type="hidden" name="id" value="${pet.id}">
    <input type="hidden" name="userid" value="${pet.user.id}">

    <table border="0" cellpadding="8" cellspacing="0">
            <tr>
                <td>Кличка</td>
                <td><input type="text" value="${pet.name}" size=40 name="name"></td>
            </tr>
            <tr>
                <td>порода</td>
                <td>
                    <select name="breed">
                        <c:forEach items="${breeds}" var="br">
                            <jsp:useBean id="br" scope="page" type="ru.pavel2107.neostoreRest.model.Breed"/>
                            <option value="${br.id}" ${br.id == pet.breed ? 'selected' : ''}>${br.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>

            <tr>
                <td>Дата рождения</td>
                <td><input type="date" value="${pet.birthDate}" size=40 name="bDate"></td>
            </tr>
                <td>пол</td>
                <td>
                    <p><input type="radio" name="sex" value="M" ${"M".equals( pet.sex) ? 'checked' : ''}/>M</p>
                    <p><input type="radio" name="sex" value="F" ${"F".equals( pet.sex) ? 'checked' : ''}/>F</p>
                </td>
            </tr>
    </table>
    <hr>
     <input type="submit" name="submit" value="Сохранить">
     <input type="submit" name="submit" value="Отказаться" onclick="window.history.back()">
    </form>
</section>
</body>
</html>
