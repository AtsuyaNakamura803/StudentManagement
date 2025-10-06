package raisetech.Student.Management.data;

import java.time.LocalDateTime;

/**
 * 学生コース情報
 */
public class StudentCourse {

    private Long id;
    private Long studentId;
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private Boolean deleted;

    // --- Getter / Setter ---
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

    public LocalDateTime getCourseStartAt() {
        return courseStartAt;
    }

    public void setCourseStartAt(LocalDateTime courseStartAt) {
        this.courseStartAt = courseStartAt;
    }

    public LocalDateTime getCourseEndAt() {
        return courseEndAt;
    }

    public void setCourseEndAt(LocalDateTime courseEndAt) {
        this.courseEndAt = courseEndAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    // MyBatis / JSON 用に isDeleted() も追加
    public boolean isDeleted() {
        return deleted != null && deleted;
    }
}