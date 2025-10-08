package raisetech.Student.Management.repository;

import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生コース情報 Repository インターフェース（XML Mapper方式）
 *
 * <p>MyBatis の XML マッパーを使用して DB アクセスを行う。</p>
 */
public interface StudentCourseRepository {

    /**
     * 学生IDでコース取得
     *
     * @param studentId 学生ID
     * @return コースリスト
     */
    List<StudentCourse> findByStudentId(Long studentId);

    /**
     * 全コース取得
     *
     * @return 全コースリスト
     */
    List<StudentCourse> findAll();

    /**
     * コース一括登録
     *
     * @param courses 登録対象のコースリスト
     */
    void insertAll(List<StudentCourse> courses);

    /**
     * コース更新
     *
     * @param course 更新対象のコース
     */
    void update(StudentCourse course);

    /**
     * 学生IDでコース削除
     *
     * @param studentId 削除対象の学生ID
     */
    void deleteByStudentId(Long studentId);
}