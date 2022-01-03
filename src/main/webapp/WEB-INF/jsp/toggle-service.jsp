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

<%
    if(request.getParameter("error") != null){
        out.println("<div class=\"alert alert-error\">Error changing service status</div>");
    }else if(request.getParameter("success") != null){
        out.println("<div class=\"alert alert-success\">Service status successfully changed</div>");
    }
%>

<sec:authorize access="hasAnyRole('ANALYST')">
    <%--@elvariable id="service" type="dev.joseluis.ticket.model.Service"--%>
    <form:form method="post" modelAttribute="service">
        <header>
            <h3>Toggle service status</h3>
        </header>
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" id="name" type="text" class="form-control"/>
        </div>
        <button type="submit">Submit</button>
    </form:form>
</sec:authorize>


</body>
</html>
