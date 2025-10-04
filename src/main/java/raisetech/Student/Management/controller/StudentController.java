package raisetech.Student.Management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.domain.DeleteStudentResultDTO;
import raisetech.Student.Management.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 学生情報 REST Controller
 *
 * <p>
 * 学生情報の登録・取得・更新・削除のエンドポイントを提供する。
 */
@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 学生登録
     *
     * @param studentDetail 登録情報
     * @return 登録後の学生情報
     */
    @PostMapping("/register")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail detail = studentService.registerStudent(studentDetail);
        return ResponseEntity.ok(detail);
    }

    /**
     * IDで学生取得
     *
     * @param id 学生ID
     * @return StudentDetail
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDetail> getStudent(@PathVariable @Positive Long id) {
        try {
            StudentDetail detail = studentService.getStudent(id);
            return ResponseEntity.ok(detail);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * 全学生取得
     *
     * @return 学生リスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * 学生更新
     *
     * @param id 更新対象学生ID
     * @param studentDetail 更新情報
     * @return 更新後 StudentDetail
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDetail> updateStudent(@PathVariable @Positive Long id,
                                                       @RequestBody @Valid StudentDetail studentDetail) {
        try {
            StudentDetail updated = studentService.updateStudent(id, studentDetail);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * 学生削除（論理削除）
     *
     * @param id 学生ID
     * @return 削除結果 DTO
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteStudentResultDTO> deleteStudent(@PathVariable @Positive Long id) {
        try {
            DeleteStudentResultDTO result = studentService.deleteStudent(id);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}