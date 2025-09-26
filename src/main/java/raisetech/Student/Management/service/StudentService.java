package raisetech.Student.Management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.controller.converter.StudentConverter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentCourseRepository;
import raisetech.Student.Management.repository.StudentRepository;

import java.util.List;

/**
 * 学生サービス
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    /**
     * 学生全件を取得し、コース情報を付与
     */
    @Transactional(readOnly = true)
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = studentCourseRepository.findAll();
        return StudentConverter.convertToStudentDetails(students, courses);
    }

    /**
     * 指定IDの学生を取得
     */
    @Transactional(readOnly = true)
    public StudentDetail getStudent(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new java.util.NoSuchElementException("Student not found: " + id);
        }
        List<StudentCourse> courses = studentCourseRepository.findByStudentId(id);
        return StudentConverter.convertToStudentDetail(student, courses);
    }

    /**
     * 学生登録
     */
    @Transactional
    public StudentDetail saveStudent(StudentDetail studentDetail) {
        Student student = studentDetail.toStudent();
        studentRepository.insertStudent(student);
        if (studentDetail.getCourses() != null) {
            studentDetail.getCourses().forEach(c -> c.setStudentId(student.getId()));
            studentCourseRepository.insertAll(studentDetail.getCourses());
        }
        return getStudent(student.getId());
    }

    /**
     * 学生更新
     */
    @Transactional
    public StudentDetail updateStudent(StudentDetail studentDetail) {
        Student student = studentDetail.toStudent();
        studentRepository.updateStudent(student);
        if (studentDetail.getCourses() != null) {
            studentCourseRepository.updateAll(studentDetail.getCourses());
        }
        return getStudent(student.getId());
    }

    /**
     * 学生削除（論理削除）
     */
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteStudent(id);
        studentCourseRepository.deleteByStudentId(id);
    }
}