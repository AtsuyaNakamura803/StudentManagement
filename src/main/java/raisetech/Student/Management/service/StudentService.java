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

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 学生情報サービス
 * <p>
 * StudentRepository / StudentCourseRepository を用いて、学生情報の
 * 登録・取得・更新・削除を行います。
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
     * 学生登録（学生 + コース）
     *
     * @param studentDetail 登録情報
     * @return 登録後の StudentDetail（ID 反映済）
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        // Student を DB 保存
        Student student = studentDetail.toStudent();
        studentRepository.insertStudent(student);

        // 生成された ID を StudentDetail に反映
        studentDetail.setId(student.getId());

        // StudentCourse を DB 保存
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
     * 学生取得
     *
     * @param id 学生ID
     * @return StudentDetail
     */
    public StudentDetail getStudent(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new NoSuchElementException("指定IDの学生が存在しません: " + id);
        }
        List<StudentCourse> courses = studentCourseRepository.findByStudentId(id);
        return StudentConverter.convertToStudentDetail(student, courses);
    }

    /**
     * 全学生取得（論理削除済は除外）
     *
     * @return StudentDetail のリスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = studentCourseRepository.findAll(); // 全コース取得
        return StudentConverter.convertToStudentDetails(students, courses);
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
     * 学生更新
     *
     * @param id            学生ID
     * @param studentDetail 更新内容
     * @return 更新後 StudentDetail
     */
    @Transactional
    public StudentDetail updateStudent(Long id, StudentDetail studentDetail) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new NoSuchElementException("指定IDの学生が存在しません: " + id);
        }

        // Student に更新内容を反映
        student.setName(studentDetail.getName());
        student.setEmail(studentDetail.getEmail());
        student.setAge(studentDetail.getAge());
        student.setSex(studentDetail.getGender()); // Student.sex に反映

        studentRepository.updateStudent(student);

        // コース更新
        List<StudentCourse> courses = studentDetail.getCourses();
        if (courses != null && !courses.isEmpty()) {
            for (StudentCourse course : courses) {
                course.setStudentId(student.getId());
            }
            studentCourseRepository.updateAll(courses);
        }

        // 更新後の StudentDetail を返却
        return getStudent(id);
    }
}