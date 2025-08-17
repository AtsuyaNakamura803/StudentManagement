package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;
import java.time.LocalDate;

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM students")
    List<Student> search();

    @Select("SELECT COALESCE(MAX(id), 0) FROM students")
    int getMaxId();

    @Insert("""
            INSERT INTO students (id,name, kanaName, nickname, email, area, age, sex, remark)
            VALUES ( #{id},#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark})
            """)
    void insert(Student student);

    @Select("SELECT student_id, course_name, start_date, end_date FROM students_courses")
    List<StudentsCourses> courseSearch();

    @Insert("""
                INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at)
                VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})
            """)
    void insertStudentCourse(@Param("studentId") int studentId,
                             @Param("courseName") String courseName,
                             @Param("courseStartAt") LocalDate courseStartAt,
                             @Param("courseEndAt") LocalDate courseEndAt);

    @Select("""
                SELECT student_id, course_name, course_start_at, course_end_at, start_date, end_date
                FROM students_courses
                WHERE student_id = #{studentId}
            """)
    List<StudentsCourses> findCoursesByStudentId(@Param("studentId") int studentId);
}