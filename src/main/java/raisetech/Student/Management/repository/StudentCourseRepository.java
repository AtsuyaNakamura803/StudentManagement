package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生コーステーブル操作用 Repository
 */
@Mapper
public interface StudentCourseRepository {

    List<StudentCourse> findAll();

    List<StudentCourse> findByStudentId(@Param("studentId") Long studentId);

    void insertAll(@Param("list") List<StudentCourse> courses);

    void deleteByStudentId(@Param("studentId") Long studentId);
}