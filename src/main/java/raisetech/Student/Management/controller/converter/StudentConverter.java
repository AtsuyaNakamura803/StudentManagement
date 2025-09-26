package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Student / StudentDetail 変換ユーティリティ
 */
public class StudentConverter {

    /**
     * 単一 Student → StudentDetail に変換
     *
     * @param student Student エンティティ
     * @param courses コースリスト
     * @return StudentDetail
     */
    public static StudentDetail convertToStudentDetail(Student student, List<StudentCourse> courses) {
        StudentDetail detail = new StudentDetail();
        detail.setId(student.getId());
        detail.setName(student.getName());
        detail.setEmail(student.getEmail());
        detail.setAge(student.getAge());
        detail.setGender(student.getSex()); // Student.sex → StudentDetail.gender
        detail.setDeleted(student.getDeleted() != null ? student.getDeleted() : false);

        if (courses != null) {
            List<StudentCourse> studentCourses = courses.stream()
                    .filter(c -> c.getStudentId().equals(student.getId()))
                    .collect(Collectors.toList());
            detail.setCourses(studentCourses);
        }
        return detail;
    }

    /**
     * 複数 Student → StudentDetail リストに変換
     *
     * @param students Student リスト
     * @param courses  コースリスト
     * @return StudentDetail のリスト
     */
    public static List<StudentDetail> convertToStudentDetails(List<Student> students, List<StudentCourse> courses) {
        List<StudentDetail> details = new ArrayList<>();
        for (Student student : students) {
            details.add(convertToStudentDetail(student, courses));
        }
        return details;
    }
}