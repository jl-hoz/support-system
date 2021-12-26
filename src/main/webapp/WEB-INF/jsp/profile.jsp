<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>

<%@ include file="templates/nav.jsp" %>
<h3>Profile</h3>

<sec:authorize access="isAuthenticated()">
    <p>Email: ${email}</p>
    <p>Name: ${name}</p>
    <p>Surname: ${surname}</p>
    <p>Role: ${role}</p>
    <p><a href="/profile/edit">Edit profile</a> | <a href="/profile/change-password">Change password</a> </p>
</sec:authorize>

</body>
</html>
