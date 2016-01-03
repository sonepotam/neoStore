<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<h2>Petstore REST Application</h2>
<hr>
   <br>
   <h4>Ой : </h4>
   <c:if test="${not empty jsonexception}">
       <hr>
       <p>сообщение в формате json</p>
       <h2>${jsonexception}</h2>
       <hr>
   </c:if>
    <h2>${exception.message}</h2>
    <h2>${userTo}</h2>
    <!--
        <c:forEach items="${exception.stackTrace}" var="stackTrace">
         ${stackTrace}
     </c:forEach>
     -->
<hr>
   Полная информация об ошибке доступна по Ctrl+U
<hr>

</body>
</html>