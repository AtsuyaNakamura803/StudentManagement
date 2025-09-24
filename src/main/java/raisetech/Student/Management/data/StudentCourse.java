package raisetech.Student.Management.data;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

/**
 * 学生のコース情報を表すドメイン
 */
public class StudentCourse {

    private int id;

    private int studentId;

    @NotBlank(message = "コース名は必須です")
    private String courseName;

    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private boolean deleted;

    // getter/setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public LocalDateTime getCourseStartAt() { return courseStartAt; }
    public void setCourseStartAt(LocalDateTime courseStartAt) { this.courseStartAt = courseStartAt; }
    public LocalDateTime getCourseEndAt() { return courseEndAt; }
    public void setCourseEndAt(LocalDateTime courseEndAt) { this.courseEndAt = courseEndAt; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}