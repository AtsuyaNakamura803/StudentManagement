package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;

import java.util.List;

/**
 * 学生サービス
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDetail> getAllStudents() {
        return studentRepository.findAllStudentDetails();
    }

    public StudentDetail getStudent(int id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public void saveStudent(StudentDetail studentDetail) {
        studentRepository.saveStudent(studentDetail);
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        studentRepository.updateStudent(studentDetail);
    }

    @Transactional
    public void deleteStudent(int id) {
        studentRepository.deleteStudent(id);
    }
}