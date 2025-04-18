package service;

import dao.StudentDAO;
import model.Student;

import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public boolean createStudent(Student student) {
        return studentDAO.createStudent(student);
    }

    public Student getStudentById(int studentId) {
        return studentDAO.getStudentById(studentId);
    }

    public boolean updateStudent(Student student) {
        return studentDAO.updateStudent(student);
    }

    public boolean deleteStudent(int studentId) {
        return studentDAO.deleteStudent(studentId);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}
