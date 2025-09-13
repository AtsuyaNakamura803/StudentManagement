package raisetech.Student.Management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * 受講生一覧取得
     *
     * @return 受講生詳細リスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = repository.searchAllStudents();
        return students.stream()
                .map(s -> new StudentDetail(s, repository.searchStudentCourses(s.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 受講生検索
     *
     * @param id 受講生ID
     * @return 該当受講生詳細
     */
    public StudentDetail searchStudentById(Long id) {
        Student student = repository.searchStudent(id);
        if (student == null) {
            logger.warn("Student not found: id={}", id);
            throw new IllegalArgumentException("指定された受講生が存在しません");
        }
        List<StudentsCourses> courses = repository.searchStudentCourses(id);
        return new StudentDetail(student, courses);
    }

    /**
     * 受講生登録
     *
     * @param studentDetail 登録対象
     */
    public void registerStudent(StudentDetail studentDetail) {
        studentDetail.validate(); // 入力検証

        // Student 登録
        int count = repository.registerStudent(studentDetail.getStudent());
        if (count == 0) {
            logger.error("Student insert failed: {}", studentDetail.getStudent());
            throw new IllegalStateException("受講生登録に失敗しました");
        }

        // StudentsCourses 登録
        for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
            sc.setStudentId(studentDetail.getStudent().getId());
            repository.registerStudentsCourses(sc);
        }

        logger.info("Student registered successfully: id={}", studentDetail.getStudent().getId());
    }

    /**
     * 受講生更新
     *
     * @param studentDetail 更新対象
     */
    public void updateStudent(StudentDetail studentDetail) {
        studentDetail.validate();

        // Student 更新
        repository.updateStudent(studentDetail.getStudent());

        // StudentsCourses 更新／追加
        for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
            if (sc.getId() == null) { // Long 型なら null 判定が可能
                sc.setStudentId(studentDetail.getStudent().getId());
                repository.registerStudentsCourses(sc);
            } else {
                repository.updateStudentsCourses(sc);
            }
        }

        logger.info("Student updated successfully: id={}", studentDetail.getStudent().getId());
    }
}