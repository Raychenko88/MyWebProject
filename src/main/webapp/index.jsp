<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My web application</title>
</head>
<body>
    <h2>Hello, this is the start page</h2>
<br>

    <form action="user" method="post">
        <input type="text" name="action" value="login" hidden>
        <input type="text" size="40" name="login" placeholder="Input login" required>
        <br>
        <input type="password" size="40" name="password" placeholder="Input password" required>
        <br>
        <input type="submit" value="LOGIN">
    </form>
<br>
    <h3>
        <a href="/WebWithJSTLMA45_war/registration.jsp">REGISTER NEW USER</a>
    </h3>
</body>
</html>