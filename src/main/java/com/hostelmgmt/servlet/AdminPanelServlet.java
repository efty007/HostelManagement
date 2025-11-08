package com.hostelmgmt.servlet;

import com.hostelmgmt.dao.ComplaintDao;
import com.hostelmgmt.dao.PaymentDao;
import com.hostelmgmt.dao.RoomDao;
import com.hostelmgmt.dao.StudentDao;
import com.hostelmgmt.model.Complaint;
import com.hostelmgmt.model.Payment;
import com.hostelmgmt.model.Room;
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

@WebServlet(name = "AdminPanelServlet", urlPatterns = "/admin/panel")
public class AdminPanelServlet extends HttpServlet {
    private final StudentDao studentDao = new StudentDao();
    private final RoomDao roomDao = new RoomDao();
    private final PaymentDao paymentDao = new PaymentDao();
    private final ComplaintDao complaintDao = new ComplaintDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        try {
            List<Student> students = studentDao.findAll();
            List<Room> rooms = roomDao.findAll();
            List<Complaint> complaints = complaintDao.findAll();
            req.setAttribute("students", students);
            req.setAttribute("rooms", rooms);
            req.setAttribute("complaints", complaints);
            req.getRequestDispatcher("/WEB-INF/views/adminDashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
