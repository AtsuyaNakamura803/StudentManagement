package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生コース Repository (MyBatis Mapper)
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 学生IDでコース取得
     * @param studentId 学生ID
     * @return コースリスト
     */
    List<StudentCourse> findByStudentId(@Param("studentId") Long studentId);

    /**
     * 全コース取得（N+1回避用）
     * @return 全コースリスト
     */
    List<StudentCourse> findAll();

    /**
     * コース一括登録
     * @param courses 登録対象コース
     */
    void insertAll(@Param("courses") List<StudentCourse> courses);

    /**
     * コース一括更新
     * @param courses 更新対象コース
     */
    void updateAll(@Param("courses") List<StudentCourse> courses);

    /**
     * 学生IDでコース論理削除
     * @param studentId 学生ID
     */
    void deleteByStudentId(@Param("studentId") Long studentId);
}