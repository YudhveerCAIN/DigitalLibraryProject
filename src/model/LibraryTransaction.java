package model;

import java.util.Date;

public class LibraryTransaction {
    private int transactionId;         // Transaction_ID
    private int userId;                // User_ID
    private int publicationId;         // Publication_ID
    private String transactionType;    // Transaction_Type (Borrow, Return, Reservation)
    private Date transactionDate;      // Transaction_Date
    private Date dueDate;              // Due_Date
    private double lateFee;            // Late_Fee

    // Constructors
    public LibraryTransaction() {}

    public LibraryTransaction(int transactionId, int userId, int publicationId,
                              String transactionType, Date transactionDate,
                              Date dueDate, double lateFee) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.publicationId = publicationId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.lateFee = lateFee;
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getLateFee() {
        return lateFee;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    // toString
    @Override
    public String toString() {
        return "LibraryTransaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", publicationId=" + publicationId +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", dueDate=" + dueDate +
                ", lateFee=" + lateFee +
                '}';
    }
}
