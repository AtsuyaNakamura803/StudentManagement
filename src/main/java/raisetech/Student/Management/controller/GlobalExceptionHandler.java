package raisetech.Student.Management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * グローバル例外ハンドラ
 * <p>
 * Controller で発生した例外を統一的に処理し、クライアントには安全なメッセージを返却。
 * 内部ログには詳細なスタックトレースを出力。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * バリデーションエラー処理
     * @param ex MethodArgumentNotValidException
     * @return フィールドごとのエラーメッセージマップ + 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        logger.warn("Validation failed: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * データ未検出時の例外処理
     * @param ex NoSuchElementException
     * @return 404 Not Found
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException ex) {
        logger.warn("Resource not found: {}", ex.getMessage());
        Map<String, String> response = Map.of("error", "Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 全ての未処理例外をキャッチ
     * @param ex Exception
     * @return 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        logger.error("Internal server error", ex);
        Map<String, String> response = Map.of("error", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}