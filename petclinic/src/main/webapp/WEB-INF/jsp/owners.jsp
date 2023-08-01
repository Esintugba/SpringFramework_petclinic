<%--
  Created by IntelliJ IDEA.
  User: esint
  Date: 27.07.2023
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        thead tr {
            font-weight: bold;
            background-color: #007bff;
            color: #fff;
        }

        tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .message {
            margin-top: 20px;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<table>
    <thead>
    <tr style="font-weight: bold;" bgcolor="lightblue">
        <td>ID</td>
        <td>First Name</td>
        <td>Last Name</td>
    </tr>
    </thead>
    <c:forEach items="${owners}" var="owner" varStatus="status">
        <tr bgcolor=${status.index % 2 == 0?'white':'lightgray'}>
            <td>${owner.id}</td>
            <td>${owner.firstName}</td>
            <td>${owner.lastName}</td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty message}">
    <div style="color: blue;">
            ${message}
    </div>
</c:if>

</body>
</html>
