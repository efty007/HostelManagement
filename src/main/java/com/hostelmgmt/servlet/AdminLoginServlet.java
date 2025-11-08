package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.AdminDao;
import com.hostelmgmt.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminLoginServlet", urlPatterns = "/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private final AdminDao adminDao = new AdminDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/adminLogin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            Admin admin = adminDao.authenticate(username, password);
            if (admin != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("admin", admin);
                resp.sendRedirect(req.getContextPath() + "/admin/panel");
            } else {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/WEB-INF/views/adminLogin.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
