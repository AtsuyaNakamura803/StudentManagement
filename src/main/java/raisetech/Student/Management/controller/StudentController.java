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

@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 受講生一覧取得
     *
     * @return 全受講生のリスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * 受講生取得
     *
     * @param id 受講生ID
     * @return 該当受講生
     */
    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable("id") Long id) {
        return studentService.searchStudentById(id);
    }

    /**
     * 受講生登録
     *
     * @param studentDetail 登録対象
     * @return 作成完了レスポンス（Location ヘッダ付き）
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentDetail studentDetail) {
        studentService.registerStudent(studentDetail);

        Long createdId = studentDetail.getStudent().getId();
        if (createdId == null || createdId <= 0) {
            logger.error("Invalid created student ID: {}", createdId);
            throw new IllegalStateException("作成された受講生のIDが無効です");
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/student/{id}")
                .buildAndExpand(createdId)
                .toUri();

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
        return ResponseEntity.noContent().build();
    }
}