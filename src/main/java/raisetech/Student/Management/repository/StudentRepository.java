package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;
import java.util.List;

/**
 * 学生情報 Repository (MyBatis Mapper)
 *
 * <p>
 * 単一引数には @Param を付与して、XML 側で #{id} が安全にバインドされるようにしています。
 */
@Mapper
public interface StudentRepository {

    /**
     * 全学生取得（論理削除除外）
     *
     * @return 学生リスト
     */
    List<Student> findAll();

    /**
     * ID指定で学生取得
     *
     * @param id 学生ID
     * @return 学生情報
     */
    Student findById(@Param("id") Long id);

    /**
     * 学生登録
     *
     * @param student 登録対象学生
     */
    void insertStudent(Student student);

    /**
     * 学生更新
     *
     * @param student 更新対象学生
     */
    void updateStudent(Student student);

    /**
     * 学生論理削除
     *
     * @param id 学生ID
     */
    void deleteStudent(@Param("id") Long id);
}