package raisetech.Student.Management.domain;

/**
 * 学生削除 API の結果 DTO
 */
public class DeleteStudentResultDTO {

    private int studentId;
    private String message;

    public DeleteStudentResultDTO() {}

    public DeleteStudentResultDTO(int studentId, String message) {
        this.studentId = studentId;
        this.message = message;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}