package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

/**
 * 学生および履修コースに関するデータアクセス操作を提供するリポジトリインターフェースです。
 */
@Mapper
public interface StudentRepository {

    /**
     * すべての学生を取得します（論理削除されていないもの）。
     *
     * @return 学生リスト
     */
    List<Student> findAll();

    /**
     * 指定IDの学生を取得します。
     *
     * @param id 学生ID
     * @return 学生情報
     */
    Student findById(@Param("id") Long id);

    /**
     * 指定学生の履修コース情報を取得します。
     *
     * @param studentId 学生ID
     * @return コースリスト
     */
    List<StudentCourse> findCoursesByStudentId(@Param("studentId") Long studentId);

    /**
     * 学生情報を登録します。
     *
     * @param student 登録対象の学生
     */
    void insertStudent(Student student);

    /**
     * 学生情報を更新します。
     *
     * @param student 更新対象の学生
     */
    void updateStudent(Student student);

    /**
     * 学生コース情報を登録します。
     *
     * @param studentCourse 登録対象のコース
     */
    void insertStudentCourse(StudentCourse studentCourse);

    /**
     * 学生の履修コースを論理削除します。
     *
     * @param id 学生ID
     */
    void deleteStudentCourses(@Param("id") Long id);

    /**
     * 学生を論理削除します。
     *
     * @param id 学生ID
     */
    void deleteStudent(@Param("id") Long id);
}