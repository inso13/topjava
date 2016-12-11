<%--
  Created by IntelliJ IDEA.
  User: Inso
  Date: 10.12.2016
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>


<table>
    <tr>
        <th style="border: groove">Дата</th>
        <th style="border: groove">Время</th>
        <th style="border: groove">Описание</th>
        <th style="border: groove">Калории</th>

    </tr>

    <%--@elvariable id="mealWithExceedList" type="java.util.List"--%>
    <c:forEach items="${mealWithExceedList}" var="mealWithExceed">


        <c:choose>
            <c:when test="${mealWithExceed.isExceed()}">
                <tr style="color: red">
                    <td align="center">${mealWithExceed.getDateTime().toLocalDate()}</td>
                    <td align="center">${mealWithExceed.getDateTime().toLocalTime()}</td>
                    <td align="center">${mealWithExceed.getDescription()}</td>
                    <td align="center">${mealWithExceed.getCalories()}</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color: green">
                    <td align="center">${mealWithExceed.getDateTime().toLocalDate()}</td>
                    <td align="center">${mealWithExceed.getDateTime().toLocalTime()}</td>
                    <td align="center">${mealWithExceed.getDescription()}</td>
                    <td align="center">${mealWithExceed.getCalories()}</td>
                </tr>
            </c:otherwise>
        </c:choose>


    </c:forEach>

</table>
</body>
</html>
