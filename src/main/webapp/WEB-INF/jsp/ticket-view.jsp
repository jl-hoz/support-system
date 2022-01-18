<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <div>
        <h4>Details</h4>
        <p>ID: ${ticket.id}</p>
        <p>Service: ${ticket.service.name}</p>
        <p>Subject: ${ticket.subject}</p>
        <p>Creation: ${ticket.created}</p>
        <p>Status: ${ticket.status}</p>
        <p>Description: ${ticket.description}</p>
        <p><a href="/ticket/close/${ticket.id}">Close ticket</a></p>
    </div>
    <div>
        <h4>Messages</h4>
        <c:forEach items="${messageList}" var="msg">
            <div class="message">
                <p>${msg.user.email}</p>
                <p>${msg.created}</p>
                <p>${msg.content}</p>
            </div>
        </c:forEach>
    </div>
    <%--@elvariable id="message" type="dev.joseluis.ticket.model.Message"--%>
    <form:form method="post" action="/ticket/${ticket.id}/send-message/" modelAttribute="message">
        <header>
            <h4>Send message</h4>
        </header>
        <div class="form-group">
            <form:textarea path="content" class="form-control" maxlength="3000" rows="10"/>
        </div>
        <button type="submit">Submit</button>
    </form:form>

</sec:authorize>

</body>
</html>
