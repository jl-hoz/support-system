<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


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
        out.println("<div class=\"alert alert-error\">Probably that service name already exists</div>");
    }else if(request.getParameter("success") != null){
        out.println("<div class=\"alert alert-success\">Service name successfully created</div>");
    }
%>

<sec:authorize access="hasRole('ANALYST')">
    <%--@elvariable id="service" type="dev.joseluis.ticket.model.Service"--%>
    <form:form method="post" modelAttribute="service">
        <header>
            <h3>New service</h3>
        </header>
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" placeholder="Set service name (has to be unique)" id="name" type="text" class="form-control"/>
        </div>
        <button type="submit">Submit</button>
    </form:form>
</sec:authorize>


</body>
</html>
