<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hostelmgmt.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Settings</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container">
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin != null) {
%>
<h2>Admin Settings</h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:#059669; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:#dc2626; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="<%=request.getContextPath()%>/admin/settings" class="form">
    <label>Username: <input type="text" name="username" value="<%= admin.getUsername() %>" required></label>
    <label>Current Password: <input type="password" name="currentPassword" required></label>
    <label>New Password: <input type="password" name="newPassword" placeholder="Leave blank to keep current"></label>
    <label>Confirm New Password: <input type="password" name="confirmPassword" placeholder="Leave blank to keep current"></label>
    <button type="submit" class="btn">Update Settings</button>
</form>
<% } else { %>
<p style="color:#dc2626;">Session expired. Please login again.</p>
<% } %>
<p style="margin-top: 20px;"><a href="<%=request.getContextPath()%>/admin/panel" class="link">← Back to Dashboard</a></p>
</div>
</body>
</html>
