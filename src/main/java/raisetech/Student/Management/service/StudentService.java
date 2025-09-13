package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.domain.StudentDetail;
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

    /**
     * 受講生一覧検索です。
     * 全件検索を行います。
     *
     * @return 受講生詳細のリスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = repository.searchAllStudents();
        return students.stream()
                .map(student -> new StudentDetail(student, repository.searchStudentCourses(student.getId())))
                .toList();
    }

    /**
     * 受講生検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 該当受講生の詳細情報
     */
    public StudentDetail searchStudentById(Long id) {
        System.out.println("searchStudentById called with id: " + id);

        Student student = repository.searchStudent(id);
        System.out.println("repository returned: " + student);

        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "受講生が見つかりません: " + id);
        }

        List<StudentsCourses> courses = repository.searchStudentCourses(id);
        return new StudentDetail(student, courses);
    }

    /**
     * 受講生登録です。
     *
     * @param studentDetail 登録対象の受講生情報
     */
    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        Long studentId = studentDetail.getStudent().getId();

        for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
            sc.setStudentId(studentId);
            sc.setCourseStartAt(LocalDate.now());
            sc.setCourseEndAt(LocalDate.now().plusMonths(8));
            repository.registerStudentsCourses(sc);
        }
    }

    /**
     * 受講生更新です。
     *
     * @param studentDetail 更新対象の受講生情報
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for (StudentsCourses course : studentDetail.getStudentsCourses()) {
            repository.updateStudentsCourses(course);
        }
    }
}