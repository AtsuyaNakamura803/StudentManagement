package raisetech.Student.Management.controller.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.Student.Management.domain.ErrorResponse;
import raisetech.Student.Management.service.StudentNotFoundException;

/**
 * グローバル例外ハンドラー
 *
 * <p>
 * Controller で発生した例外をキャッチして、
 * 統一された JSON 形式のレスポンスを返却する。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 学生が存在しない場合の例外ハンドリング
     *
     * @param ex StudentNotFoundException
     * @return ErrorResponse を返す
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * データベースの一意制約違反などの例外ハンドリング
     *
     * @param ex DataIntegrityViolationException
     * @return ErrorResponse を返す
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "データベース制約違反: " + (ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), message);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * その他の全ての例外ハンドリング
     *
     * @param ex Exception
     * @return ErrorResponse を返す
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}