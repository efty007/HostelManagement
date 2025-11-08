package com.hostelmgmt.dao;

import com.hostelmgmt.model.Student;
import com.hostelmgmt.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    public Student authenticate(String email, String password) throws SQLException {
        String sql = "SELECT id, name, email, password, phone, blood_group, hometown, room_id, created_at FROM students WHERE email=? AND password=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public int create(Student s) throws SQLException {
        String sql = "INSERT INTO students(name, email, password, phone, blood_group, hometown, room_id) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPassword());
            ps.setString(4, s.getPhone());
            ps.setString(5, s.getBloodGroup());
            ps.setString(6, s.getHometown());
            if (s.getRoomId() == null) ps.setNull(7, Types.INTEGER); else ps.setInt(7, s.getRoomId());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
            return 0;
        }
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT id, name, email, password, phone, blood_group, hometown, room_id, created_at FROM students WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public Student findByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, email, password, phone, blood_group, hometown, room_id, created_at FROM students WHERE email=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Student> findAll() throws SQLException {
        String sql = "SELECT id, name, email, password, phone, blood_group, hometown, room_id, created_at FROM students ORDER BY id DESC";
        List<Student> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public boolean update(Student s) throws SQLException {
        String sql = "UPDATE students SET name=?, email=?, password=?, phone=?, blood_group=?, hometown=?, room_id=? WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPassword());
            ps.setString(4, s.getPhone());
            ps.setString(5, s.getBloodGroup());
            ps.setString(6, s.getHometown());
            if (s.getRoomId() == null) ps.setNull(7, Types.INTEGER); else ps.setInt(7, s.getRoomId());
            ps.setInt(8, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Student map(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("password"));
        s.setPhone(rs.getString("phone"));
        s.setBloodGroup(rs.getString("blood_group"));
        s.setHometown(rs.getString("hometown"));
        int roomId = rs.getInt("room_id");
        s.setRoomId(rs.wasNull() ? null : roomId);
        Timestamp created = rs.getTimestamp("created_at");
        s.setCreatedAt(created != null ? created.toLocalDateTime() : null);
        return s;
    }
}
