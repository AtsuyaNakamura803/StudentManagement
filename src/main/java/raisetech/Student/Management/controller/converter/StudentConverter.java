package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Student ⇔ StudentDetail 変換ユーティリティ
 *
 * <p>
 * Student データと StudentCourse データを StudentDetail に変換するためのユーティリティクラスです。
 * </p>
 */
public class StudentConverter {

    /**
     * Student と StudentCourse のリストを StudentDetail に変換
     *
     * @param student Student データ
     * @param courses StudentCourse リスト
     * @return StudentDetail ドメインオブジェクト
     */
    public static StudentDetail convertToStudentDetail(Student student, List<StudentCourse> courses) {
        StudentDetail detail = new StudentDetail();
        detail.setId(student.getId());
        detail.setName(student.getName());
        detail.setEmail(student.getEmail());
        detail.setAge(student.getAge());
        detail.setGender(student.getSex()); // sex → gender

        List<StudentDetail.CourseDetail> courseDetails = new ArrayList<>();
        if (courses != null) {
            for (StudentCourse course : courses) {
                StudentDetail.CourseDetail c = new StudentDetail.CourseDetail(course);
                courseDetails.add(c);
            }
        }
        detail.setCourses(courseDetails);

        return detail;
    }

    /**
     * StudentDetail から Student に変換
     *
     * @param detail StudentDetail
     * @return Student
     */
    public static Student convertToStudent(StudentDetail detail) {
        return detail.toStudent();
    }

    /**
     * StudentDetail から StudentCourse のリストに変換
     *
     * @param detail StudentDetail
     * @param studentId Student ID
     * @return List<StudentCourse>
     */
    public static List<StudentCourse> convertToStudentCourses(StudentDetail detail, Long studentId) {
        return detail.toStudentCourses(studentId);
    }
}