package raisetech.Student.Management.domain;

import lombok.Data;

/**
 * 学生削除操作の結果を返す DTO。
 * <p>
 * 削除成功時に学生ID、削除フラグ、メッセージを返却します。
 * API の削除エンドポイント (/student/{id}) で利用されます。
 * </p>
 */
@Data
public class DeleteStudentResultDTO {

    /** 削除対象の学生ID */
    private Long studentId;

    /** 論理削除フラグ */
    private Boolean deleted;

    /** 削除結果メッセージ */
    private String message;
}