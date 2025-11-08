package com.hostelmgmt.model;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private int studentId;
    private double amount;
    private String status; // PENDING, PAID, OVERDUE
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;

    public Payment() {}

    public Payment(int id, int studentId, double amount, String status, LocalDateTime paidAt, LocalDateTime createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
