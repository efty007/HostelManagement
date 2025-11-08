package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.RoomDao;
import com.hostelmgmt.dao.StudentDao;
import com.hostelmgmt.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RoomAssignmentServlet", urlPatterns = "/admin/assign-room")
public class RoomAssignmentServlet extends HttpServlet {
    private final StudentDao studentDao = new StudentDao();
    private final RoomDao roomDao = new RoomDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        try {
            req.setAttribute("students", studentDao.findAll());
            req.setAttribute("rooms", roomDao.findAll());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/assignRoom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        String studentEmail = req.getParameter("studentEmail");
        String roomNumber = req.getParameter("roomNumber");
        try {
            // Find student by email
            Student student = studentDao.findByEmail(studentEmail);
            if (student == null) {
                req.setAttribute("error", "Student not found");
                req.setAttribute("students", studentDao.findAll());
                req.setAttribute("rooms", roomDao.findAll());
                req.getRequestDispatcher("/WEB-INF/views/assignRoom.jsp").forward(req, resp);
                return;
            }

            // Find room by room number
            com.hostelmgmt.model.Room room = roomDao.findByRoomNumber(roomNumber);
            if (room == null) {
                req.setAttribute("error", "Room not found");
                req.setAttribute("students", studentDao.findAll());
                req.setAttribute("rooms", roomDao.findAll());
                req.getRequestDispatcher("/WEB-INF/views/assignRoom.jsp").forward(req, resp);
                return;
            }

            // Check if room is full
            if (room.getOccupied() >= room.getCapacity()) {
                req.setAttribute("error", "Room " + roomNumber + " is already full!");
                req.setAttribute("students", studentDao.findAll());
                req.setAttribute("rooms", roomDao.findAll());
                req.getRequestDispatcher("/WEB-INF/views/assignRoom.jsp").forward(req, resp);
                return;
            }

            Integer oldRoomId = student.getRoomId();
            student.setRoomId(room.getId());
            boolean ok = studentDao.update(student);
            if (ok) {
                roomDao.assignStudentToRoom(room.getId());
                if (oldRoomId != null) roomDao.removeStudentFromRoom(oldRoomId);
                req.setAttribute("message", "Room assigned successfully to " + student.getName());
            } else {
                req.setAttribute("error", "Failed to assign room");
            }
            req.setAttribute("students", studentDao.findAll());
            req.setAttribute("rooms", roomDao.findAll());
            req.getRequestDispatcher("/WEB-INF/views/assignRoom.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
