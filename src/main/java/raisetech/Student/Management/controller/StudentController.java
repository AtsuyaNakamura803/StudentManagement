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
     * 学生を登録するAPI
     *
     * @param studentDetail 登録対象の学生詳細
     * @return 登録成功のURI
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
        studentService.registerStudent(studentDetail);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studentDetail.getStudent().getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * 学生を更新するAPI
     *
     * @param studentDetail 更新対象の学生詳細
     */
    @PutMapping("/update")
    public void updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
        studentService.updateStudent(studentDetail);
    }

    /**
     * 学生を削除するAPI
     *
     * @param id 学生ID
     * @return 削除結果
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteStudent(@PathVariable @Positive Long id) {
        return studentService.deleteStudent(id);
    }
}