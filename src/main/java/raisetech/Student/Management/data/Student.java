package raisetech.Student.Management.data;

import lombok.Data;

/**
 * 学生エンティティ（DB保存用）
 */
@Data
public class Student {

    /** 学生ID（自動採番） */
    private Long id;

    /** 名前 */
    private String name;

    /** メールアドレス */
    private String email;

    /** 年齢 */
    private Integer age;

    /** 性別 */
    private String sex;

    /** 論理削除フラグ */
    private Boolean deleted = false;
}