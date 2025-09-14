package raisetech.Student.Management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.net.URI;
import java.util.List;

/**
 * 学生に関する REST API を提供するコントローラーです。
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 受講生一覧検索
     *
     * @return 全受講生の詳細リスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        logger.info("Fetching all students");
        return studentService.getAllStudents();
    }

    /**
     * 受講生検索
     *
     * @param id 受講生ID
     * @return 該当受講生の詳細
     */
    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable("id") Long id) {
        logger.info("Fetching student by id={}", id);
        return studentService.searchStudentById(id);
    }

    /**
     * 受講生登録
     *
     * @param studentDetail 登録対象
     * @return Location ヘッダ付きレスポンス
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentDetail studentDetail) {
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
     * 受講生更新
     *
     * @param studentDetail 更新対象
     * @return 更新完了レスポンス
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody StudentDetail studentDetail) {
        studentService.updateStudent(studentDetail);
        logger.info("Student updated successfully: id={}", studentDetail.getStudent().getId());
        return ResponseEntity.noContent().build();
    }
}