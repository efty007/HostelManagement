package com.hostelmgmt.dao;

import com.hostelmgmt.model.Admin;
import com.hostelmgmt.util.DBConnection;

import java.sql.*;

public class AdminDao {
    public Admin authenticate(String username, String password) throws SQLException {
        String sql = "SELECT id, username, password FROM admins WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Admin a = new Admin();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setPassword(rs.getString("password"));
                    return a;
                }
            }
        }
        return null;
    }

    public int create(Admin admin) throws SQLException {
        String sql = "INSERT INTO admins(username, password) VALUES(?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
            return 0;
        }
    }

    public Admin findById(int id) throws SQLException {
        String sql = "SELECT id, username, password FROM admins WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null;
    }

    public boolean update(Admin admin) throws SQLException {
        String sql = "UPDATE admins SET username=?, password=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.setInt(3, admin.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM admins WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
