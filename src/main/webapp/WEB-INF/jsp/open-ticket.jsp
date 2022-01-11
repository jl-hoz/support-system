<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>New service</title>
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>

<%@ include file="templates/nav.jsp" %>


<%
    if(request.getParameter("error") != null){
        out.println("<div class=\"alert alert-error\">Something happened, please try again!</div>");
    }else if(request.getParameter("success") != null){
        out.println("<div class=\"alert alert-success\">Ticket successfully created</div>");
    }
%>

<sec:authorize access="hasRole('CUSTOMER')">
    <%--@elvariable id="ticket" type="dev.joseluis.ticket.model.Ticket"--%>
    <form:form method="post" modelAttribute="ticket">
        <header>
            <h3>Open ticket</h3>
        </header>
        <div class="form-group">
            <label for="service">Service</label>
            <form:select path="service" id="service" class="form-control">
                <c:forEach items="${serviceList}" var="service">
                    <form:option value="${service.id}">${service.name}</form:option>
                </c:forEach>
            </form:select>
        </div>
        <div class="form-group">
            <label for="subject">Subject</label>
            <form:input path="subject" placeholder="Short description of problem" id="subject" type="text" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <form:textarea path="description" id="description" placeholder="Describe the problem in great detail!" class="form-control" maxlength="1000" rows="10"/>
        </div>
        <button type="submit">Submit</button>
    </form:form>
</sec:authorize>


</body>
</html>
