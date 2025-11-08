package com.hostelmgmt.dao;

import com.hostelmgmt.model.Room;
import com.hostelmgmt.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    public int create(Room r) throws SQLException {
        String sql = "INSERT INTO rooms(room_number, capacity, occupied, status) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getRoomNumber());
            ps.setInt(2, r.getCapacity());
            ps.setInt(3, r.getOccupied());
            ps.setString(4, r.getStatus());
            int affected = ps.executeUpdate();
            if (affected > 0) { try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) return k.getInt(1); } }
            return 0;
        }
    }

    public Room findById(int id) throws SQLException {
        String sql = "SELECT id, room_number, capacity, occupied, status FROM rooms WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public Room findByRoomNumber(String roomNumber) throws SQLException {
        String sql = "SELECT id, room_number, capacity, occupied, status FROM rooms WHERE room_number=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, roomNumber);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public List<Room> findAll() throws SQLException {
        String sql = "SELECT id, room_number, capacity, occupied, status FROM rooms ORDER BY room_number";
        List<Room> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public boolean update(Room r) throws SQLException {
        String sql = "UPDATE rooms SET room_number=?, capacity=?, occupied=?, status=? WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getRoomNumber());
            ps.setInt(2, r.getCapacity());
            ps.setInt(3, r.getOccupied());
            ps.setString(4, r.getStatus());
            ps.setInt(5, r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean assignStudentToRoom(int roomId) throws SQLException {
        String sql = "UPDATE rooms SET occupied = occupied + 1, status = CASE WHEN occupied + 1 >= capacity THEN 'FULL' ELSE 'AVAILABLE' END WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeStudentFromRoom(int roomId) throws SQLException {
        String sql = "UPDATE rooms SET occupied = GREATEST(occupied - 1, 0), status = CASE WHEN GREATEST(occupied - 1, 0) >= capacity THEN 'FULL' ELSE 'AVAILABLE' END WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;
        }
    }

    private Room map(ResultSet rs) throws SQLException {
        Room r = new Room();
        r.setId(rs.getInt("id"));
        r.setRoomNumber(rs.getString("room_number"));
        r.setCapacity(rs.getInt("capacity"));
        r.setOccupied(rs.getInt("occupied"));
        r.setStatus(rs.getString("status"));
        return r;
    }
}
