package raisetech.Student.Management.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("IllegalArgumentException: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("error", "不正なリクエストです");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        logger.error("IllegalStateException: {}", ex.getMessage(), ex);
        Map<String, String> body = new HashMap<>();
        body.put("error", "サーバー内部でエラーが発生しました");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        logger.warn("ResponseStatusException: {}", ex.getReason(), ex);
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getReason() != null ? ex.getReason() : "リクエスト処理中にエラーが発生しました");
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("Unhandled exception: {}", ex.getMessage(), ex);
        Map<String, String> body = new HashMap<>();
        body.put("error", "サーバー内部でエラーが発生しました");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}