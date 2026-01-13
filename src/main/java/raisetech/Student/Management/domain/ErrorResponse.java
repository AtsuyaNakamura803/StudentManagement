package raisetech.Student.Management.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API エラー応答 DTO
 *
 * <p>
 * Controller で例外発生時に、HTTP ステータスと
 * エラーメッセージを一貫して返すためのクラスです。
 * JSON レスポンス例:
 * <pre>
 * {
 *   "status": 400,
 *   "message": "name: must not be blank"
 * }
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * HTTP ステータスコード（例: 400, 404, 500）
     */
    @JsonProperty("status")
    private int status;

    /**
     * エラー内容のメッセージ
     */
    @JsonProperty("message")
    private String message;
}