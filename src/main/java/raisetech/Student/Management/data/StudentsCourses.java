package raisetech.Student.Management.data;

import java.time.LocalDate;

public class StudentsCourses {

    private long id;
    private long studentId;
    private String courseName;
    private LocalDate courseStartAt;
    private LocalDate courseEndAt;

    // Getter / Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }

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