<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.meal"--%>
<%--
  Created by IntelliJ IDEA.
  User: Котик
  Date: 13.12.2016
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add or update meals</title>
</head>
<body>
<h2>Add or edit meal</h2>
<form method="post" action="meals">

   Description : <input type="text" name="description " value="${meal.description}"
                      /> <br /> </form>
</body>
</html>
