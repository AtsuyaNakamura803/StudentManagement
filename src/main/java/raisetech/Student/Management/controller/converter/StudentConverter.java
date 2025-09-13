package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentConverter {

    /**
     * 受講生とコース情報を結合して StudentDetail を生成
     */
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