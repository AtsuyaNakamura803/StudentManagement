package raisetech.Student.Management.data;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 学生データを表すクラス（DB 保存用）。
 */
@Data
public class Student {

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

    /** 削除フラグ */
    private Boolean isDeleted = false;
}