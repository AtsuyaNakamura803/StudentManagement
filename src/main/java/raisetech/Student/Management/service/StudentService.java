package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行います。
     *
     * @return 受講生詳細のリスト
     */
    public List<StudentDetail> getAllStudents() {
        logger.info("Fetching all students");
        List<Student> students = repository.searchAllStudents();
        return students.stream()
                .map(student -> new StudentDetail(student, repository.searchStudentCourses(student.getId())))
                .toList();
    }

    /**
     * 受講生検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 該当受講生の詳細情報
     */
    public StudentDetail searchStudentById(Long id) {
        logger.info("Searching student by id={}", id);
        Student student = repository.searchStudent(id);
        if (student == null) {
            logger.warn("Student not found: id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "受講生が見つかりません: " + id);
        }
        List<StudentsCourses> courses = repository.searchStudentCourses(id);
        return new StudentDetail(student, courses);
    }

    /**
     * 受講生登録です。
     *
     * @param studentDetail 登録対象の受講生情報
     */
    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        logger.info("Registering student: {}", studentDetail.getStudent().getName());
        int result = repository.registerStudent(studentDetail.getStudent());
        if (result == 0 || studentDetail.getStudent().getId() == 0) {
            logger.error("Failed to register student: {}", studentDetail.getStudent().getName());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "受講生登録に失敗しました");
        }
        Long studentId = studentDetail.getStudent().getId();
        for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
            sc.setStudentId(studentId);
            sc.setCourseStartAt(LocalDate.now());
            sc.setCourseEndAt(LocalDate.now().plusMonths(8));
            int courseResult = repository.registerStudentsCourses(sc);
            if (courseResult == 0) {
                logger.error("Failed to register course: {} for studentId={}", sc.getCourseName(), studentId);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "受講生コース登録に失敗しました");
            }
        }
        logger.info("Student registered successfully: id={}", studentId);
    }

    /**
     * 受講生更新です。
     *
     * @param studentDetail 更新対象の受講生情報
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        logger.info("Updating student: id={}", studentDetail.getStudent().getId());
        int updateResult = repository.updateStudent(studentDetail.getStudent());
        if (updateResult == 0) {
            logger.warn("No student updated: id={}", studentDetail.getStudent().getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "更新対象の受講生が存在しません: " + studentDetail.getStudent().getId());
        }
        for (StudentsCourses course : studentDetail.getStudentsCourses()) {
            int courseUpdateResult = repository.updateStudentsCourses(course);
            if (courseUpdateResult == 0) {
                logger.warn("No course updated: courseId={} studentId={}", course.getId(), course.getStudentId());
            }
        }
        logger.info("Student updated successfully: id={}", studentDetail.getStudent().getId());
    }
}