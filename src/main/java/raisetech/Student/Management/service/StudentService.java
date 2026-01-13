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
import java.util.Optional;

/**
 * 学生情報サービス
 *
 * <p>学生情報およびそのコース情報の取得、登録、更新、削除を提供するサービスクラス。
 * StudentRepository / StudentCourseRepository は MyBatis の XML マッパーを使用。</p>
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    /**
     * コンストラクタ。
     *
     * @param studentRepository 学生情報リポジトリ（XML Mapper対応）
     * @param studentCourseRepository 学生コース情報リポジトリ（XML Mapper対応）
     */
    public StudentService(StudentRepository studentRepository,
                          StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    /**
     * 学生一覧取得
     *
     * <p>全学生とコース情報を取得する。</p>
     *
     * @return 学生詳細情報リスト
     */
    @Transactional(readOnly = true)
    public List<StudentDetail> getStudentList() {
        List<Student> students = studentRepository.findAll(); // XML Mapperで全件取得
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
     * @return 学生詳細情報
     * @throws StudentNotFoundException 存在しない場合
     */
    @Transactional(readOnly = true)
    public StudentDetail getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        Student student = optionalStudent.orElseThrow(
                () -> new StudentNotFoundException("Student not found with id: " + id)
        );
        List<StudentCourse> courses = studentCourseRepository.findByStudentId(id);
        return new StudentDetail(student, courses);
    }

    /**
     * 学生登録
     *
     * <p>学生情報とコース情報を登録する。</p>
     *
     * @param studentDetail 登録する学生情報
     * @return 登録後の学生詳細情報
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
     * 学生情報更新
     *
     * <p>学生情報とコース情報を更新する。既存コースは削除して再登録。</p>
     *
     * @param id 更新対象の学生ID
     * @param studentDetail 更新内容
     * @return 更新後の学生詳細情報
     * @throws StudentNotFoundException 存在しない場合
     */
    @Transactional
    public StudentDetail updateStudent(Long id, StudentDetail studentDetail) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }

        Student student = studentDetail.toStudent();
        student.setId(id);
        studentRepository.updateStudent(student);

        // 既存コースを削除して再挿入
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
     * @throws StudentNotFoundException 存在しない場合
     */
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }

        studentRepository.deleteStudent(id);
        studentCourseRepository.deleteByStudentId(id);
    }
}