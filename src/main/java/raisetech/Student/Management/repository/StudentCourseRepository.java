package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 学生コース情報 Repository (MyBatis Mapper)
 *
 * Mapper XML と連携して使用
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 学生IDでコース取得
     *
     * @param id 学生ID
     * @return 学生コースリスト
     */
    List<StudentCourse> findByStudentId(Long id);

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
    void insertAll(List<StudentCourse> courses);

    /**
     * 単体コース更新
     *
     * <p>
     * XML に定義した SQL を呼び出す
     *
     * @param course 更新対象の StudentCourse
     */
    void update(StudentCourse course);

    /**
     * 学生IDで論理削除
     *
     * @param id 学生ID
     */
    void deleteByStudentId(Long id);
}