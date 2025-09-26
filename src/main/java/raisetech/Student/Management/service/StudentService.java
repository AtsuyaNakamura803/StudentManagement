package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentCourseRepository;
import raisetech.Student.Management.repository.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * 学生情報 Service
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
     * 全学生の詳細情報を取得
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = studentCourseRepository.findAll();
        return students.stream()
                .map(s -> {
                    List<StudentCourse> studentCourses = courses.stream()
                            .filter(c -> c.getStudentId().equals(s.getId()))
                            .collect(Collectors.toList());
                    return new StudentDetail(s.getId(), s.getName(), s.getEmail(), s.getAge(), s.getSex(), s.getDeleted(), studentCourses);
                })
                .collect(Collectors.toList());
    }

    /**
     * 学生登録
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail detail) {
        Student student = detail.toStudent();
        studentRepository.insertStudent(student);

        if (detail.getCourses() != null && !detail.getCourses().isEmpty()) {
            detail.getCourses().forEach(c -> c.setStudentId(student.getId()));
            studentCourseRepository.insertAll(detail.getCourses());
        }

        return detail;
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