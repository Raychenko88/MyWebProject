<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Store</title>
</head>
<body>
<h2><c:out value="${errorMsg}"/></h2>
<br>

<h3>
    Hello Admin
</h3>


<form action="item-admin" method="post">
    <input type="text" name="action" value="add-item" hidden>
    <input type="text" size="40" name="item-code" placeholder="Item code" required>
    <br>
    <input type="text" size="40" name="name" placeholder="Name" required>
    <br>
    <input type="text" size="40" name="price" placeholder="Price" required>
    <br>
    <input type="text" size="40" name="availability" placeholder="Availability" required>
    <br>
    <input type="submit" value="Add item">
</form>

<br>
<form action="item-admin" method="post">
    <input type="text" name="action" value="delete-item" hidden>
    <input type="text" size="40" name="item-code" placeholder="Item code" required>
    <br>
    <input type="submit" value="Delete item">
</form>

<br>
<br>
<h3>
    <a href="/WebWithJSTL">Back to registration</a>
</h3>
</body>
</html>


