package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    // -----------------------------
    // 受講生全件取得
    // -----------------------------
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.getAllStudents();
    }

    // -----------------------------
    // 受講生情報取得（ID指定）
    // -----------------------------
    @GetMapping("/student/{id}")
    public StudentDetail nowStudent(@PathVariable("id") String id) {
        return service.searchStudentById(id);
    }

    // -----------------------------
    // 受講生登録
    // -----------------------------
    @PostMapping("/registerStudent")
    public String registerStudent(@RequestBody StudentDetail studentDetail) {
        service.registerStudent(studentDetail);
        return studentDetail.getStudent().getName() + "さんの登録が完了しました。";
    }

    // -----------------------------
    // 受講生更新
    // -----------------------------
    @PostMapping("/updateStudent")
    public String updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return studentDetail.getStudent().getName() + "さんの更新が成功しました。";
    }
}