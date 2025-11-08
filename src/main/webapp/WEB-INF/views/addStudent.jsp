<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Student</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container">
<h2>Add New Student</h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:#059669; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:#dc2626; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="<%=request.getContextPath()%>/admin/add-student" class="form">
    <label>Name: <input type="text" name="name" required></label>
    <label>Email: <input type="email" name="email" required></label>
    <label>Password: <input type="password" name="password" required></label>
    <label>Phone: <input type="tel" name="phone" required></label>
    <label>Blood Group: <input type="text" name="bloodGroup" placeholder="e.g. O+"></label>
    <label>Hometown: <input type="text" name="hometown"></label>
    <button type="submit" class="btn">Add Student</button>
</form>
<p style="margin-top: 20px;"><a href="<%=request.getContextPath()%>/admin/panel" class="link">← Back to Dashboard</a></p>
</div>
</body>
</html>
