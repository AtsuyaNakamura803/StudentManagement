package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;

import java.util.List;
import java.util.Optional;

/**
 * 学生情報 Repository
 */
@Mapper
public interface StudentRepository {

    /**
     * 全学生取得
     * @return 学生リスト
     */
    List<Student> findAll();

    /**
     * 指定IDの学生取得
     * @param id 学生ID
     * @return 学生情報(Optional)
     */
    Optional<Student> findById(@Param("id") Long id);

    /**
     * 学生登録
     * @param student 登録する学生
     */
    void insertStudent(Student student);

    /**
     * 学生更新
     * @param student 更新対象の学生
     */
    void updateStudent(Student student);

    /**
     * 学生論理削除
     * @param id 削除対象の学生ID
     */
    void deleteStudent(@Param("id") Long id);
}