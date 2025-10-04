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
 */
@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail detail = studentService.registerStudent(studentDetail);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetail> getStudent(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDetail> updateStudent(@PathVariable @Positive Long id,
                                                       @RequestBody @Valid StudentDetail studentDetail) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDetail));
    }

    @DeleteMapping("/{id}")
    public DeleteStudentResultDTO deleteStudent(@PathVariable @Positive Long id) {
        return studentService.deleteStudent(id);
    }
}