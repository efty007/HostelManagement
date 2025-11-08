<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.hostelmgmt.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Record/Update Payment</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container">
<h2>Record Payment</h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:#059669; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:#dc2626; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="<%=request.getContextPath()%>/admin/update-payment" class="form">
    <label>Student:
        <select name="studentEmail" required>
            <option value="">-- Select Student --</option>
            <%
                List<Student> students = (List<Student>) request.getAttribute("students");
                if (students != null) {
                    for (Student s : students) {
            %>
            <option value="<%= s.getEmail() %>"><%= s.getName() %> (<%= s.getEmail() %>)</option>
            <%      }
                }
            %>
        </select>
    </label>
    <label>Amount: <input type="number" step="0.01" name="amount" required></label>
    <label>Status:
        <select name="status" required>
            <option value="PENDING">PENDING</option>
            <option value="PAID">PAID</option>
            <option value="OVERDUE">OVERDUE</option>
        </select>
    </label>
    <button type="submit" class="btn">Save</button>
</form>
<p style="margin-top: 20px;"><a href="<%=request.getContextPath()%>/admin/panel" class="link">← Back to Dashboard</a></p>
</div>
</body>
</html>
