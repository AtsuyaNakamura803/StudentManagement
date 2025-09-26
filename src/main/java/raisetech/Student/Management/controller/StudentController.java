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

    /**
     * 学生全件取得（学生 + コース配列）
     * @return StudentDetail のリスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * 指定IDの学生取得
     * @param id 学生ID
     * @return StudentDetail
     */
    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable @Min(1) Long id) {
        return studentService.getStudent(id);
    }

    /**
     * 学生登録
     * @param studentDetail 学生情報
     * @return 登録後の StudentDetail
     */
    @PostMapping("/register")
    public StudentDetail registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
        return studentService.saveStudent(studentDetail);
    }

    /**
     * 学生更新
     * @param studentDetail 学生情報
     * @return 更新後の StudentDetail
     */
    @PutMapping("/update")
    public StudentDetail updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
        return studentService.updateStudent(studentDetail);
    }

    /**
     * 学生削除（論理削除）
     * @param id 学生ID
     */
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable @Min(1) Long id) {
        studentService.deleteStudent(id);
    }
}