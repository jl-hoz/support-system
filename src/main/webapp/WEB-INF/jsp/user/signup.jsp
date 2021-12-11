<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sign up</title>
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>


<form:form method="post" action="/signup" modelAttribute="user">
    <header>
        <h3>Sign up</h3>
    </header>
    <div class="form-group">
        <label for="email">Email</label>
        <form:input path="email" id="email" class="form-control" />
    </div>
    <div class="form-group">
        <label for="name">Name</label>
        <form:input path="name" id="name" class="form-control" />
    </div>
    <div class="form-group">
        <label for="surname">Surname</label>
        <form:input path="surname" id="surname" class="form-control"/>
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <form:input path="password" id="password" type="password" class="form-control" />
    </div>
    <button type="submit">Sign up</button>
</form:form>


</body>
</html>
