package raisetech.Student.Management.data;

import java.util.Objects;

/**
 * 学生とコースの関連を表すクラスです。
 */
public class StudentsCourses {

    private Long id;
    private Long studentId;
    private String courseName;

    public StudentsCourses() {}

    public StudentsCourses(Long id, Long studentId, String courseName) {
        this.id = id;
        this.studentId = studentId;
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentsCourses)) return false;
        StudentsCourses that = (StudentsCourses) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseName, that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, courseName);
    }

    @Override
    public String toString() {
        return "StudentsCourses{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}