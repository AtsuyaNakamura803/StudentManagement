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
 * 学生 REST API コントローラー。
 * 学生情報およびコース情報の CRUD 操作を提供。
 */
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Validated
public class StudentController {

    /** 学生サービス */
    private final StudentService studentService;

    /**
     * 全学生とコース情報を取得。
     *
     * @return StudentDetail のリスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * 指定IDの学生詳細を取得。
     *
     * @param id 学生ID
     * @return StudentDetail
     */
    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable @Min(1) Long id) {
        return studentService.getStudent(id);
    }

    /**
     * 学生とコース情報を登録。
     *
     * @param studentDetail 登録する情報
     * @return 登録結果
     */
    @PostMapping("/register")
    public StudentDetail registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
        return studentService.saveStudent(studentDetail);
    }

    /**
     * 学生情報とコース情報を更新。
     *
     * @param studentDetail 更新する情報
     * @return 更新結果
     */
    @PutMapping("/update")
    public StudentDetail updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
        return studentService.updateStudent(studentDetail);
    }

    /**
     * 学生情報とそのコース情報を削除。
     *
     * @param id 削除する学生ID
     */
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable @Min(1) Long id) {
        studentService.deleteStudent(id);
    }
}