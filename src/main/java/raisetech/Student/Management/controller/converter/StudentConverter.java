package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 受講生とコース情報を結合して {@link StudentDetail} を生成するユーティリティクラスです。
 */
public class StudentConverter {

    public static List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentCourse> courses) {
        return students.stream()
                .map(student -> {
                    List<StudentCourse> studentCourses = courses.stream()
                            .filter(sc -> Objects.equals(student.getId(), sc.getStudentId()))
                            .collect(Collectors.toList());
                    return new StudentDetail(student, studentCourses);
                })
                .collect(Collectors.toList());
    }
}