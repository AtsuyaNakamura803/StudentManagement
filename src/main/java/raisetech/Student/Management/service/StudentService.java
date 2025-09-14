package raisetech.Student.Management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<StudentDetail> getAllStudents() {
        List<Student> students = repository.searchAllStudents();
        return students.stream()
                .map(s -> new StudentDetail(s, repository.searchStudentCourse(s.getId())))
                .collect(Collectors.toList());
    }

    public StudentDetail searchStudentById(Long id) {
        Student student = repository.searchStudent(id);
        if (student == null) {
            logger.warn("Student not found: id={}", id);
            throw new IllegalArgumentException("指定された受講生が存在しません");
        }
        List<StudentCourse> courses = repository.searchStudentCourse(id);
        return new StudentDetail(student, courses);
    }

    public void registerStudent(StudentDetail studentDetail) {
        studentDetail.validate();

        int count = repository.registerStudent(studentDetail.getStudent());
        if (count == 0 || studentDetail.getStudent().getId() == null) {
            logger.error("Student insert failed: {}", studentDetail.getStudent());
            throw new IllegalStateException("受講生登録に失敗しました");
        }

        for (StudentCourse sc : studentDetail.getCourses()) {
            sc.setStudentId(studentDetail.getStudent().getId());
            repository.registerStudentCourse(sc);
        }

        logger.info("Student registered successfully: id={}", studentDetail.getStudent().getId());
    }

    public void updateStudent(StudentDetail studentDetail) {
        studentDetail.validate();

        repository.updateStudent(studentDetail.getStudent());

        for (StudentCourse sc : studentDetail.getCourses()) {
            if (sc.getId() == null) {
                sc.setStudentId(studentDetail.getStudent().getId());
                repository.registerStudentCourse(sc);
            } else {
                repository.updateStudentCourse(sc);
            }
        }

        logger.info("Student updated successfully: id={}", studentDetail.getStudent().getId());
    }

    /**
     * 学生と紐づくコースを論理削除します。
     *
     * @param id 学生ID
     * @return 削除結果情報
     */
    public Map<String, Object> deleteStudent(Long id) {
        Student student = repository.searchStudent(id);
        if (student == null || Boolean.TRUE.equals(student.getIsDeleted())) {
            logger.warn("Student not found or already deleted: id={}", id);
            throw new IllegalArgumentException("指定された受講生が存在しません");
        }

        int studentCount = repository.deleteStudent(id);
        int courseCount = repository.deleteStudentCourses(id);

        if (studentCount == 0) {
            logger.error("Failed to delete student: id={}", id);
            throw new IllegalStateException("受講生の削除に失敗しました");
        }

        logger.info("Student logically deleted: id={}, coursesDeleted={}", id, courseCount);

        Map<String, Object> result = new HashMap<>();
        result.put("studentId", id);
        result.put("studentDeleted", studentCount);
        result.put("coursesDeleted", courseCount);

        return result;
    }
}