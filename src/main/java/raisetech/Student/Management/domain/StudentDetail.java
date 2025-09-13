package raisetech.Student.Management.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

    // 学生情報（null回避で初期化）
    private Student student = new Student();

    // 学生に紐づくコースリスト（null回避で初期化）
    private List<StudentsCourses> studentsCourses = new ArrayList<>();
}