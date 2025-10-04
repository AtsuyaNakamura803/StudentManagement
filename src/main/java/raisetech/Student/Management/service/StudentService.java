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

/**
 * 学生情報サービス
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

    /** 学生登録（学生＋コース） */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.toStudent();
        studentRepository.insert(student); // Mapper 名に合わせ修正

        studentDetail.setId(student.getId());

        List<StudentCourse> courses = studentDetail.getCourses();
        if (courses != null && !courses.isEmpty()) {
            for (StudentCourse course : courses) {
                course.setStudentId(student.getId());
            }
            studentCourseRepository.insertAll(courses);
        }
        return studentDetail;
    }

    /** 学生取得 */
    public StudentDetail getStudent(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new NoSuchElementException("Student not found with id: " + id);
        }
        List<StudentCourse> courses = studentCourseRepository.findByStudentId(id);
        return StudentConverter.convertToStudentDetail(student, courses);
    }

    /** 学生更新（既存コースはupdate、新規コースはinsert） */
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
        studentRepository.update(student); // Mapper 名に合わせ修正

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
            if (!toUpdate.isEmpty()) studentCourseRepository.updateAll(toUpdate);
            if (!toInsert.isEmpty()) studentCourseRepository.insertAll(toInsert);
        }

        List<StudentCourse> updatedCourses = studentCourseRepository.findByStudentId(id);
        return StudentConverter.convertToStudentDetail(student, updatedCourses);
    }

    /** 学生削除（論理削除） */
    @Transactional
    public DeleteStudentResultDTO deleteStudent(Long id) {
        studentRepository.deleteById(id); // Mapper 名に合わせ修正
        studentCourseRepository.deleteByStudentId(id);
        return new DeleteStudentResultDTO(id, true);
    }

    /** 全学生取得（N+1回避） */
    public List<StudentDetail> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> courses = studentCourseRepository.findAll();
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
}