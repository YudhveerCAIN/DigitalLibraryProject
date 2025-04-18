package model;

public class Librarian {
    private int staffId;         // Staff_ID
    private int userId;          // User_ID
    private String workShift;    // Work_Shift

    // Constructors
    public Librarian() {}

    public Librarian(int staffId, int userId, String workShift) {
        this.staffId = staffId;
        this.userId = userId;
        this.workShift = workShift;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWorkShift() {
        return workShift;
    }

    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }

    // toString method
    @Override
    public String toString() {
        return "Librarian{" +
                "staffId=" + staffId +
                ", userId=" + userId +
                ", workShift='" + workShift + '\'' +
                '}';
    }
}
