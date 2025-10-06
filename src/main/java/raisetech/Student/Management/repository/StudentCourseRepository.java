package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 学生コース情報 Repository (MyBatis Mapper)
 * <p>
 * MyBatis XML と整合した Repository インターフェース。
 * 個別 update メソッドを用意して安全な更新を実現する。
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 学生IDでコース取得
     * @param id 学生ID
     * @return 学生コースリスト
     */
    List<StudentCourse> findByStudentId(@Param("id") Long id);

    /**
     * 全コース取得（論理削除除外）
     * @return 全コースリスト
     */
    List<StudentCourse> findAll();

    /**
     * コース一括挿入
     * @param courses 挿入対象コースリスト
     */
    void insertAll(@Param("list") List<StudentCourse> courses);

    /**
     * 単一コース更新
     * @param course 更新対象
     */
    void update(@Param("course") StudentCourse course);

    /**
     * 学生IDで論理削除
     * @param id 学生ID
     */
    void deleteByStudentId(@Param("id") Long id);
}