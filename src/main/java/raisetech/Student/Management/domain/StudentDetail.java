package raisetech.Student.Management.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 学生詳細情報（学生 + 複数コース）を表すドメインクラス。
 * API レスポンスで返すためのオブジェクト。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String gender;
    private Boolean isDeleted;
    private List<StudentCourse> courses;

    /**
     * StudentDetail から DB 保存用の Student エンティティに変換する。
     *
     * @return Student エンティティ
     */
    public Student toStudent() {
        Student student = new Student();
        student.setId(this.id);
        student.setName(this.name);
        student.setEmail(this.email);
        student.setAge(this.age);
        student.setGender(this.gender);
        student.setIsDeleted(this.isDeleted != null ? this.isDeleted : false);
        return student;
    }
}