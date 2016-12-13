<%--
  Created by IntelliJ IDEA.
  User: Inso
  Date: 10.12.2016
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://util.topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<h3><a href="meals?action=create">Add Meal</a></h3>


<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>

    </tr>

    <%--@elvariable id="mealWithExceedList" type="java.util.List"--%>
    <c:forEach items="${mealWithExceedList}" var="mealWithExceed">


        <c:choose>
            <c:when test="${mealWithExceed.isExceed()}">
                <tr style="color: red">
                    <td>${f:matches(mealWithExceed.getDateTime(), 'dd MMMM yyyy HH:mm')}</td>
                    <td align="center">${mealWithExceed.getDescription()}</td>
                    <td align="center">${mealWithExceed.getCalories()}</td>
                    <td><a href="meals?action=update&id=${mealWithExceed.getId()}">Update</a></td>
                    <td><a href="meals?action=remove&id=${mealWithExceed.getId()}">Remove</a></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color: green">
                    <td>${f:matches(mealWithExceed.getDateTime(), 'dd MMMM yyyy HH:mm')}</td>
                    <td align="center">${mealWithExceed.getDescription()}</td>
                    <td align="center">${mealWithExceed.getCalories()}</td>
                    <td><a href="meals?action=update&id=${mealWithExceed.getId()}">Update</a></td>
                    <td><a href="meals?action=remove&id=${mealWithExceed.getId()}">Remove</a></td>
                </tr>
            </c:otherwise>
        </c:choose>


    </c:forEach>

</table>
</body>
</html>
