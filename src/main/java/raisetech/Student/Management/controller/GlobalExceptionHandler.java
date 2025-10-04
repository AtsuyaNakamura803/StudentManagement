package raisetech.Student.Management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import raisetech.Student.Management.domain.ErrorResponse;

import java.util.NoSuchElementException;
import jakarta.validation.ValidationException;

/**
 * グローバル例外ハンドラー
 *
 * <p>
 * Controller で発生した例外を統一的にキャッチし、
 * HTTP ステータス + エラーメッセージ形式で返す。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Bean Validation の @Valid 失敗時のハンドリング
     *
     * @param ex MethodArgumentNotValidException
     * @return HTTP 400 + エラーメッセージ
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .reduce((m1, m2) -> m1 + ", " + m2)
                .orElse("Validation failed");
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * ValidationException のハンドリング（手動スローも含む）
     *
     * @param ex ValidationException
     * @return HTTP 400 + エラーメッセージ
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 存在しないリソースアクセス時のハンドリング
     *
     * @param ex NoSuchElementException
     * @return HTTP 404 + エラーメッセージ
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * その他の例外をキャッチ
     *
     * @param ex Exception
     * @return HTTP 500 + エラーメッセージ
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        // 内部例外はログに残すべき（ここでは簡略化）
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}