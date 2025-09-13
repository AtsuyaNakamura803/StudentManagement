package raisetech.Student.Management.data;

import java.time.LocalDate;

public class StudentsCourses {

    private Long id;            // 修正: long → Long
    private Long studentId;     // 修正: long → Long
    private String courseName;
    private LocalDate courseStartAt;
    private LocalDate courseEndAt;

    /**
     * 入力値を検証します。
     * 必須項目が空の場合は IllegalArgumentException を投げます。
     */
    public void validate() {
        if (courseName == null || courseName.isBlank())
            throw new IllegalArgumentException("コース名は必須です");
        if (courseStartAt == null)
            throw new IllegalArgumentException("開始日は必須です");
        if (courseEndAt == null)
            throw new IllegalArgumentException("終了日は必須です");
    }

    // Getter / Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public LocalDate getCourseStartAt() { return courseStartAt; }
    public void setCourseStartAt(LocalDate courseStartAt) { this.courseStartAt = courseStartAt; }

    public LocalDate getCourseEndAt() { return courseEndAt; }
    public void setCourseEndAt(LocalDate courseEndAt) { this.courseEndAt = courseEndAt; }

    @Override
    public String toString() {
        return "StudentsCourses{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseName='" + courseName + '\'' +
                ", courseStartAt=" + courseStartAt +
                ", courseEndAt=" + courseEndAt +
                '}';
    }
}