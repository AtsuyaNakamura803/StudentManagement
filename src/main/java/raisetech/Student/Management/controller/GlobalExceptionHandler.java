package raisetech.Student.Management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Validation failed", ex);
        return Map.of("message", "入力値が不正です");
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody Map<String, String> handleIllegalState(IllegalStateException ex) {
        logger.error("System error", ex);
        return Map.of("message", "サーバー内部でエラーが発生しました");
    }
}