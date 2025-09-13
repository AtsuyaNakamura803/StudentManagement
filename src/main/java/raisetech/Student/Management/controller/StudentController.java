package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.net.URI;
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
     * 全件検索を行います。
     *
     * @return 受講生詳細のリスト
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDetail>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    /**
     * 受講生検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 該当受講生の詳細情報
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDetail> getStudentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.searchStudentById(id));
    }

    /**
     * 受講生登録です。
     * 201 Created + Location ヘッダで返します。
     *
     * @param studentDetail 登録対象の受講生情報
     * @return 登録結果メッセージ
     */
    @PostMapping(value = "/registerStudent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerStudent(@RequestBody StudentDetail studentDetail) {
        service.registerStudent(studentDetail);

        Long createdId = studentDetail.getStudent().getId();
        URI location = URI.create("/student/" + createdId);

        String message = studentDetail.getStudent().getName() + "さんの登録が完了しました。";
        return ResponseEntity.created(location).body(message);
    }

    /**
     * 受講生更新です。
     * 旧 URL /updateStudent に POST で対応
     *
     * @param studentDetail 更新対象の受講生情報
     * @return 更新結果メッセージ
     */
    @PostMapping(value = "/updateStudent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentLegacy(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        String message = studentDetail.getStudent().getName() + "さんの更新が成功しました。";
        return ResponseEntity.ok(message);
    }
}