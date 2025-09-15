package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 学生の詳細情報を保持するドメインクラスです。
 * Student と StudentCourse をまとめて扱います。
 */
public class StudentDetail {

    private Student student;
    private List<StudentCourse> courses = new ArrayList<>();

    public StudentDetail() {}

    public StudentDetail(Student student, List<StudentCourse> courses) {
        this.student = student;
        if (courses != null) {
            this.courses = courses;
        }
    }

    public Student getStudent() { return student; }

    public void setStudent(Student student) { this.student = student; }

    public List<StudentCourse> getCourses() { return courses; }

    public void setCourses(List<StudentCourse> courses) {
        if (courses != null) this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentDetail)) return false;
        StudentDetail that = (StudentDetail) o;
        return Objects.equals(student, that.student) &&
                Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() { return Objects.hash(student, courses); }

    @Override
    public String toString() {
        return "StudentDetail{" +
                "student=" + student +
                ", courses=" + courses +
                '}';
    }

    /**
     * バリデーションを行います。
     * @throws IllegalArgumentException student が null の場合
     */
    public void validate() {
        if (student == null) {
            throw new IllegalArgumentException("Student information must not be null");
        }
    }
}