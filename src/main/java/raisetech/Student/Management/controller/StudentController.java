package raisetech.Student.Management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 学生に関する REST API を提供するコントローラーです。
 */
@Validated
@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * すべての学生を取得するAPI
     *
     * @return 学生詳細リスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        logger.info("Fetching all students");
        return studentService.getAllStudents();
    }

    /**
     * 指定したIDの学生を取得するAPI
     *
     * @param id 学生ID（正の数）
     * @return 学生詳細
     */
    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable @Positive Long id) {
        logger.info("Fetching student by id={}", id);
        return studentService.searchStudentById(id);
    }

    /**
     * 学生を新規登録するAPI
     *
     * @param studentDetail 登録対象の学生情報
     * @return Location ヘッダーに新規リソースのURIを含めたレスポンス
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        studentService.registerStudent(studentDetail);
        Long createdId = studentDetail.getStudent().getId();

        if (createdId == null || createdId <= 0) {
            logger.error("Failed to retrieve created student id after registration");
            throw new IllegalStateException("登録後に受講生IDが取得できませんでした");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/student/{id}")
                .buildAndExpand(createdId)
                .toUri();

        logger.info("Student registered with id={}, Location={}", createdId, location);
        return ResponseEntity.created(location).build();
    }

    /**
     * 学生情報を更新するAPI
     *
     * @param studentDetail 更新対象の学生情報
     * @return 更新結果（204 No Content）
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody StudentDetail studentDetail) {
        studentService.updateStudent(studentDetail);
        logger.info("Student updated successfully: id={}", studentDetail.getStudent().getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * 学生を論理削除するAPI
     * <p>
     * /deleteStudent/{id} というURLで呼び出せるように変更。
     * students と students_courses の is_deleted を true に更新する。
     * </p>
     *
     * @param id 学生ID
     * @return 削除件数と削除対象のIDを含むレスポンス
     */
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id) {
        logger.info("Deleting student logically: id={}", id);
        Map<String, Object> result = studentService.deleteStudent(id);
        return ResponseEntity.ok(result);
    }
}