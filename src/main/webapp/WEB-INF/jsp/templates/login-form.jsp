<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%--@elvariable id="user" type="dev.joseluis.ticket.model.User"--%>
<form:form method="post" action="/user/login" modelAttribute="user">
    <header>
        <h3>Login</h3>
    </header>
    <div class="form-group">
        <label for="email">Email</label>
        <form:input path="email" id="email" class="form-control" />
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <form:input path="password" id="password" type="password" class="form-control" />
    </div>
    <button type="submit">Login</button>
</form:form>
