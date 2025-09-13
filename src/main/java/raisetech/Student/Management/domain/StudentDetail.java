package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;

public class StudentDetail {

    private Student student;
    private List<StudentsCourses> studentsCourses;

    public StudentDetail(Student student, List<StudentsCourses> studentsCourses) {
        this.student = student;
        this.studentsCourses = studentsCourses;
    }

    public void validate() {
        if (student != null) student.validate();
        if (studentsCourses != null) {
            for (StudentsCourses sc : studentsCourses) sc.validate();
        }
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public List<StudentsCourses> getStudentsCourses() { return studentsCourses; }
    public void setStudentsCourses(List<StudentsCourses> studentsCourses) { this.studentsCourses = studentsCourses; }
}