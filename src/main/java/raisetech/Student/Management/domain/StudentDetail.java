package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * 学生詳細情報を表すドメイン
 */
public class StudentDetail {

    @NotNull
    @Valid
    private Student student;

    @Valid
    private List<StudentCourse> courses;

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public List<StudentCourse> getCourses() { return courses; }
    public void setCourses(List<StudentCourse> courses) { this.courses = courses; }
}