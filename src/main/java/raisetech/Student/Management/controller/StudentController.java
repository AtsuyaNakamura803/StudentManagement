package raisetech.Student.Management.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.ErrorResponse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentNotFoundException;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

/**
 * 学生情報管理 Controller
 *
 * <p>学生の一覧取得、登録、更新、削除などの REST API を提供。
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 学生一覧取得
     *
     * GET http://localhost:8080/student/list
     *
     * @return 学生 + コース情報のリスト
     */
    @GetMapping("/list")
    public List<StudentDetail> getStudentList() {
        return studentService.getStudentList();
    }

    /**
     * 学生IDで取得
     *
     * GET http://localhost:8080/student/{id}
     *
     * @param id 学生ID
     * @return 学生 + コース情報
     */
    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    /**
     * 学生登録
     *
     * POST http://localhost:8080/student/register
     *
     * @param studentDetail 登録する学生情報 + コース情報
     * @return 登録された学生情報
     */
    @PostMapping("/register")
    public StudentDetail registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        return studentService.registerStudent(studentDetail);
    }

    /**
     * 学生更新
     *
     * PUT http://localhost:8080/student/{id}
     *
     * @param id 更新対象の学生ID
     * @param studentDetail 更新内容
     * @return 更新後の学生情報
     */
    @PutMapping("/{id}")
    public StudentDetail updateStudent(
            @PathVariable Long id,
            @RequestBody @Valid StudentDetail studentDetail) {
        return studentService.updateStudent(id, studentDetail);
    }

    /**
     * 学生削除（論理削除）
     *
     * DELETE http://localhost:8080/student/{id}
     *
     * @param id 削除対象の学生ID
     */
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    /**
     * StudentNotFoundException の例外ハンドリング
     *
     * <p>指定IDの学生が存在しない場合に 404 エラーを返す。
     *
     * @param ex 例外オブジェクト
     * @return エラー情報
     */
    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleStudentNotFound(StudentNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}