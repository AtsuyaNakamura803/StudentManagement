package raisetech.Student.Management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.controller.converter.StudentConverter;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.repository.StudentCourseRepository;

import java.util.List;

/**
 * 学生サービスクラス。
 * 学生情報とコース情報の操作をまとめて提供する。
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    /** 学生リポジトリ */
    private final StudentRepository studentRepository;

    /** 学生コースリポジトリ */
    private final StudentCourseRepository studentCourseRepository;

    /**
     * 全学生とそのコース情報を取得。
     *
     * @return StudentDetail のリスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = studentCourseRepository.findAll();
        return StudentConverter.convertToStudentDetails(students, courses);
    }

    /**
     * 指定IDの学生詳細を取得。
     *
     * @param id 学生ID
     * @return StudentDetail
     */
    public StudentDetail getStudent(Long id) {
        Student student = studentRepository.findById(id);
        List<StudentCourse> courses = studentCourseRepository.findByStudentId(id);
        return StudentConverter.convertToStudentDetail(student, courses);
    }

    /**
     * 学生とコース情報を登録。
     *
     * @param studentDetail 登録する学生情報 + コース情報
     * @return 登録結果の StudentDetail
     */
    @Transactional
    public StudentDetail saveStudent(StudentDetail studentDetail) {
        studentRepository.insertStudent(studentDetail.toStudent());
        List<StudentCourse> courses = studentDetail.getCourses();
        if (courses != null && !courses.isEmpty()) {
            studentCourseRepository.insertAll(courses);
        }
        return studentDetail;
    }

    /**
     * 学生情報とコース情報を更新。
     *
     * @param studentDetail 更新する学生情報 + コース情報
     * @return 更新後の StudentDetail
     */
    @Transactional
    public StudentDetail updateStudent(StudentDetail studentDetail) {
        studentRepository.updateStudent(studentDetail.toStudent());
        List<StudentCourse> courses = studentDetail.getCourses();
        if (courses != null && !courses.isEmpty()) {
            studentCourseRepository.updateAll(courses);
        }
        return studentDetail;
    }

    /**
     * 学生情報およびそのコース情報を論理削除。
     *
     * @param id 削除する学生ID
     */
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteStudent(id);
        studentCourseRepository.deleteByStudentId(id);
    }
}