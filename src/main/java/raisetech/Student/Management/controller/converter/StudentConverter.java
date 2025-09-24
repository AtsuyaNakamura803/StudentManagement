package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Student と StudentCourse を StudentDetail に変換するユーティリティクラス。
 */
public class StudentConverter {

    /**
     * 学生リストとコースリストをまとめて StudentDetail リストに変換する
     *
     * @param students 学生リスト
     * @param courses  コースリスト
     * @return StudentDetail のリスト
     */
    public static List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentCourse> courses) {
        // 学生IDをキーにしてコースをグループ化
        Map<Integer, List<StudentCourse>> coursesMap = courses.stream()
                .collect(Collectors.groupingBy(StudentCourse::getStudentId));

        List<StudentDetail> details = new ArrayList<>();
        for (Student student : students) {
            StudentDetail detail = new StudentDetail();
            detail.setStudent(student);
            // 該当学生のコースリストをセット（なければ空リスト）
            detail.setCourses(coursesMap.getOrDefault(student.getId(), new ArrayList<>()));
            details.add(detail);
        }
        return details;
    }
}