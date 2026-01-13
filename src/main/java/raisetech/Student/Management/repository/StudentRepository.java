package raisetech.Student.Management.repository;

import raisetech.Student.Management.data.Student;

import java.util.List;
import java.util.Optional;

/**
 * 学生情報 Repository インターフェース（XML Mapper方式）
 *
 * <p>MyBatis の XML マッパーを使用して DB アクセスを行う。</p>
 */
public interface StudentRepository {

    /**
     * 全学生取得（論理削除されていないもの）
     *
     * @return 学生リスト
     */
    List<Student> findAll();

    /**
     * ID で学生取得
     *
     * @param id 学生ID
     * @return 存在する場合は Optional で返却
     */
    Optional<Student> findById(Long id);

    /**
     * 学生登録
     *
     * @param student 登録する学生
     */
    void insertStudent(Student student);

    /**
     * 学生更新
     *
     * @param student 更新対象の学生
     */
    void updateStudent(Student student);

    /**
     * 学生存在確認
     *
     * @param id 学生ID
     * @return 存在する場合 true
     */
    boolean existsById(Long id);

    /**
     * 学生削除（論理削除）
     *
     * @param id 削除対象の学生ID
     */
    void deleteStudent(Long id);
}