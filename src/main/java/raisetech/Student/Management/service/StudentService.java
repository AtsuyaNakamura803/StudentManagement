package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.controller.converter.StudentConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

/**
 * 学生情報および履修コース情報のビジネスロジックを提供するサービスクラスです。
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * すべての学生情報（履修コース含む）を取得します。
     *
     * @return 学生詳細リスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = students.stream()
                .flatMap(s -> studentRepository.findCoursesByStudentId(s.getId()).stream())
                .collect(Collectors.toList());

        return StudentConverter.convertStudentDetails(students, courses);
    }

    /**
     * 指定IDの学生情報（履修コース含む）を取得します。
     *
     * @param id 学生ID
     * @return 学生詳細
     */
    public StudentDetail searchStudentById(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new IllegalArgumentException("指定された学生IDが存在しません: " + id);
        }
        List<StudentCourse> courses = studentRepository.findCoursesByStudentId(id);
        return new StudentDetail(student, courses);
    }

    /**
     * 学生情報および履修コース情報を登録します。
     *
     * @param studentDetail 登録対象の学生詳細
     */
    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        if (studentDetail == null || studentDetail.getStudent() == null) {
            throw new IllegalArgumentException("StudentDetail または Student が null です");
        }

        studentRepository.insertStudent(studentDetail.getStudent());

        List<StudentCourse> courses = studentDetail.getCourses();
        if (courses != null && !courses.isEmpty()) {
            for (StudentCourse sc : courses) {
                sc.setStudentId(studentDetail.getStudent().getId());
                studentRepository.insertStudentCourse(sc);
            }
        }
    }

    /**
     * 学生情報および履修コース情報を更新します。
     *
     * @param studentDetail 更新対象の学生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        if (studentDetail == null || studentDetail.getStudent() == null) {
            throw new IllegalArgumentException("StudentDetail または Student が null です");
        }

        studentRepository.updateStudent(studentDetail.getStudent());
        // コース更新処理は必要に応じて追加
    }

    /**
     * 学生情報および履修コース情報を論理削除します。
     *
     * @param id 学生ID
     * @return 削除結果情報
     */
    @Transactional
    public Map<String, Object> deleteStudent(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null || Boolean.TRUE.equals(student.getIsDeleted())) {
            throw new IllegalArgumentException("指定された学生IDは存在しないか既に削除されています: " + id);
        }

        studentRepository.deleteStudentCourses(id);
        studentRepository.deleteStudent(id);

        Map<String, Object> result = new HashMap<>();
        result.put("studentId", id);
        result.put("studentDeleted", 1);
        return result;
    }
}