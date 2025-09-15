package raisetech.Student.Management.data;

import java.util.Objects;

/**
 * 学生とコースの関連情報を保持するクラスです。
 */
public class StudentCourse {

    private Long id;
    private Long studentId;
    private String courseName;
    private Boolean isDeleted; // 論理削除フラグ

    public StudentCourse() {}

    public StudentCourse(Long id, Long studentId, String courseName, Boolean isDeleted) {
        this.id = id;
        this.studentId = studentId;
        this.courseName = courseName;
        this.isDeleted = isDeleted;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }

    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getCourseName() { return courseName; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Boolean getIsDeleted() { return isDeleted; }

    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourse)) return false;
        StudentCourse that = (StudentCourse) o;
        return Objects.equals(id, that.id)
                && Objects.equals(studentId, that.studentId)
                && Objects.equals(courseName, that.courseName)
                && Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() { return Objects.hash(id, studentId, courseName, isDeleted); }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseName='" + courseName + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}