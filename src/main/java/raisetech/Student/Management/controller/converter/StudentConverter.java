package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Student と StudentCourse を結合して StudentDetail を生成するユーティリティクラスです。
 */
public class StudentConverter {

    /**
     * 学生とコースを結合して StudentDetail リストに変換します。
     *
     * @param students 学生リスト
     * @param courses コースリスト
     * @return StudentDetail リスト
     */
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