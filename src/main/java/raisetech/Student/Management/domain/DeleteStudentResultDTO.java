package raisetech.Student.Management.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生削除結果 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStudentResultDTO {

    /** 削除対象の学生ID */
    private Long id;

    /** 削除成功フラグ */
    private Boolean deleted;
}