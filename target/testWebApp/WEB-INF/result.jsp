<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!-- /WEB-INF/result.jsp -->
<!-- <html>
<body>
    <h1>Платежи</h1>
    <p>${table}</p>
</body>
</html>-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
    <h1>Платежи</h1>
    <table border="1">
        <tr>
            <th>№</th>
            <th>Сумма</th>
            <th>Получатель</th>
            <th>Дата</th>
        </tr>
        <c:forEach var="row" items="${rows}">
            <tr>
                <td>${row[0]}</td>
                <td>${row[1]}</td>
                <td>${row[2]}</td>
                <td>${row[3]}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>