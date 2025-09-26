package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;
import java.util.List;

/**
 * 学生情報リポジトリ（MyBatis Mapper インターフェース）
 */
@Mapper
public interface StudentRepository {

    /**
     * 学生登録
     * @param student 登録する学生
     */
    void insertStudent(Student student);

    /**
     * 学生更新
     * @param student 更新対象学生
     */
    void updateStudent(Student student);

    /**
     * 学生取得（ID）
     * @param id 学生ID
     * @return Student
     */
    Student findById(@Param("id") Long id);

    /**
     * 論理削除
     * @param id 学生ID
     */
    void deleteStudent(@Param("id") Long id);

    /**
     * 全学生取得（deleted=false のみ）
     * @return 学生リスト
     */
    List<Student> findAll();
}