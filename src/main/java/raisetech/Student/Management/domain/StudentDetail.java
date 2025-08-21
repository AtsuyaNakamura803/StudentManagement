package raisetech.Student.Management.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;

@Getter
@Setter
public class StudentDetail {

    // 学生情報
    private Student student;

    // DB保存用の受講コースリスト
    private List<StudentsCourses> studentsCourses;

    // フォーム入力用：カンマ区切りのコース名
    private String courseNames;

    // Thymeleaf用：フォームで表示・編集可能なコースリスト
    private List<StudentsCourses> courses;

}