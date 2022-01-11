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
<h3>Ticket</h3>

<sec:authorize access="hasAnyRole('SUPPORT','CUSTOMER')">
    <p>ID: ${ticket.id}</p>
    <p>Service: ${ticket.service.name}</p>
    <p>Subject: ${ticket.subject}</p>
    <p>Creation: ${ticket.created}</p>
    <p>Status: ${ticket.status}</p>
    <p>Description: ${ticket.description}</p>
    <p><a href="/ticket/close/${ticket.id}">Close ticket</a></p>
</sec:authorize>

</body>
</html>
