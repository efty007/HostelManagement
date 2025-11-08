package com.hostelmgmt.dao;

import com.hostelmgmt.model.Payment;
import com.hostelmgmt.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    public int create(Payment p) throws SQLException {
        String sql = "INSERT INTO payments(student_id, amount, status, paid_at) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getStudentId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getStatus());
            if (p.getPaidAt() == null) ps.setNull(4, Types.TIMESTAMP); else ps.setTimestamp(4, Timestamp.valueOf(p.getPaidAt()));
            int affected = ps.executeUpdate();
            if (affected > 0) { try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) return k.getInt(1); } }
            return 0;
        }
    }

    public Payment findById(int id) throws SQLException {
        String sql = "SELECT id, student_id, amount, status, paid_at, created_at FROM payments WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public List<Payment> findByStudent(int studentId) throws SQLException {
        String sql = "SELECT id, student_id, amount, status, paid_at, created_at FROM payments WHERE student_id=? ORDER BY id DESC";
        List<Payment> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }

    public boolean update(Payment p) throws SQLException {
        String sql = "UPDATE payments SET student_id=?, amount=?, status=?, paid_at=? WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getStudentId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getStatus());
            if (p.getPaidAt() == null) ps.setNull(4, Types.TIMESTAMP); else ps.setTimestamp(4, Timestamp.valueOf(p.getPaidAt()));
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM payments WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Payment map(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setId(rs.getInt("id"));
        p.setStudentId(rs.getInt("student_id"));
        p.setAmount(rs.getDouble("amount"));
        p.setStatus(rs.getString("status"));
        Timestamp paid = rs.getTimestamp("paid_at");
        p.setPaidAt(paid != null ? paid.toLocalDateTime() : null);
        Timestamp created = rs.getTimestamp("created_at");
        p.setCreatedAt(created != null ? created.toLocalDateTime() : null);
        return p;
    }
}
