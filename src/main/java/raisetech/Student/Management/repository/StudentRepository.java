package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;

/**
 * MyBatis Mapper: 学生およびコースに関するデータアクセスを定義
 */
@Mapper
public interface StudentRepository {

    @Select("SELECT id, name, age FROM students")
    List<Student> searchAllStudents();

    @Select("SELECT id, student_id, course_name FROM students_courses")
    List<StudentsCourses> searchAllStudentsCourses();

    @Select("SELECT id, name, age FROM students WHERE id = #{id}")
    Student searchStudent(@Param("id") Long id);

    @Select("SELECT id, student_id, course_name FROM students_courses WHERE student_id = #{studentId}")
    List<StudentsCourses> searchStudentCourses(@Param("studentId") Long studentId);

    @Insert("INSERT INTO students(name, age) VALUES(#{name}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int registerStudent(Student student);

    @Insert("INSERT INTO students_courses(student_id, course_name) VALUES(#{studentId}, #{courseName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int registerStudentsCourses(StudentsCourses studentsCourses);

    @Update("UPDATE students SET name=#{name}, age=#{age} WHERE id=#{id}")
    int updateStudent(Student student);

    @Update("UPDATE students_courses SET course_name=#{courseName} WHERE id=#{id}")
    int updateStudentsCourses(StudentsCourses studentsCourses);
}