package raisetech.Student.Management.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生詳細情報（DTO）
 */
@Data
public class StudentDetail {

    /** 学生ID */
    private Long id;

    /** 名前 */
    @NotBlank(message = "名前は必須です")
    private String name;

    /** メールアドレス */
    @Email(message = "メールアドレスの形式が不正です")
    private String email;

    /** 年齢 */
    @Min(value = 0, message = "年齢は0以上である必要があります")
    private Integer age;

    /** 性別 */
    @NotBlank(message = "性別は必須です")
    private String gender;

    /** 論理削除フラグ */
    private Boolean deleted = false;

    /** 学生が受講するコース情報 */
    @Valid
    private List<StudentCourse> courses = new ArrayList<>();

    /**
     * StudentDetail → Student 変換
     * @return Student エンティティ
     */
    public Student toStudent() {
        Student student = new Student();
        student.setId(this.id);
        student.setName(this.name);
        student.setEmail(this.email);
        student.setAge(this.age);
        student.setSex(this.gender);
        student.setDeleted(this.deleted);
        return student;
    }
}