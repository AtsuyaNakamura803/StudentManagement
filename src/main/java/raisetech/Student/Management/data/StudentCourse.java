package raisetech.Student.Management.data;

import lombok.Data;
import java.time.LocalDate;

/**
 * 学生のコース受講情報を表すデータクラス
 */
@Data
public class StudentCourse {

    private Long id;
    private Long studentId;
    private String courseName;
    private LocalDate courseStartAt;
    private LocalDate courseEndAt;
    private Boolean deleted = false;
}