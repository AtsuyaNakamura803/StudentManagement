package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.domain.DeleteStudentResultDTO;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.repository.StudentCourseRepository;
import raisetech.Student.Management.controller.converter.StudentConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

/**
 * 学生情報サービス
 *
 * <p>
 * 学生およびコース情報の登録・更新・取得・削除を管理するサービスクラス。
 * Bean Validation に基づく入力チェックを行い、@Transactional によって
 * 登録・更新・削除処理の整合性を保証する。
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public StudentService(StudentRepository studentRepository,
                          StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    /**
     * 学生登録（学生＋コース）
     *
     * @param studentDetail 登録情報（@Valid でコース含む全項目を検証）
     * @return 登録後の StudentDetail（ID反映済）
     */
    @Transactional
    public StudentDetail registerStudent(@Valid StudentDetail studentDetail) {
        validateCourses(studentDetail.getCourses());

        Student student = studentDetail.toStudent();
        studentRepository.insertStudent(student);

        // ID を StudentDetail に反映
        studentDetail.setId(student.getId());

        // コース保存
        List<StudentCourse> courses = studentDetail.getCourses();
        if (courses != null && !courses.isEmpty()) {
            for (StudentCourse course : courses) {
                course.setStudentId(student.getId());
            }
            studentCourseRepository.insertAll(courses);
        }

        return studentDetail;
    }

    /**
     * 学生更新（学生＋コース）
     *
     * @param id 更新対象学生ID
     * @param studentDetail 更新情報（@Valid）
     * @return 更新後 StudentDetail
     */
    @Transactional
    public StudentDetail updateStudent(Long id, @Valid StudentDetail studentDetail) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new NoSuchElementException("Student not found with id: " + id);
        }

        validateCourses(studentDetail.getCourses());

        student.setName(studentDetail.getName());
        student.setEmail(studentDetail.getEmail());
        student.setAge(studentDetail.getAge());
        student.setSex(studentDetail.getGender());
        studentRepository.updateStudent(student);

        List<StudentCourse> courses = studentDetail.getCourses();
        if (courses != null && !courses.isEmpty()) {
            List<StudentCourse> toUpdate = new ArrayList<>();
            List<StudentCourse> toInsert = new ArrayList<>();

            for (StudentCourse course : courses) {
                course.setStudentId(id);
                if (course.getId() != null) {
                    toUpdate.add(course);
                } else {
                    toInsert.add(course);
                }
            }

            // ✅ 個別 update 実行に変更（安全な方式）
            if (!toUpdate.isEmpty()) {
                for (StudentCourse course : toUpdate) {
                    studentCourseRepository.update(course);
                }
            }

            if (!toInsert.isEmpty()) {
                studentCourseRepository.insertAll(toInsert);
            }
        }

        List<StudentCourse> updatedCourses = studentCourseRepository.findByStudentId(id);
        return StudentConverter.convertToStudentDetail(student, updatedCourses);
    }

    /**
     * 学生削除（論理削除）
     *
     * @param id 学生ID
     * @return 削除結果 DTO
     */
    @Transactional
    public DeleteStudentResultDTO deleteStudent(Long id) {
        studentRepository.deleteStudent(id);
        studentCourseRepository.deleteByStudentId(id);
        return new DeleteStudentResultDTO(id, true);
    }

    /**
     * 学生を ID で取得
     *
     * @param id 学生ID
     * @return 学生詳細情報
     */
    public StudentDetail getStudent(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new NoSuchElementException("Student not found with id: " + id);
        }
        List<StudentCourse> courses = studentCourseRepository.findByStudentId(id);
        return StudentConverter.convertToStudentDetail(student, courses);
    }

    /**
     * 全学生取得（N+1 回避のため全コース一括取得）
     *
     * @return 学生リスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = studentCourseRepository.findAll(); // 全コース取得

        List<StudentDetail> result = new ArrayList<>();
        for (Student student : students) {
            List<StudentCourse> studentCourses = new ArrayList<>();
            for (StudentCourse course : courses) {
                if (course.getStudentId().equals(student.getId())) {
                    studentCourses.add(course);
                }
            }
            result.add(StudentConverter.convertToStudentDetail(student, studentCourses));
        }
        return result;
    }

    /**
     * コース情報の簡易バリデーション
     *
     * @param courses 登録・更新対象のコースリスト
     */
    private void validateCourses(List<StudentCourse> courses) {
        if (courses == null) return;
        for (StudentCourse course : courses) {
            if (course.getCourseName() == null || course.getCourseName().isBlank()) {
                throw new ValidationException("Course name must not be blank");
            }
        }
    }
}