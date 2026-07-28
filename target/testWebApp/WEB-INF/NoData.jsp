<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html><head><title>Error</title></head>
<body><h1>Вы ввели не все необходимые данные или записи с таким номером не существует.</h1>
<a href="<c:url value='/delete'/>">Назад</a>
</body></html>