package raisetech.Student.Management.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.Student.Management.domain.ErrorResponse;
import raisetech.Student.Management.service.StudentNotFoundException;

import jakarta.validation.ConstraintViolationException;

/**
 * アプリ全体の例外を一括ハンドリングするクラス
 *
 * <p>500 系は内部メッセージを返さず、ログに詳細を残す設計。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** StudentNotFoundException を 404 として返す */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
        // ログには詳細を出力
        logger.warn("Student not found", ex);
        // API には安全なメッセージだけ返す
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "指定された学生は存在しません。");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /** バリデーションエラー (ConstraintViolation) を 400 として返す */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        logger.warn("Validation failed", ex);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "入力値に誤りがあります。");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /** それ以外の例外を 500 として返す */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        // スタックトレースなど詳細はログに出力
        logger.error("Internal server error", ex);
        // API には安全な汎用メッセージだけ返す
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "サーバ内部でエラーが発生しました。");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}