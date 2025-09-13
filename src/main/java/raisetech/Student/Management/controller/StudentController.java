package raisetech.Student.Management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生一覧取得です。
     *
     * @return 全受講生の詳細リスト
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDetail>> getAllStudents() {
        List<StudentDetail> students = service.getAllStudents();
        logger.info("受講生一覧取得件数: {}", students.size());
        return ResponseEntity.ok(students);
    }

    /**
     * 指定IDの受講生を取得します。
     *
     * @param id 受講生ID
     * @return 該当受講生詳細
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDetail> getStudentById(@PathVariable("id") Long id) {
        StudentDetail studentDetail = service.searchStudentById(id);
        logger.info("受講生取得: id={}", id);
        return ResponseEntity.ok(studentDetail);
    }

    /**
     * 受講生登録です。
     * 登録後は 201 Created と Location ヘッダを返します。
     *
     * @param studentDetail 登録対象の受講生詳細
     * @return 登録完了メッセージ
     */
    @PostMapping(value = "/registerStudent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerStudent(@RequestBody StudentDetail studentDetail) {
        studentDetail.validate();
        service.registerStudent(studentDetail);

        Long createdId = studentDetail.getStudent().getId();
        URI location = URI.create("/student/" + createdId);
        String message = studentDetail.getStudent().getName() + "さんの登録が完了しました。";

        logger.info("受講生登録完了: id={}, name={}", createdId, studentDetail.getStudent().getName());
        return ResponseEntity.created(location).body(message);
    }

    /**
     * 受講生更新です。
     *
     * @param studentDetail 更新対象の受講生詳細
     * @return 更新完了メッセージ
     */
    @PostMapping(value = "/updateStudent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        studentDetail.validate();
        service.updateStudent(studentDetail);

        String message = studentDetail.getStudent().getName() + "さんの更新が成功しました。";
        logger.info("受講生更新完了: id={}, name={}", studentDetail.getStudent().getId(),
                studentDetail.getStudent().getName());
        return ResponseEntity.ok(message);
    }
}