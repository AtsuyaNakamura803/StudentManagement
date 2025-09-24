package raisetech.Student.Management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

/**
 * 学生情報の REST API コントローラー。
 */
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * すべての学生情報を取得するAPI。
     * @return 学生詳細情報リスト
     */
    @GetMapping("/student/list")
    public List<StudentDetail> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * 指定IDの学生情報を取得するAPI。
     * @param id 学生ID
     * @return 学生詳細情報
     */
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable int id) {
        return studentService.getStudentById(id);
    }
}