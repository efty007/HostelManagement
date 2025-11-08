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
import java.time.LocalDateTime;

@WebServlet(name = "PaymentUpdateServlet", urlPatterns = "/admin/update-payment")
public class PaymentUpdateServlet extends HttpServlet {
    private final PaymentDao paymentDao = new PaymentDao();
    private final StudentDao studentDao = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        try {
            req.setAttribute("students", studentDao.findAll());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/updatePayment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        String studentEmail = req.getParameter("studentEmail");
        String amountStr = req.getParameter("amount");
        String status = req.getParameter("status");
        try {
            Student student = studentDao.findByEmail(studentEmail);
            if (student == null) {
                req.setAttribute("error", "Student not found");
                req.setAttribute("students", studentDao.findAll());
                req.getRequestDispatcher("/WEB-INF/views/updatePayment.jsp").forward(req, resp);
                return;
            }
            
            double amount = Double.parseDouble(amountStr);
            Payment p = new Payment();
            p.setStudentId(student.getId());
            p.setAmount(amount);
            p.setStatus(status);
            if ("PAID".equalsIgnoreCase(status)) {
                p.setPaidAt(LocalDateTime.now());
            }
            paymentDao.create(p);
            req.setAttribute("message", "Payment recorded successfully for " + student.getName());
            req.setAttribute("students", studentDao.findAll());
            req.getRequestDispatcher("/WEB-INF/views/updatePayment.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }
}
