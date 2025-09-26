package raisetech.Student.Management.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生詳細情報（学生 + コース複数）を表すドメインクラス
 *
 * <p>
 * Bean Validation 注釈を付与し、Controller で @Valid を使用する場合に入力チェックが有効になるようにしています。
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

    /** 学生ID */
    private Long id;

    /** 学生名（必須） */
    @NotBlank(message = "名前は必須です")
    private String name;

    /** メールアドレス（必須・形式チェック） */
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "正しいメールアドレス形式で入力してください")
    private String email;

    /** 年齢（0以上） */
    @NotNull(message = "年齢は必須です")
    @Min(value = 0, message = "年齢は0以上を入力してください")
    private Integer age;

    /** 性別（必須） */
    @NotBlank(message = "性別は必須です")
    private String gender;

    /** 論理削除フラグ */
    private Boolean deleted = false;

    /** 学生に紐づくコース情報 */
    @Valid
    private List<StudentCourse> courses;

    /**
     * StudentDetail から DB 保存用の Student に変換
     *
     * @return Student エンティティ
     */
    public raisetech.Student.Management.data.Student toStudent() {
        raisetech.Student.Management.data.Student student = new raisetech.Student.Management.data.Student();
        student.setId(this.id);
        student.setName(this.name);
        student.setEmail(this.email);
        student.setAge(this.age);
        student.setSex(this.gender); // Student.sex にマッピング
        student.setDeleted(this.deleted != null ? this.deleted : false);
        return student;
    }
}