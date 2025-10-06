package raisetech.Student.Management.domain;

/**
 * 学生削除結果 DTO
 */
public class DeleteStudentResultDTO {

    private Long id;
    private boolean deleted;

    public DeleteStudentResultDTO(Long id, boolean deleted) {
        this.id = id;
        this.deleted = deleted;
    }

    public Long getId() { return id; }
    public boolean isDeleted() { return deleted; }
}