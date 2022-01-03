<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<sec:authorize access="hasAnyRole('ROOT','ADMIN')">
    <table>
        <tr>
            <th>Email</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Role</th>
            <th>Active</th>
        </tr>
        <c:forEach items="${userList}" var="user">
            <tr>
                <th>${user.email}</th>
                <th>${user.name}</th>
                <th>${user.surname}</th>
                <th>${user.role}</th>
                <th>${user.active}</th>
            </tr>
        </c:forEach>
    </table>
</sec:authorize>

<sec:authorize access="hasRole('ANALYST')">
    <table>
        <tr>
            <th>Service</th>
            <th>Active</th>
        </tr>
        <c:forEach items="${serviceList}" var="service">
            <tr>
                <th>${service.name}</th>
                <th>${service.active}</th>
            </tr>
        </c:forEach>
    </table>
</sec:authorize>
</body>
</html>
