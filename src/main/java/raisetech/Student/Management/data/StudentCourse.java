package raisetech.Student.Management.data;

import java.time.LocalDateTime;

/**
 * 学生のコース情報を表すドメイン
 */
public class StudentCourse {

    private int id;
    private int studentId;
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private boolean deleted;

    /** ID を取得 */
    public int getId() {
        return id;
    }

    /** ID を設定 */
    public void setId(int id) {
        this.id = id;
    }

    /** 学生IDを取得 */
    public int getStudentId() {
        return studentId;
    }

    /** 学生IDを設定 */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /** コース名を取得 */
    public String getCourseName() {
        return courseName;
    }

    /** コース名を設定 */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /** コース開始日時を取得 */
    public LocalDateTime getCourseStartAt() {
        return courseStartAt;
    }

    /** コース開始日時を設定 */
    public void setCourseStartAt(LocalDateTime courseStartAt) {
        this.courseStartAt = courseStartAt;
    }

    /** コース終了日時を取得 */
    public LocalDateTime getCourseEndAt() {
        return courseEndAt;
    }

    /** コース終了日時を設定 */
    public void setCourseEndAt(LocalDateTime courseEndAt) {
        this.courseEndAt = courseEndAt;
    }

    /** 削除フラグを取得 */
    public boolean isDeleted() {
        return deleted;
    }

    /** 削除フラグを設定 */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}