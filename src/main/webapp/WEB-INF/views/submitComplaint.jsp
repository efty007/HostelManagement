<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Complaint</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container">
<h2>Submit a Complaint</h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:#059669; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:#dc2626; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="<%=request.getContextPath()%>/student/submit-complaint" class="form">
    <label>Subject: <input type="text" name="subject" required maxlength="200"></label>
    <label>Message: 
        <textarea name="message" required rows="6" style="width: 100%; padding: 8px; border: 2px solid #93c5fd; border-radius: 8px; font-family: Arial, sans-serif;"></textarea>
    </label>
    <button type="submit" class="btn">Submit Complaint</button>
</form>
<p style="margin-top: 20px;"><a href="<%=request.getContextPath()%>/student/panel" class="link">← Back to Dashboard</a></p>
</div>
</body>
</html>
