package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import raisetech.Student.Management.data.Student;
import java.util.List;

/**
 * 学生情報 Repository (MyBatis Mapper)
 * <p>
 * MyBatis を使用した CRUD 操作を提供します。
 * XML マッパーで定義された SQL ステートメントと連携します。
 * </p>
 */
@Mapper
public interface StudentRepository {

    /**
     * 全学生取得（論理削除済みを除外）
     *
     * @return 学生リスト
     */
    List<Student> findAll();

    /**
     * ID 指定で学生取得
     *
     * @param id 学生ID
     * @return 指定IDの Student オブジェクト。存在しない場合は null。
     */
    Student findById(Long id);

    /**
     * 学生登録
     *
     * @param student 登録対象の Student
     */
    void insertStudent(Student student);

    /**
     * 学生情報更新
     *
     * @param student 更新対象の Student
     */
    void updateStudent(Student student);

    /**
     * 学生論理削除
     *
     * @param id 削除対象の学生ID
     */
    void deleteStudent(Long id);
}