package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.repository.StudentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public int saveOrUpdateStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        Integer studentId = student.getId();

        // =========================
        // メール重複チェック
        // =========================
        Student existingByEmail = studentRepository.findByEmail(student.getEmail());
        if (existingByEmail != null && (studentId == null || !existingByEmail.getId().equals(studentId))) {
            throw new IllegalArgumentException("このメールアドレスは既に登録されています。");
        }

        // =========================
        // 新規 or 更新
        // =========================
        if (studentId == null) {
            studentRepository.insert(student);
            studentId = student.getId();
        } else {
            studentRepository.updateStudent(student);
            studentRepository.deleteCoursesByStudentId(studentId);
        }

        // =========================
        // courseNames から StudentsCourses を生成して保存
        // =========================
        List<StudentsCourses> coursesToSave = convertCourseNamesToStudentsCourses(studentId, studentDetail.getCourseNames());
        insertCourses(coursesToSave);

        return studentId;
    }

    public List<StudentDetail> findAllStudentDetails() {
        List<Student> students = studentRepository.search();
        return students.stream()
                .map(this::buildStudentDetail)
                .collect(Collectors.toList());
    }

    public Optional<Student> findById(Integer id) {
        return Optional.ofNullable(studentRepository.findById(id));
    }

    public Optional<StudentDetail> findStudentDetailById(Integer id) {
        return findById(id).map(this::buildStudentDetail);
    }

    // -------------------- private helpers --------------------

    /**
     * カンマ区切りの courseNames から StudentsCourses リストを生成
     */
    private List<StudentsCourses> convertCourseNamesToStudentsCourses(Integer studentId, String courseNames) {
        List<StudentsCourses> list = new ArrayList<>();
        if (courseNames != null && !courseNames.isEmpty()) {
            String[] arr = courseNames.split(",");
            LocalDate start = LocalDate.now();
            LocalDate end = start.plusMonths(3);
            for (String name : arr) {
                StudentsCourses sc = new StudentsCourses();
                sc.setStudentId(studentId);
                sc.setCourseName(name.trim());
                sc.setCourseStartAt(start);
                sc.setCourseEndAt(end);
                list.add(sc);
            }
        }
        return list;
    }

    /**
     * StudentsCourses リストを DB に保存
     */
    private void insertCourses(List<StudentsCourses> courses) {
        if (courses != null && !courses.isEmpty()) {
            for (StudentsCourses sc : courses) {
                studentRepository.insertStudentCourse(
                        sc.getStudentId(),
                        sc.getCourseName(),
                        sc.getCourseStartAt(),
                        sc.getCourseEndAt()
                );
            }
        }
    }

    /**
     * Student から StudentDetail を生成
     */
    private StudentDetail buildStudentDetail(Student student) {
        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);

        List<StudentsCourses> courses = studentRepository.findCoursesByStudentId(student.getId());
        detail.setStudentsCourses(courses);

        if (courses != null && !courses.isEmpty()) {
            String courseNames = courses.stream()
                    .map(StudentsCourses::getCourseName)
                    .collect(Collectors.joining(","));
            detail.setCourseNames(courseNames);
        }

        return detail;
    }
}