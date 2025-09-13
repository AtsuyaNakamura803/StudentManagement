package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentConverter {

    /**
     * 受講生とコース情報を結合して StudentDetail を生成します。
     *
     * @param students 学生一覧
     * @param courses  全コース一覧
     * @return 学生詳細一覧
     */
    public static List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentsCourses> courses) {
        return students.stream()
                .map(student -> {
                    List<StudentsCourses> studentCourses = courses.stream()
                            .filter(sc -> Objects.equals(student.getId(), sc.getStudentId())) // null 安全
                            .collect(Collectors.toList());
                    return new StudentDetail(student, studentCourses);
                })
                .collect(Collectors.toList());
    }
}