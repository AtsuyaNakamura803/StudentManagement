package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.repository.StudentCourseRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生情報 Service
 *
 * <p>学生およびそのコース情報の取得、登録、更新、削除処理を提供するサービスクラス。
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public StudentService(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    /**
     * 学生一覧取得
     *
     * @return 学生 + コース情報のリスト
     */
    @Transactional(readOnly = true)
    public List<StudentDetail> getStudentList() {
        List<Student> students = studentRepository.findAll();
        List<StudentDetail> details = new ArrayList<>();
        for (Student student : students) {
            List<StudentCourse> courses = studentCourseRepository.findByStudentId(student.getId());
            details.add(new StudentDetail(student, courses));
        }
        return details;
    }

    /**
     * 学生IDで取得
     *
     * @param id 学生ID
     * @return 学生 + コース情報
     * @throws StudentNotFoundException 指定IDの学生が存在しない場合
     */
    @Transactional(readOnly = true)
    public StudentDetail getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        List<StudentCourse> courses = studentCourseRepository.findByStudentId(id);
        return new StudentDetail(student, courses);
    }

    /**
     * 学生登録
     *
     * @param studentDetail 登録する学生情報 + コース情報
     * @return 登録された学生情報
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.toStudent();
        studentRepository.insertStudent(student);

        List<StudentCourse> courses = studentDetail.toStudentCourses(student.getId());
        if (!courses.isEmpty()) {
            studentCourseRepository.insertAll(courses);
        }

        return new StudentDetail(student, courses);
    }

    /**
     * 学生更新
     *
     * @param id 更新対象の学生ID
     * @param studentDetail 更新内容
     * @return 更新後の学生情報
     * @throws StudentNotFoundException 指定IDの学生が存在しない場合
     */
    @Transactional
    public StudentDetail updateStudent(Long id, StudentDetail studentDetail) {
        // 存在確認と取得
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        Student student = studentDetail.toStudent();
        student.setId(id);
        studentRepository.updateStudent(student);

        studentCourseRepository.deleteByStudentId(id);
        List<StudentCourse> courses = studentDetail.toStudentCourses(id);
        if (!courses.isEmpty()) {
            studentCourseRepository.insertAll(courses);
        }

        return new StudentDetail(student, courses);
    }

    /**
     * 学生削除（論理削除）
     *
     * @param id 削除対象の学生ID
     * @throws StudentNotFoundException 指定IDの学生が存在しない場合
     */
    @Transactional
    public void deleteStudent(Long id) {
        // 存在確認と取得
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        studentRepository.deleteStudent(id);
        studentCourseRepository.deleteByStudentId(id);
    }
}