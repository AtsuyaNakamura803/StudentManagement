package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // -----------------------------
    // 受講生全件取得
    // -----------------------------
    public List<StudentDetail> getAllStudents() {
        List<Student> students = repository.searchAllStudents();
        return students.stream().map(student -> {
            StudentDetail detail = new StudentDetail();
            detail.setStudent(student);
            detail.setStudentsCourses(repository.searchStudentCourses(student.getId()));
            return detail;
        }).toList();
    }

    // -----------------------------
    // 受講生情報取得（ID指定）
    // -----------------------------
    public StudentDetail searchStudentById(String id) {
        int studentId;
        try {
            studentId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("IDは整数で指定してください: " + id);
        }

        Student student = repository.searchStudent(studentId);
        List<StudentsCourses> studentsCourses = repository.searchStudentCourses(studentId);

        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentsCourses(studentsCourses);

        return studentDetail;
    }

    // -----------------------------
    // 受講生登録
    // -----------------------------
    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent()); // ID 自動採番
        int studentId = studentDetail.getStudent().getId(); // null になっていないか確認
        for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
            sc.setStudentId(studentId);
            sc.setCourseStartAt(LocalDate.now());
            sc.setCourseEndAt(LocalDate.now().plusMonths(8));
            repository.registerStudentsCourses(sc);
        }
    }

    // -----------------------------
    // 受講生更新
    // -----------------------------
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for (StudentsCourses course : studentDetail.getStudentsCourses()) {
            repository.updateStudentsCourses(course);
        }
    }
}