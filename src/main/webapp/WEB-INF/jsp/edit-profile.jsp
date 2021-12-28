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
        out.println("<div class=\"alert alert-error\">Error updating user</div>");
    }else if(request.getParameter("success") != null){
        out.println("<div class=\"alert alert-success\">User successfully updated</div>");
    }
%>

<sec:authorize access="hasAnyRole('ROOT', 'ADMIN', 'SUPPORT', 'ANALYST', 'CUSTOMER')">
    <%--@elvariable id="user" type="dev.joseluis.ticket.model.User"--%>
    <form:form method="post" modelAttribute="user">
        <header>
            <h3>Update profile</h3>
        </header>
        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="email" id="email" class="form-control" value="${email}"/>
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" id="name" class="form-control" value="${name}"/>
        </div>
        <div class="form-group">
            <label for="surname">Surname</label>
            <form:input path="surname" id="surname" class="form-control" value="${surname}"/>
        </div>
        <button type="submit">Update</button>
    </form:form>
</sec:authorize>

</body>
</html>
