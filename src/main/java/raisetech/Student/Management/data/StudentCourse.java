package raisetech.Student.Management.data;

import lombok.Data;
import java.util.Date;

/**
 * 学生コース情報エンティティ
 */
@Data
public class StudentCourse {

    /** コースID */
    private Long id;

    /** 学生ID */
    private Long studentId;

    /** コース名 */
    private String courseName;

    /** 開始日 */
    private Date courseStartAt;

    /** 終了日 */
    private Date courseEndAt;

    /** 論理削除フラグ */
    private Boolean deleted = false;
}