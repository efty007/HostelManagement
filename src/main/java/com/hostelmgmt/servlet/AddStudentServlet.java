package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.StudentDao;
import com.hostelmgmt.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/add-student")
public class AddStudentServlet extends HttpServlet {
    private StudentDao studentDao = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/addStudent.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String bloodGroup = req.getParameter("bloodGroup");
        String hometown = req.getParameter("hometown");

        try {
            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);
            student.setPhone(phone);
            student.setBloodGroup(bloodGroup);
            student.setHometown(hometown);

            int id = studentDao.create(student);
            if (id > 0) {
                req.setAttribute("message", "Student added successfully! ID: " + id);
            } else {
                req.setAttribute("error", "Failed to add student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/views/addStudent.jsp").forward(req, resp);
    }
}
