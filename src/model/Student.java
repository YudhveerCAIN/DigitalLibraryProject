package model;

public class Student {
    private int studentId;           // Student_ID
    private int userId;              // User_ID
    private String degreeProgram;    // Degree_Program
    private int enrollmentYear;      // Enrollment_Year

    // Constructors
    public Student() {}

    public Student(int studentId, int userId, String degreeProgram, int enrollmentYear) {
        this.studentId = studentId;
        this.userId = userId;
        this.degreeProgram = degreeProgram;
        this.enrollmentYear = enrollmentYear;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", userId=" + userId +
                ", degreeProgram='" + degreeProgram + '\'' +
                ", enrollmentYear=" + enrollmentYear +
                '}';
    }
}
