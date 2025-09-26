package raisetech.Student.Management.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生詳細情報（学生 + コース複数）を表すドメインクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String gender; // Student.sex をここにマッピング
    private Boolean deleted;
    private List<StudentCourse> courses;

    /**
     * StudentDetail から DB 保存用の Student に変換
     */
    public Student toStudent() {
        Student student = new Student();
        student.setId(this.id);
        student.setName(this.name);
        student.setEmail(this.email);
        student.setAge(this.age);
        student.setSex(this.gender); // gender → Student.sex にマッピング
        student.setDeleted(this.deleted != null ? this.deleted : false);
        return student;
    }
}