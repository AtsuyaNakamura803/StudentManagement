package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 学生詳細情報を表すドメイン
 */
public class StudentDetail {

    private Student student;
    private List<StudentCourse> courses;

    /** 学生情報を取得 */
    public Student getStudent() {
        return student;
    }

    /** 学生情報を設定 */
    public void setStudent(Student student) {
        this.student = student;
    }

    /** コース情報リストを取得 */
    public List<StudentCourse> getCourses() {
        return courses;
    }

    /** コース情報リストを設定 */
    public void setCourses(List<StudentCourse> courses) {
        this.courses = courses;
    }
}