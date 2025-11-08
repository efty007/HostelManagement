<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hostelmgmt.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container wide">
<div class="topbar"><a href="<%=request.getContextPath()%>/logout" class="btn secondary small">Logout</a></div>
<%
    Student student = (Student) session.getAttribute("student");
    if (student != null) {
%>
<h2>Welcome, <%= student.getName() %>!</h2>

<h3>Your Information</h3>
<table class="table">
    <tr><th>Email</th><td><%= student.getEmail() %></td></tr>
    <tr><th>Phone</th><td><%= student.getPhone() != null ? student.getPhone() : "-" %></td></tr>
    <tr><th>Blood Group</th><td><%= student.getBloodGroup() != null ? student.getBloodGroup() : "-" %></td></tr>
    <tr><th>Hometown</th><td><%= student.getHometown() != null ? student.getHometown() : "-" %></td></tr>
    <tr><th>Room</th><td><%= student.getRoomId() != null ? "Room #" + student.getRoomId() : "Not Assigned" %></td></tr>
</table>

<h3>Your Payments</h3>
<% } %>
<table class="table">
    <tr><th>Amount</th><th>Status</th><th>Date</th><th>Paid At</th></tr>
    <%
        java.util.List<com.hostelmgmt.model.Payment> payments = (java.util.List<com.hostelmgmt.model.Payment>) request.getAttribute("payments");
        if (payments != null) {
            for (com.hostelmgmt.model.Payment p : payments) {
    %>
    <tr>
        <td>৳ <%= String.format("%.2f", p.getAmount()) %></td>
        <td><%= p.getStatus() %></td>
        <td><%= p.getCreatedAt() != null ? p.getCreatedAt().toString().replace("T", " ") : "-" %></td>
        <td><%= p.getPaidAt() != null ? p.getPaidAt().toString().replace("T", " ") : "-" %></td>
    </tr>
    <%      }
        }
    %>
</table>

<div class="btn-group">
    <a href="<%=request.getContextPath()%>/student/submit-complaint" class="btn">Submit Complaint</a>
</div>
</div>
</body>
</html>
