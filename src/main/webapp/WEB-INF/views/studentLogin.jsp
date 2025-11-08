<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Login</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container">
<h2>Student Login</h2>
<% String error = (String) request.getAttribute("error"); if (error != null) { %>
<p style="color:#dc2626; font-weight: 600; margin: 10px 0;"><%= error %></p>
<% } %>
<form method="post" action="<%=request.getContextPath()%>/student/login" class="form">
    <label>Email: <input type="email" name="email" required></label>
    <label>Password: <input type="password" name="password" required></label>
    <button type="submit" class="btn">Login</button>
</form>
<p style="margin-top: 20px;"><a href="<%=request.getContextPath()%>/" class="link">← Back to Home</a></p>
</div>
</body>
</html>
