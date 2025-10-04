package raisetech.Student.Management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * グローバル例外ハンドラ
 * <p>
 * REST API で発生する例外を統一的にハンドリングし、
 * 適切な HTTP ステータスコードとメッセージを返却します。
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * バリデーション例外をハンドリングします。
     * PathVariable や RequestParam のバリデーション失敗時に 400 を返します。
     *
     * @param ex ConstraintViolationException
     * @return HTTP 400 + エラー情報
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation failed");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 指定した要素が存在しない場合にハンドリングします。
     * 404 Not Found を返却します。
     *
     * @param ex NoSuchElementException
     * @return HTTP 404 + エラー情報
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * その他の例外をハンドリングします。
     * 500 Internal Server Error を返却します。
     *
     * @param ex Exception
     * @return HTTP 500 + エラー情報
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal server error");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}