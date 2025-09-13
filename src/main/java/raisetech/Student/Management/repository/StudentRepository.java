package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;

@Mapper
public interface StudentRepository {

    List<Student> searchAllStudents();
    List<StudentsCourses> searchAllStudentsCourses();
    Student searchStudent(@Param("id") Long id);
    List<StudentsCourses> searchStudentCourses(@Param("studentId") Long studentId);
    int registerStudent(Student student);
    int registerStudentsCourses(StudentsCourses studentsCourses);
    int updateStudent(Student student);
    int updateStudentsCourses(StudentsCourses studentsCourses);
}