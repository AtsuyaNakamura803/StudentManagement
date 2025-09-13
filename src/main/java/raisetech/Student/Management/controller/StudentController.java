package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return 受講生一覧(全件)
     */
    @GetMapping
    public ResponseEntity<List<StudentDetail>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    /**
     * 受講生検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDetail> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(service.searchStudentById(id));
    }

    /**
     * 受講生登録です。
     *
     * @param studentDetail 登録対象の受講生情報
     * @return 登録結果メッセージ
     */
    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody StudentDetail studentDetail) {
        service.registerStudent(studentDetail);
        return ResponseEntity.ok(studentDetail.getStudent().getName() + "さんの登録が完了しました。");
    }

    /**
     * 受講生更新です。
     *
     * @param studentDetail 更新対象の受講生情報
     * @return 更新結果メッセージ
     */
    @PutMapping
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok(studentDetail.getStudent().getName() + "さんの更新が成功しました。");
    }
}