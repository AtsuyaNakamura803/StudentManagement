package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentDetail {

    private Student student;
    private List<StudentsCourses> studentsCourses;

    public StudentDetail(Student student, List<StudentsCourses> studentsCourses) {
        this.student = student;
        this.studentsCourses = studentsCourses;
    }

    public Student getStudent() { return student; }
    public List<StudentsCourses> getStudentsCourses() { return studentsCourses; }

    public void validate() {
        Objects.requireNonNull(student, "Student は必須です");
        student.validate();
        if (studentsCourses != null) studentsCourses.forEach(StudentsCourses::validate);
    }

    public String getCourseNamesAsString() {
        if (studentsCourses == null || studentsCourses.isEmpty()) return "";
        return studentsCourses.stream()
                .map(StudentsCourses::getCourseName)
                .collect(Collectors.joining(", "));
    }
}