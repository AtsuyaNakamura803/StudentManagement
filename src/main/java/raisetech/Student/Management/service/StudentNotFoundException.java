package raisetech.Student.Management.service;

/**
 * 存在しない学生IDを参照した場合の例外
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}