<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<html>
<body>
    <h1>Добавить запись</h1>
    <p>Введите данные записи, которую нужно добавить</p>
    <form method="post" action="insert">
        Номер записи: <input name="num"><br>
        Сумма: <input name="value"><br>
        Дата: <input name="paydate"><br>
        Получатель: <input name="receiver"><br>
        <input type="submit">
    </form>
</body>
</html>