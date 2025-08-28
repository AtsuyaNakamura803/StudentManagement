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

    /**
     * 学生情報を新規登録または更新
     */
    @Transactional
    public int saveOrUpdateStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        Integer studentId = student.getId();

        // 論理削除されていないレコードでメール重複チェック
        Student existingByEmail = studentRepository.findByEmailAndNotDeleted(student.getEmail());
        if (existingByEmail != null && (studentId == null || !existingByEmail.getId().equals(studentId))) {
            throw new IllegalArgumentException("このメールアドレスは既に登録されています。");
        }

        // 新規登録
        if (studentId == null) {
            studentRepository.insert(student);
            studentId = student.getId();
        } else {
            // 更新時、論理削除された場合はメールをユニーク化
            if (student.isDeleted()) {
                student.setEmail(student.getEmail() + "-deleted-" + student.getId());
            }
            studentRepository.updateStudent(student);

            // 論理削除でない場合はコースを一旦削除
            if (!student.isDeleted()) {
                studentRepository.deleteCoursesByStudentId(studentId);
            }
        }

        // コース登録（論理削除されていない場合のみ）
        if (!student.isDeleted()) {
            List<StudentsCourses> coursesToSave = convertCourseNamesToStudentsCourses(studentId, studentDetail.getCourseNames());
            insertCourses(coursesToSave);
        }

        return studentId;
    }

    /**
     * 全学生情報取得（削除済みも含む）
     */
    public List<StudentDetail> findAllStudentDetails() {
        List<Student> students = studentRepository.findAll();
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

    /**
     * 論理削除する
     */
    @Transactional
    public void deleteStudent(Integer studentId) {
        Student student = studentRepository.findById(studentId);
        if (student != null && !student.isDeleted()) {
            student.setDeleted(true);
            student.setEmail(student.getEmail() + "-deleted-" + student.getId());
            studentRepository.updateStudent(student);
        }
    }

    /**
     * 論理削除済みを復活
     */
    @Transactional
    public void restoreStudent(Integer studentId) {
        Student student = studentRepository.findById(studentId);
        if (student != null && student.isDeleted()) {
            // 復活前に同じメールアドレスの有効レコードがないかチェック
            String originalEmail = student.getEmail().replaceAll("-deleted-\\d+$", "");
            List<Student> existing = studentRepository.findByEmailAll(originalEmail);

            if (existing.stream().anyMatch(s -> !s.isDeleted() && !s.getId().equals(studentId))) {
                // 衝突する場合はユニーク化
                student.setEmail(originalEmail + "-restored-" + student.getId());
            } else {
                student.setEmail(originalEmail);
            }

            student.setDeleted(false);
            studentRepository.updateStudent(student);
        }
    }
}