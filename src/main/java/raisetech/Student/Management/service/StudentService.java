package raisetech.Student.Management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 学生情報サービス
 * <p>
 * Student および StudentCourse の CRUD 操作を提供します。
 * getAllStudents では全コースをまとめて取得して N+1 問題を回避します。
 * </p>
 */
@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

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
     * @param studentDetail 登録情報
     * @return 登録後の StudentDetail（ID 反映済）
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
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
     * 学生取得
     *
     * @param id 学生ID
     * @return StudentDetail
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
     * 学生更新（学生＋コース）
     * <p>
     * 既存コースは updateAll、新規コースは insertAll で処理
     * </p>
     *
     * @param id            更新対象の学生ID
     * @param studentDetail 更新情報
     * @return 更新後の StudentDetail
     */
    @Transactional
    public StudentDetail updateStudent(Long id, StudentDetail studentDetail) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new NoSuchElementException("Student not found with id: " + id);
        }

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

            if (!toUpdate.isEmpty()) {
                studentCourseRepository.updateAll(toUpdate);
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
     * 全学生取得（N+1 回避＋SQL 発行件数ログ付き）
     *
     * @return 学生リスト
     */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = studentCourseRepository.findAll(); // 全コース取得

        logger.info("getAllStudents(): {} students loaded", students.size());
        logger.info("getAllStudents(): {} courses loaded", courses.size());

        List<StudentDetail> result = new ArrayList<>();
        for (Student student : students) {
            List<StudentCourse> studentCourses = new ArrayList<>();
            for (StudentCourse course : courses) {
                if (course.getStudentId().equals(student.getId())) {
                    studentCourses.add(course);
                }
            }
            result.add(StudentConverter.convertToStudentDetail(student, studentCourses));

            logger.debug("Student {} has {} courses", student.getId(), studentCourses.size());
        }

        return result;
    }
}