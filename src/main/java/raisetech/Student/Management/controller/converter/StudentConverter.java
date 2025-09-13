package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Student と StudentsCourses を結合して StudentDetail を生成するユーティリティ。
 */
public class StudentConverter {

    public static List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentsCourses> courses) {
        return students.stream()
                .map(student -> {
                    List<StudentsCourses> studentCourses = courses.stream()
                            .filter(sc -> Objects.equals(student.getId(), sc.getStudentId()))
                            .collect(Collectors.toList());
                    return new StudentDetail(student, studentCourses);
                })
                .collect(Collectors.toList());
    }
}