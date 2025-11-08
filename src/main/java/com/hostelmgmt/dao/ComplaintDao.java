package com.hostelmgmt.dao;

import com.hostelmgmt.model.Complaint;
import com.hostelmgmt.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDao {
    
    public int create(Complaint c) throws SQLException {
        String sql = "INSERT INTO complaints(student_id, subject, message, status) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getStudentId());
            ps.setString(2, c.getSubject());
            ps.setString(3, c.getMessage());
            ps.setString(4, c.getStatus());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
            return 0;
        }
    }

    public List<Complaint> findAll() throws SQLException {
        String sql = "SELECT c.id, c.student_id, s.name as student_name, c.subject, c.message, c.status, c.created_at " +
                     "FROM complaints c JOIN students s ON c.student_id = s.id ORDER BY c.created_at DESC";
        List<Complaint> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); 
             Statement st = con.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Complaint c = new Complaint();
                c.setId(rs.getInt("id"));
                c.setStudentId(rs.getInt("student_id"));
                c.setStudentName(rs.getString("student_name"));
                c.setSubject(rs.getString("subject"));
                c.setMessage(rs.getString("message"));
                c.setStatus(rs.getString("status"));
                Timestamp ts = rs.getTimestamp("created_at");
                c.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);
                list.add(c);
            }
        }
        return list;
    }

    public List<Complaint> findByStudent(int studentId) throws SQLException {
        String sql = "SELECT id, student_id, subject, message, status, created_at FROM complaints WHERE student_id=? ORDER BY created_at DESC";
        List<Complaint> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Complaint c = new Complaint();
                    c.setId(rs.getInt("id"));
                    c.setStudentId(rs.getInt("student_id"));
                    c.setSubject(rs.getString("subject"));
                    c.setMessage(rs.getString("message"));
                    c.setStatus(rs.getString("status"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    c.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);
                    list.add(c);
                }
            }
        }
        return list;
    }
}
