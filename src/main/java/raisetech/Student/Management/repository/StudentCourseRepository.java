package raisetech.Student.Management.repository;

import raisetech.Student.Management.data.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生のコース情報 CRUD を行う Repository。
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 全コースを取得（N+1 回避用）。
     * @return コースリスト
     */
    List<StudentCourse> findAllCourses();

    /**
     * 指定学生IDのコースを取得。
     * @param studentId 学生ID
     * @return コースリスト
     */
    List<StudentCourse> findCoursesByStudentId(@Param("studentId") Long studentId);

    void insertCourse(StudentCourse course);

    void updateCourse(StudentCourse course);

    void deleteCourse(@Param("id") Long id);
}