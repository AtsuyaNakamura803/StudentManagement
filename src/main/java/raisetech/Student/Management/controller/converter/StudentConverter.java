package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Student および StudentCourse の情報を StudentDetail に変換するユーティリティクラス。
 */
public class StudentConverter {

    /**
     * 単一の Student およびそのコース情報リストを StudentDetail に変換する。
     *
     * @param student 学生情報
     * @param courses 該当学生のコース情報リスト
     * @return StudentDetail
     */
    public static StudentDetail convertToStudentDetail(Student student, List<StudentCourse> courses) {
        StudentDetail detail = new StudentDetail();
        detail.setId(student.getId());
        detail.setName(student.getName());
        detail.setEmail(student.getEmail());
        detail.setAge(student.getAge());
        detail.setGender(student.getGender());
        detail.setIsDeleted(student.getIsDeleted() != null ? student.getIsDeleted() : false);
        detail.setCourses(courses);
        return detail;
    }

    /**
     * 学生リストと全コースリストを StudentDetail のリストに変換する。
     * 各学生に対応するコースを割り当てる。
     *
     * @param students 学生リスト
     * @param courses コースリスト（全学生分）
     * @return StudentDetail のリスト
     */
    public static List<StudentDetail> convertToStudentDetails(List<Student> students, List<StudentCourse> courses) {
        Map<Long, List<StudentCourse>> courseMap = courses.stream()
                .collect(Collectors.groupingBy(StudentCourse::getStudentId));

        return students.stream()
                .map(student -> {
                    List<StudentCourse> studentCourses = courseMap.getOrDefault(student.getId(), List.of());
                    return convertToStudentDetail(student, studentCourses);
                })
                .collect(Collectors.toList());
    }
}