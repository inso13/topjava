<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>User list</h2>
<%--@elvariable id="users" type="java.util.List"--%>
<c:forEach items="${users}" var="user">
    <h3>Name: ${user.name}; E-mail: ${user.email}; Password:  ${user.password};</h3>
</c:forEach>
</body>
</html>
