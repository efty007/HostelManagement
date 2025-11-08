package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.PaymentDao;
import com.hostelmgmt.dao.StudentDao;
import com.hostelmgmt.model.Payment;
import com.hostelmgmt.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StudentPanelServlet", urlPatterns = "/student/panel")
public class StudentPanelServlet extends HttpServlet {
    private final StudentDao studentDao = new StudentDao();
    private final PaymentDao paymentDao = new PaymentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Student logged = session != null ? (Student) session.getAttribute("student") : null;
        if (logged == null) {
            resp.sendRedirect(req.getContextPath() + "/student/login");
            return;
        }
        try {
            List<Payment> payments = paymentDao.findByStudent(logged.getId());
            req.setAttribute("payments", payments);
            req.getRequestDispatcher("/WEB-INF/views/studentDashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
