<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.hostelmgmt.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body class="bg">
<div class="container wide">
<div class="topbar">
    <a href="<%=request.getContextPath()%>/admin/settings" class="btn small" style="margin-right: 10px;">Settings</a>
    <a href="<%=request.getContextPath()%>/logout" class="btn secondary small">Logout</a>
</div>
<h2>Admin Dashboard</h2>

<h3>Students</h3>
<table class="table">
    <tr><th>Name</th><th>Email</th><th>Phone</th><th>Blood Group</th><th>Room</th><th>Actions</th></tr>
    <%
        List<Student> students = (List<Student>) request.getAttribute("students");
        if (students != null) {
            for (Student s : students) {
    %>
    <tr>
        <td><%= s.getName() %></td>
        <td><%= s.getEmail() %></td>
        <td><%= s.getPhone() != null ? s.getPhone() : "-" %></td>
        <td><%= s.getBloodGroup() != null ? s.getBloodGroup() : "-" %></td>
        <td>
            <%
                if (s.getRoomId() != null) {
                    List<Room> roomsList = (List<Room>) request.getAttribute("rooms");
                    String roomNum = "-";
                    if (roomsList != null) {
                        for (Room r : roomsList) {
                            if (r.getId() == s.getRoomId()) {
                                roomNum = r.getRoomNumber();
                                break;
                            }
                        }
                    }
            %>
            <%= roomNum %>
            <% } else { %>
            -
            <% } %>
        </td>
        <td><a href="<%=request.getContextPath()%>/admin/edit-student?id=<%= s.getId() %>" class="btn small">Edit</a></td>
    </tr>
    <%      }
        }
    %>
</table>

<h3>Rooms</h3>
<table class="table">
    <tr><th>Number</th><th>Capacity</th><th>Occupied</th><th>Status</th></tr>
    <%
        List<Room> rooms = (List<Room>) request.getAttribute("rooms");
        if (rooms != null) {
            for (Room r : rooms) {
    %>
    <tr>
        <td><%= r.getRoomNumber() %></td>
        <td><%= r.getCapacity() %></td>
        <td><%= r.getOccupied() %></td>
        <td><%= r.getStatus() %></td>
    </tr>
    <%      }
        }
    %>
</table>

<h3>Student Complaints</h3>
<table class="table">
    <tr><th>Student</th><th>Subject</th><th>Message</th><th>Status</th><th>Date</th></tr>
    <%
        List<com.hostelmgmt.model.Complaint> complaints = (List<com.hostelmgmt.model.Complaint>) request.getAttribute("complaints");
        if (complaints != null && !complaints.isEmpty()) {
            for (com.hostelmgmt.model.Complaint c : complaints) {
    %>
    <tr>
        <td><%= c.getStudentName() %></td>
        <td><%= c.getSubject() %></td>
        <td><%= c.getMessage().length() > 50 ? c.getMessage().substring(0, 50) + "..." : c.getMessage() %></td>
        <td><%= c.getStatus() %></td>
        <td><%= c.getCreatedAt() != null ? c.getCreatedAt().toString().replace("T", " ").substring(0, 16) : "-" %></td>
    </tr>
    <%      }
        } else {
    %>
    <tr><td colspan="5" style="text-align:center;">No complaints yet</td></tr>
    <%  } %>
</table>

<div class="btn-group">
    <a href="<%=request.getContextPath()%>/admin/add-student" class="btn">Add Student</a>
    <a href="<%=request.getContextPath()%>/admin/assign-room" class="btn">Assign Room</a>
    <a href="<%=request.getContextPath()%>/admin/update-payment" class="btn secondary">Record Payment</a>
</div>
</div>
</body>
</html>
