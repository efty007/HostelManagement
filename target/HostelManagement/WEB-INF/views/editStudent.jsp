<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hostelmgmt.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Student</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container">
<%
    Student student = (Student) request.getAttribute("student");
    if (student != null) {
%>
<h2>Edit Student: <%= student.getName() %></h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:#059669; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:#dc2626; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="<%=request.getContextPath()%>/admin/edit-student" class="form">
    <input type="hidden" name="studentId" value="<%= student.getId() %>">
    <label>Name: <input type="text" name="name" value="<%= student.getName() %>" required></label>
    <label>Email: <input type="email" name="email" value="<%= student.getEmail() %>" required></label>
    <label>Phone: <input type="tel" name="phone" value="<%= student.getPhone() != null ? student.getPhone() : "" %>"></label>
    <label>Blood Group: <input type="text" name="bloodGroup" value="<%= student.getBloodGroup() != null ? student.getBloodGroup() : "" %>"></label>
    <label>Hometown: <input type="text" name="hometown" value="<%= student.getHometown() != null ? student.getHometown() : "" %>"></label>
    <button type="submit" class="btn">Update Student</button>
</form>
<% } else { %>
<p style="color:#dc2626;">Student not found.</p>
<% } %>
<p style="margin-top: 20px;"><a href="<%=request.getContextPath()%>/admin/panel" class="link">← Back to Dashboard</a></p>
</div>
</body>
</html>
