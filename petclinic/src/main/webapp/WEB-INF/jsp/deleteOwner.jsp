<%--
  Created by IntelliJ IDEA.
  User: esint
  Date: 31.07.2023
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>PetClinic Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        .login-container {
            max-width: 400px;
            margin: 100px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .login-container h1 {
            text-align: center;
            color: #333;
        }

        .login-form {
            margin-top: 20px;
        }

        .login-form label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        .login-form input[type="text"],
        .login-form input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 3px;
            font-size: 16px;
        }

        .login-form input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 3px;
            font-size: 16px;
            cursor: pointer;
        }

        .login-form .error-message {
            color: red;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h1>PetClinic Login Page</h1>
    <form class="login-form" action="login" method="post">
        <%--@declare id="username"--%><%--@declare id="password"--%><%--@declare id="remember-me"--%>
        <label for="username">Username:</label>
        <input name="username" type="text" required><br>
        <label for="password">Password:</label>
        <input name="password" type="password" required><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <label for="remember-me">Remember Me:</label>
        <input name="remember-me" type="checkbox"><br>
        <input type="submit" value="Login">
    </form>
    <div class="error-message">
        <c:if test="${not empty param.loginFailed}">
            Login Failed, Incorrect Username or Password
        </c:if>
    </div>
</div>
</body>
</html>