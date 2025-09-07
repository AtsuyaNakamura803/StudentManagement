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

    // 学生に紐づくコースリスト
    private List<StudentsCourses> studentsCourses;

    // コンストラクタ（student が null にならないように初期化）
    public StudentDetail() {
        this.student = new Student();
    }
}