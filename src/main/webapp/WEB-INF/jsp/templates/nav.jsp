<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar">
    <span class="navbar-title">
        <h1>Ticket</h1>
    </span>
    <ul class="navbar-nav">
        <li><a href="/" class="nav-link">home</a></li>
        <sec:authorize access="!isAuthenticated()">
            <li><a href="/login" class="nav-link">login</a></li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ADMIN', 'ANALYST', 'SUPPORT', 'CUSTOMER')">
            <li><a href="/profile" class="nav-link">profile</a></li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROOT', 'ADMIN')">
            <li><a href="/activate" class="nav-link">create</a></li>
            <li><a href="/deactivate" class="nav-link">remove</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ANALYST')">
            <li>
                <a href="/service/new" class="nav-link">new</a> /
                <a href="/service/edit-status" class="nav-link">edit</a>
            </li>
        </sec:authorize>
        <sec:authorize access="hasRole('CUSTOMER')">
            <li>
                <a href="/ticket/open" class="nav-link">open ticket</a>
            </li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <li><a href="/logout" class="nav-link">logout</a></li>
        </sec:authorize>
    </ul>
</nav>