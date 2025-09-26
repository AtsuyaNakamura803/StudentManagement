package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 学生コース情報の MyBatis リポジトリ
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 全コース情報を取得する
     * @return StudentCourse のリスト
     */
    List<StudentCourse> findAll();

    /**
     * 学生IDでコース一覧を取得
     * @param studentId 学生ID
     * @return 指定学生の StudentCourse リスト
     */
    List<StudentCourse> findByStudentId(Long studentId);

    /**
     * コース一括登録
     * @param courses 登録する StudentCourse のリスト
     */
    void insertAll(List<StudentCourse> courses);

    /**
     * コース一括更新
     * @param courses 更新対象の StudentCourse のリスト
     */
    void updateAll(List<StudentCourse> courses);

    /**
     * 学生IDでコースを論理削除
     * @param studentId 学生ID
     */
    void deleteByStudentId(Long studentId);
}