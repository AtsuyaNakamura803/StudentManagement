package raisetech.Student.Management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;

/**
 * 学生 REST API
 */
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService studentService;

    /** 学生一覧取得 */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    /** 学生取得 */
    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable @Min(1) Long id) {
        return studentService.getStudent(id);
    }

    /** 学生登録 */
    @PostMapping("/register")
    public StudentDetail registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
        return studentService.saveStudent(studentDetail);
    }

    /** 学生更新 */
    @PutMapping("/update")
    public StudentDetail updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
        return studentService.updateStudent(studentDetail);
    }

    /** 学生削除 */
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable @Min(1) Long id) {
        studentService.deleteStudent(id);
    }
}