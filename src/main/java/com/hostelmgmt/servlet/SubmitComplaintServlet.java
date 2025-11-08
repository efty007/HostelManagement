package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.ComplaintDao;
import com.hostelmgmt.model.Complaint;
import com.hostelmgmt.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student/submit-complaint")
public class SubmitComplaintServlet extends HttpServlet {
    private ComplaintDao complaintDao = new ComplaintDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/student/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/submitComplaint.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Student student = session != null ? (Student) session.getAttribute("student") : null;
        if (student == null) {
            resp.sendRedirect(req.getContextPath() + "/student/login");
            return;
        }

        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        try {
            Complaint complaint = new Complaint();
            complaint.setStudentId(student.getId());
            complaint.setSubject(subject);
            complaint.setMessage(message);
            complaint.setStatus("PENDING");

            int id = complaintDao.create(complaint);
            if (id > 0) {
                req.setAttribute("message", "Your complaint has been submitted successfully!");
            } else {
                req.setAttribute("error", "Failed to submit complaint.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/views/submitComplaint.jsp").forward(req, resp);
    }
}
