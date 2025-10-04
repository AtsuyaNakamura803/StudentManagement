package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 学生コース情報 Repository (MyBatis Mapper)
 * <p>
 * Mapper XML と整合させたシンプルな定義。@Param は使用せず、
 * XML 側のプレースホルダと直接マッピングする。
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 学生IDでコース取得
     * @param id 学生ID
     * @return 学生コースリスト
     */
    List<StudentCourse> findByStudentId(Long id);

    /**
     * 全コース取得（論理削除除外）
     * @return 全コースリスト
     */
    List<StudentCourse> findAll();

    /**
     * コース一括挿入
     * @param courses 挿入対象コースリスト
     */
    void insertAll(List<StudentCourse> courses);

    /**
     * コース一括更新
     * @param courses 更新対象コースリスト
     */
    void updateAll(List<StudentCourse> courses);

    /**
     * 学生IDで論理削除
     * @param id 学生ID
     */
    void deleteByStudentId(Long id);
}