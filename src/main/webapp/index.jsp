<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hostel Management - Home</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css" />
</head>
<body class="bg">
<div class="container">
    <h1>Hostel Management</h1>
    <p>Welcome to the Hostel Management System.</p>
    <div class="btn-group">
        <a class="btn" href="<%=request.getContextPath()%>/admin/login">Admin Login</a>
        <a class="btn secondary" href="<%=request.getContextPath()%>/student/login">Student Login</a>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/js/app.js"></script>
</body>
</html>
