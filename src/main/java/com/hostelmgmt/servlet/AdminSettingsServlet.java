package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.AdminDao;
import com.hostelmgmt.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/settings")
public class AdminSettingsServlet extends HttpServlet {
    private AdminDao adminDao = new AdminDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/adminSettings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Admin admin = session != null ? (Admin) session.getAttribute("admin") : null;
        if (admin == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        String username = req.getParameter("username");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        try {
            // Verify current password
            if (!admin.getPassword().equals(currentPassword)) {
                req.setAttribute("error", "Current password is incorrect!");
                req.getRequestDispatcher("/WEB-INF/views/adminSettings.jsp").forward(req, resp);
                return;
            }

            // Validate new password if provided
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    req.setAttribute("error", "New passwords do not match!");
                    req.getRequestDispatcher("/WEB-INF/views/adminSettings.jsp").forward(req, resp);
                    return;
                }
                admin.setPassword(newPassword);
            }

            admin.setUsername(username);
            
            boolean updated = adminDao.update(admin);
            if (updated) {
                session.setAttribute("admin", admin); // Update session
                req.setAttribute("message", "Settings updated successfully!");
            } else {
                req.setAttribute("error", "Failed to update settings.");
            }
            req.getRequestDispatcher("/WEB-INF/views/adminSettings.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
