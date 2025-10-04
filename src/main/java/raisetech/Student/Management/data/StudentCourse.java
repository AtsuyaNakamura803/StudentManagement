package raisetech.Student.Management.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 学生のコース情報
 */
public class StudentCourse {

    /** コースID（DB自動生成） */
    private Long id;

    /** 学生ID */
    @NotNull(message = "studentId must not be null")
    private Long studentId;

    /** コース名 */
    @NotBlank(message = "courseName must not be blank")
    private String courseName;

    /** コース開始日 */
    private LocalDate courseStartAt;

    /** コース終了日 */
    private LocalDate courseEndAt;

    /** 論理削除フラグ */
    private Boolean deleted = false;

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

    public LocalDate getCourseStartAt() {
        return courseStartAt;
    }

    public void setCourseStartAt(LocalDate courseStartAt) {
        this.courseStartAt = courseStartAt;
    }

    public LocalDate getCourseEndAt() {
        return courseEndAt;
    }

    public void setCourseEndAt(LocalDate courseEndAt) {
        this.courseEndAt = courseEndAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}