<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.hostelmgmt.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Assign Room</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container">
<h2>Assign Room</h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:#059669; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:#dc2626; font-weight: 600; margin: 10px 0;"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="<%=request.getContextPath()%>/admin/assign-room" class="form">
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
    <label>Room:
        <select name="roomNumber" required>
            <option value="">-- Select Room --</option>
            <%
                List<Room> rooms = (List<Room>) request.getAttribute("rooms");
                if (rooms != null) {
                    for (Room r : rooms) {
            %>
            <option value="<%= r.getRoomNumber() %>">Room <%= r.getRoomNumber() %> (<%= r.getOccupied() %>/<%= r.getCapacity() %> - <%= r.getStatus() %>)</option>
            <%      }
                }
            %>
        </select>
    </label>
    <button type="submit" class="btn">Assign Room</button>
</form>
<p style="margin-top: 20px;"><a href="<%=request.getContextPath()%>/admin/panel" class="link">← Back to Dashboard</a></p>
</div>
</body>
</html>
