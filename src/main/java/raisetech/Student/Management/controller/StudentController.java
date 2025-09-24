package raisetech.Student.Management.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

/**
 * 学生情報 Controller
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable int id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        studentService.saveStudent(studentDetail);
        return ResponseEntity.ok("登録完了");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
        studentService.updateStudent(studentDetail);
        return ResponseEntity.ok("更新完了");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("削除完了");
    }
}