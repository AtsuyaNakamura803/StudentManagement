package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生コース用リポジトリ（MyBatis Mapper）
 * 学生に紐づくコース情報の CRUD を提供
 */
@Mapper
public interface StudentCourseRepository {

    /**
     * 指定学生IDに紐づくコース一覧を取得
     * @param studentId 学生ID
     * @return StudentCourse のリスト
     */
    List<StudentCourse> findByStudentId(@Param("studentId") Long studentId);

    /**
     * 全コースを取得
     * N+1 回避用
     * @return StudentCourse のリスト
     */
    List<StudentCourse> findAll();

    /**
     * 学生IDに紐づくコースを論理削除
     * @param studentId 学生ID
     */
    void deleteByStudentId(@Param("studentId") Long studentId);

    /**
     * コースリストを一括登録
     * @param courses 登録対象の StudentCourse リスト
     */
    void insertAll(@Param("courses") List<StudentCourse> courses);

    /**
     * コースリストを一括更新（必要な場合）
     * @param courses 更新対象の StudentCourse リスト
     */
    void updateAll(@Param("courses") List<StudentCourse> courses);
}