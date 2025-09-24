package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.controller.converter.StudentConverter;

import java.util.List;

/**
 * 学生情報およびコース情報のビジネスロジックを提供するサービスクラス。
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * すべての学生詳細情報を取得する。
     * @return 学生詳細情報リスト
     */
    public List<StudentDetail> getAllStudents() {
        return studentRepository.findAllStudentDetails();
    }

    /**
     * 指定IDの学生詳細情報を取得する。
     * @param id 学生ID
     * @return 学生詳細情報
     */
    public StudentDetail getStudentById(int id) {
        return studentRepository.findById(id);
    }

    /**
     * 学生情報を登録する。
     * @param studentDetail 学生詳細情報
     */
    public void saveStudent(StudentDetail studentDetail) {
        studentRepository.save(studentDetail);

        // コース情報を保存
        if (studentDetail.getCourses() != null) {
            for (StudentCourse course : studentDetail.getCourses()) {
                course.setStudentId(studentDetail.getStudent().getId());
                studentRepository.insertStudentCourse(course);
            }
        }
    }

    /**
     * 学生情報を更新する。
     * @param studentDetail 学生詳細情報
     */
    public void updateStudent(StudentDetail studentDetail) {
        studentRepository.updateStudentInfo(studentDetail);

        // コース情報は一旦削除してから再登録
        studentRepository.deleteCoursesByStudentId(studentDetail.getStudent().getId());
        if (studentDetail.getCourses() != null) {
            for (StudentCourse course : studentDetail.getCourses()) {
                course.setStudentId(studentDetail.getStudent().getId());
                studentRepository.insertStudentCourse(course);
            }
        }
    }
}