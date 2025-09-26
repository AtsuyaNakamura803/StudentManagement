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

    public static StudentDetail convertToStudentDetail(Student student, List<StudentCourse> courses) {
        StudentDetail detail = new StudentDetail();
        detail.setId(student.getId());
        detail.setName(student.getName());
        detail.setEmail(student.getEmail());
        detail.setAge(student.getAge());
        detail.setGender(student.getGender());
        detail.setDeleted(student.getDeleted() != null ? student.getDeleted() : false);

        // 学生IDに紐づくコースだけ抽出
        if (courses != null) {
            List<StudentCourse> studentCourses = courses.stream()
                    .filter(c -> c.getStudentId().equals(student.getId()))
                    .collect(Collectors.toList());
            detail.setCourses(studentCourses);
        }
        return detail;
    }

    public static List<StudentDetail> convertToStudentDetails(List<Student> students, List<StudentCourse> courses) {
        List<StudentDetail> details = new ArrayList<>();
        for (Student student : students) {
            details.add(convertToStudentDetail(student, courses));
        }
        return details;
    }
}