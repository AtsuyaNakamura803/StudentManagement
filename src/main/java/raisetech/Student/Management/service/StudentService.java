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

/**
 * 学生関連のビジネスロジックを提供するサービス。
 */
@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * 受講生一覧検索です。
     *
     * @return 全受講生のリスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = repository.searchAllStudents();
        return students.stream()
                .map(s -> new StudentDetail(s, repository.searchStudentCourses(s.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 受講生検索です。
     *
     * @param id 受講生ID
     * @return 該当受講生の詳細
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
     * 受講生登録です。
     *
     * @param studentDetail 登録対象
     */
    public void registerStudent(StudentDetail studentDetail) {
        studentDetail.validate();

        int count = repository.registerStudent(studentDetail.getStudent());
        if (count == 0 || studentDetail.getStudent().getId() == null) {
            logger.error("Student insert failed: {}", studentDetail.getStudent());
            throw new IllegalStateException("受講生登録に失敗しました");
        }

        for (StudentsCourses sc : studentDetail.getCourses()) {
            sc.setStudentId(studentDetail.getStudent().getId());
            repository.registerStudentsCourses(sc);
        }

        logger.info("Student registered successfully: id={}", studentDetail.getStudent().getId());
    }

    /**
     * 受講生更新です。
     *
     * @param studentDetail 更新対象
     */
    public void updateStudent(StudentDetail studentDetail) {
        studentDetail.validate();

        repository.updateStudent(studentDetail.getStudent());

        for (StudentsCourses sc : studentDetail.getCourses()) {
            if (sc.getId() == null) {
                sc.setStudentId(studentDetail.getStudent().getId());
                repository.registerStudentsCourses(sc);
            } else {
                repository.updateStudentsCourses(sc);
            }
        }

        logger.info("Student updated successfully: id={}", studentDetail.getStudent().getId());
    }
}