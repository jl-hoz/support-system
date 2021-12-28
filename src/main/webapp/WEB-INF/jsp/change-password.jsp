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

<sec:authorize access="hasAnyRole('ROOT', 'ADMIN', 'SUPPORT', 'ANALYST', 'CUSTOMER')">
    <%--@elvariable id="user" type="dev.joseluis.ticket.model.User"--%>
    <form:form method="post" modelAttribute="user">
        <header>
            <h3>Change password</h3>
        </header>
        <div class="form-group">
            <label for="password">Password</label>
            <form:input path="password" id="surname" type="password" class="form-control"/>
        </div>
        <button type="submit">Submit</button>
    </form:form>
</sec:authorize>


</body>
</html>
