package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * MyBatis Mapper: 学生およびコースに関するデータアクセスを定義
 */
@Mapper
public interface StudentRepository {

    List<Student> searchAllStudents();

    List<StudentCourse> searchAllStudentCourseList();

    Student searchStudent(@Param("id") Long id);

    List<StudentCourse> searchStudentCourse(@Param("studentId") Long studentId);

    int registerStudent(Student student);

    int registerStudentCourse(StudentCourse studentCourse);

    int updateStudent(Student student);

    int updateStudentCourse(StudentCourse studentCourse);

    // 論理削除
    int deleteStudent(@Param("id") Long id);

    int deleteStudentCourses(@Param("studentId") Long studentId);

    // 将来用：特定コース削除
    int deleteStudentCourse(@Param("courseId") Long courseId);

    StudentCourse searchStudentCourseById(@Param("courseId") Long courseId);
}