<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ticket</title>
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>

<%@ include file="templates/nav.jsp" %>
<sec:authorize access="!isAuthenticated()">
    <p>You are not logged in!</p>
</sec:authorize>
</body>
</html>
