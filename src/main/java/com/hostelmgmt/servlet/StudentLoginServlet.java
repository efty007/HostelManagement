package com.hostelmgmt.servlet;

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

@WebServlet(name = "StudentLoginServlet", urlPatterns = "/student/login")
public class StudentLoginServlet extends HttpServlet {
    private final StudentDao studentDao = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/studentLogin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            Student student = studentDao.authenticate(email, password);
            if (student != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("student", student);
                resp.sendRedirect(req.getContextPath() + "/student/panel");
            } else {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/WEB-INF/views/studentLogin.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
