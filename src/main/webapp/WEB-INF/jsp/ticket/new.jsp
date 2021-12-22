<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Ticket</title>
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>

<%@ include file="../templates/nav.jsp" %>

<%--@elvariable id="ticket" type="dev.joseluis.ticket.model.Ticket"--%>
<form:form method="post" action="/ticket/new" modelAttribute="ticket">
    <header>
        <h3>Create new ticket</h3>
    </header>
    <div class="form-group">
        <label for="service">Service</label>
        <form:select path="service" id="service" class="form-control">
            <option value="DNS">DNS</option>
            <option value="Email" selected>Email</option>
            <option value="Web">Web</option>
        </form:select>
    </div>
    <div class="form-group">
        <label for="subject">Subject</label>
        <form:input path="subject" id="subject" placeholder="Short description of ticket" class="form-control" />
    </div>
    <div class="form-group">
        <label for="description">Description</label>
        <form:textarea path="description" id="description" placeholder="Describe the problem in great detail!" class="form-control" maxlength="3000" rows="10"></form:textarea>
    </div>
    <button type="submit">Submit ticket</button>
</form:form>

</body>
</html>
