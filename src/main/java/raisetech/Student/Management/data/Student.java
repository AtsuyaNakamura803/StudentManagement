package raisetech.Student.Management.data;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 学生エンティティ（DB保存用）
 */
@Data
public class Student {

    private Long id;

    @NotBlank(message = "名前は必須です")
    private String name;

    @Email(message = "メールアドレスの形式が不正です")
    private String email;

    @Min(value = 0, message = "年齢は0以上である必要があります")
    private Integer age;

    @NotBlank(message = "性別は必須です")
    private String sex;

    /** 論理削除フラグ */
    private Boolean deleted = false;
}