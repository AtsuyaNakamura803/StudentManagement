package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生コースリポジトリ
 */
@Mapper
public interface StudentCourseRepository {

    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId} AND deleted = FALSE")
    List<StudentCourse> findByStudentId(Long studentId);

    @Select("SELECT * FROM students_courses WHERE deleted = FALSE")
    List<StudentCourse> findAll();

    void insertAll(List<StudentCourse> courses);

    void updateAll(List<StudentCourse> courses);

    void deleteByStudentId(Long studentId);
}