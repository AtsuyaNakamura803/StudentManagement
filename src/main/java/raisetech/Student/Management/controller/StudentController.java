package raisetech.Student.Management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentNotFoundException;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

/**
 * 学生情報 Controller
 *
 * <p>学生情報の取得、登録、更新、削除を REST API で提供するコントローラー。
 * 本コントローラーでは、学生一覧取得のエンドポイントを /student/list にマッピング。</p>
 */
@RestController
@RequestMapping("/student") // 全ての学生関連エンドポイントのベースパス
public class StudentController {

    private final StudentService studentService;

    /**
     * コンストラクタ
     *
     * @param studentService 学生情報サービス
     */
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 全学生取得
     *
     * <p>GET /student/list で全学生情報（コース情報含む）を取得する。</p>
     *
     * @return 学生情報リスト
     */
    @GetMapping("/list")
    public ResponseEntity<List<StudentDetail>> getAllStudents() {
        List<StudentDetail> students = studentService.getStudentList();
        return ResponseEntity.ok(students);
    }

    /**
     * 学生IDで取得
     *
     * <p>GET /student/{id} で指定IDの学生情報を取得する。</p>
     *
     * @param id 学生ID
     * @return 学生情報
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDetail> getStudentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentService.getStudentById(id));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 学生登録
     *
     * <p>POST /student で学生情報を登録する。</p>
     *
     * @param studentDetail 登録する学生情報
     * @return 登録後の学生情報
     */
    @PostMapping
    public ResponseEntity<StudentDetail> createStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail created = studentService.registerStudent(studentDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * 学生更新
     *
     * <p>PUT /student/{id} で学生情報を更新する。</p>
     *
     * @param id 学生ID
     * @param studentDetail 更新内容
     * @return 更新後の学生情報
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDetail> updateStudent(@PathVariable Long id,
                                                       @RequestBody StudentDetail studentDetail) {
        try {
            StudentDetail updated = studentService.updateStudent(id, studentDetail);
            return ResponseEntity.ok(updated);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 学生削除（論理削除）
     *
     * <p>DELETE /student/{id} で指定IDの学生を削除する。</p>
     *
     * @param id 学生ID
     * @return 削除結果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}