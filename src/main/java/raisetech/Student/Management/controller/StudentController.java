package raisetech.Student.Management.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

/**
 * 学生情報 API Controller
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 全学生を取得
     */
    @GetMapping("/list")
    public ResponseEntity<List<StudentDetail>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * 学生登録
     */
    @PostMapping("/register")
    public ResponseEntity<StudentDetail> registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
        return ResponseEntity.ok(studentService.registerStudent(studentDetail));
    }

    /**
     * 学生削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}