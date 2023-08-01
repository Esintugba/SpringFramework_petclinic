<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
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
<h1>PetClinic Login Page</h1>
<form action="login" method="post">
    Username:<input name="username" type="text" /><br /> Password:<input
        name="password" type="password" /><br /> <input
        type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    Remember Me:<input name="remember-me" type="checkbox"><br />
    <input type="submit" value="Login">
</form>
<font color="red"> <c:if test="${not empty param.loginFailed}">
        <c:out value="Login Failed, Incorrect Username or Password"></c:out>
    </c:if>
</body>
</html>