package raisetech.Student.Management.data;

import lombok.Data;
import java.time.LocalDate;

/**
 * 学生のコース受講情報を表すデータクラス。
 */
@Data
public class StudentCourse {

    /** コースID */
    private Long id;

    /** 学生ID（外部キー） */
    private Long studentId;

    /** コース名 */
    private String courseName;

    /** コース開始日 */
    private LocalDate courseStartAt;

    /** コース終了日 */
    private LocalDate courseEndAt;

    /** 削除フラグ */
    private Boolean isDeleted = false;
}