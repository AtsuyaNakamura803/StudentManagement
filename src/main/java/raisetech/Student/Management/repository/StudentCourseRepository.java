package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 学生コース情報 Repository (MyBatis Mapper)
 *
 * <p>
 * Mapper XML と整合させたシンプルな定義。
 * 単一パラメータには @Param を付与してマッピングを明示的にしています。
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 学生IDでコース取得
     *
     * @param id 学生ID
     * @return 学生コースリスト
     */
    List<StudentCourse> findByStudentId(@Param("id") Long id);

    /**
     * 全コース取得（論理削除除外）
     *
     * @return 全コースリスト
     */
    List<StudentCourse> findAll();

    /**
     * コース一括挿入
     *
     * @param courses 挿入対象コースリスト
     */
    void insertAll(@Param("list") List<StudentCourse> courses);

    /**
     * コース一括更新
     *
     * @param courses 更新対象コースリスト
     */
    void updateAll(@Param("list") List<StudentCourse> courses);

    /**
     * 学生IDで論理削除
     *
     * @param id 学生ID
     */
    void deleteByStudentId(@Param("id") Long id);
}