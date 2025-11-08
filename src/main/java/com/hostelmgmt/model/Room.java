package com.hostelmgmt.model;

public class Room {
    private int id;
    private String roomNumber;
    private int capacity;
    private int occupied;
    private String status; // AVAILABLE, FULL, MAINTENANCE

    public Room() {}

    public Room(int id, String roomNumber, int capacity, int occupied, String status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.occupied = occupied;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getOccupied() { return occupied; }
    public void setOccupied(int occupied) { this.occupied = occupied; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
