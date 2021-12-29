<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create new user</title>
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>

<%@ include file="templates/nav.jsp" %>
<%
    if(request.getParameter("error") != null){
        out.println("<div class=\"alert alert-error\">Error deactivating user</div>");
    }else if(request.getParameter("success") != null){
        out.println("<div class=\"alert alert-success\">User successfully deactivated</div>");
    }
%>

<sec:authorize access="hasAnyRole('ROOT', 'ADMIN')">
<%--@elvariable id="user" type="dev.joseluis.ticket.model.User"--%>
    <form:form method="post" modelAttribute="user">
        <header>
            <h3>Deactivate user</h3>
        </header>
        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="email" id="email" class="form-control"/>
        </div>
        <p>To reactivate user, enter again email of user.</p>
        <button type="submit">Submit</button>
    </form:form>
</sec:authorize>

</body>
</html>
