package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.ArrayList;
import java.util.List;

public class StudentDetail {

    private Student student;
    private List<StudentsCourses> studentsCourses;

    public StudentDetail() {
        this.student = new Student();
        this.studentsCourses = new ArrayList<>();
    }

    public StudentDetail(Student student, List<StudentsCourses> studentsCourses) {
        this.student = student != null ? student : new Student();
        this.studentsCourses = studentsCourses != null ? studentsCourses : new ArrayList<>();
    }

    // Getter / Setter
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public List<StudentsCourses> getStudentsCourses() { return studentsCourses; }
    public void setStudentsCourses(List<StudentsCourses> studentsCourses) { this.studentsCourses = studentsCourses; }

    @Override
    public String toString() {
        return "StudentDetail{" +
                "student=" + student +
                ", studentsCourses=" + studentsCourses +
                '}';
    }
}