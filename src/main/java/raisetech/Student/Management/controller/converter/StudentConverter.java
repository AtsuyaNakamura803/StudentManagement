package raisetech.Student.Management.controller.converter;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Student と StudentCourse を StudentDetail に変換するユーティリティクラス
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
        Map<Integer, List<StudentCourse>> coursesMap = courses.stream()
                .filter(c -> !c.isDeleted())
                .collect(Collectors.groupingBy(StudentCourse::getStudentId));

        List<StudentDetail> details = new ArrayList<>();
        for (Student student : students) {
            if (!student.isDeleted()) {
                StudentDetail detail = new StudentDetail();
                detail.setStudent(student);
                detail.setCourses(coursesMap.getOrDefault(student.getId(), new ArrayList<>()));
                details.add(detail);
            }
        }
        return details;
    }
}