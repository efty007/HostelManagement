package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.StudentDao;
import com.hostelmgmt.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/edit-student")
public class EditStudentServlet extends HttpServlet {
    private StudentDao studentDao = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        
        String studentIdStr = req.getParameter("id");
        try {
            int studentId = Integer.parseInt(studentIdStr);
            Student student = studentDao.findById(studentId);
            req.setAttribute("student", student);
            req.getRequestDispatcher("/WEB-INF/views/editStudent.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        String studentIdStr = req.getParameter("studentId");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String bloodGroup = req.getParameter("bloodGroup");
        String hometown = req.getParameter("hometown");

        try {
            int studentId = Integer.parseInt(studentIdStr);
            Student student = studentDao.findById(studentId);
            if (student != null) {
                student.setName(name);
                student.setEmail(email);
                student.setPhone(phone);
                student.setBloodGroup(bloodGroup);
                student.setHometown(hometown);
                
                boolean updated = studentDao.update(student);
                if (updated) {
                    req.setAttribute("message", "Student updated successfully!");
                } else {
                    req.setAttribute("error", "Failed to update student.");
                }
                req.setAttribute("student", student);
            } else {
                req.setAttribute("error", "Student not found.");
            }
            req.getRequestDispatcher("/WEB-INF/views/editStudent.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }
}
