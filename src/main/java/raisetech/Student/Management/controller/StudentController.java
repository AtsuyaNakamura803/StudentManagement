package raisetech.Student.Management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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

    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        logger.info("Fetching all students");
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable @Size(min = 1, max = 3) Long id) {
        logger.info("Fetching student by id={}", id);
        return studentService.searchStudentById(id);
    }

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

    @PutMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody StudentDetail studentDetail) {
        studentService.updateStudent(studentDetail);
        logger.info("Student updated successfully: id={}", studentDetail.getStudent().getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id) {
        logger.info("Deleting student logically: id={}", id);
        Map<String, Object> result = studentService.deleteStudent(id);
        return ResponseEntity.ok(result);
    }
}