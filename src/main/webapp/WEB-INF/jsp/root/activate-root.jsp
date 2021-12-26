<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create new user</title>
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>

<%@ include file="../templates/nav.jsp" %>
<%
    if(request.getParameter("error") != null){
        out.println("<div class=\"alert alert-error\">Error creating a user</div>");
    }else if(request.getParameter("success") != null){
        out.println("<div class=\"alert alert-success\">User successfully created</div>");
    }
%>

<sec:authorize access="hasAnyRole('ROOT', 'ADMIN')">
<%--@elvariable id="user" type="dev.joseluis.ticket.model.User"--%>
    <form:form method="post" action="/root/activate" modelAttribute="user">
    <header>
        <h3>Create new user</h3>
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
        <label for="role">Service</label>
        <form:select path="role" id="role" class="form-control">
            <option value="ROLE_CUSTOMER" selected>Customer</option>
            <option value="ROLE_SUPPORT">Support</option>
            <option value="ROLE_ANALYSIS">Analysis</option>
            <sec:authorize access="hasRole('ROOT')">
                <option value="ROLE_ADMIN">Administrator</option>
            </sec:authorize>
        </form:select>
    </div>
    <button type="submit">Create new user</button>
</form:form>
</sec:authorize>

</body>
</html>
