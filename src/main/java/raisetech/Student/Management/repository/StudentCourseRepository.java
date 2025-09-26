package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

@Mapper
public interface StudentCourseRepository {

    /**
     * 全件取得
     */
    List<StudentCourse> findAll();

    /**
     * 学生IDでコース一覧取得
     */
    List<StudentCourse> findByStudentId(@Param("studentId") Long studentId);

    /**
     * コース一括追加
     */
    void insertAll(@Param("list") List<StudentCourse> courses);

    /**
     * コース一括更新
     */
    void updateAll(@Param("list") List<StudentCourse> courses);

    /**
     * 学生IDでコース削除（論理削除）
     */
    void deleteByStudentId(@Param("studentId") Long studentId);
}