package raisetech.Student.Management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.domain.DeleteStudentResultDTO;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

/**
 * 学生情報 REST Controller
 * <p>
 * 学生の登録・取得・更新・削除を REST API で提供します。
 */
@RestController
@RequestMapping("/student")
@Validated // PathVariable バリデーションに必須
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 学生登録
     *
     * @param studentDetail 登録情報
     * @return 登録後の StudentDetail
     */
    @PostMapping("/register")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail detail = studentService.registerStudent(studentDetail);
        return ResponseEntity.ok(detail);
    }

    /**
     * 学生取得
     *
     * @param id 学生ID
     * @return StudentDetail
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDetail> getStudent(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    /**
     * 全学生取得
     *
     * @return StudentDetail のリスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * 学生更新
     *
     * @param id            学生ID
     * @param studentDetail 更新内容
     * @return 更新後 StudentDetail
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDetail> updateStudent(@PathVariable @Positive Long id,
                                                       @RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail updated = studentService.updateStudent(id, studentDetail);
        return ResponseEntity.ok(updated);
    }

    /**
     * 学生削除（論理削除）
     *
     * @param id 学生ID
     * @return 削除結果 DTO
     */
    @DeleteMapping("/{id}")
    public DeleteStudentResultDTO deleteStudent(@PathVariable @Positive Long id) {
        return studentService.deleteStudent(id);
    }
}