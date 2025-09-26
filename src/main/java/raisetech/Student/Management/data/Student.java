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

    @Email(message = "正しいメールアドレス形式で入力してください")
    private String email;

    @Min(value = 0, message = "年齢は0以上を入力してください")
    private Integer age;

    @NotBlank(message = "性別は必須です")
    private String sex; // DBカラムと整合

    /** 論理削除フラグ */
    private Boolean deleted = false;
}