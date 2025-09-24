package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.List;

/**
 * 学生情報とコース情報を操作する MyBatis Mapper インターフェース。
 */
@Mapper
public interface StudentRepository {

    /**
     * すべての学生詳細情報を取得する。
     *
     * @return 学生詳細情報のリスト
     */
    List<StudentDetail> findAllStudentDetails();

    /**
     * 指定IDの学生詳細情報を取得する。
     *
     * @param id 学生ID
     * @return 学生詳細情報
     */
    StudentDetail findById(@Param("id") int id);

    /**
     * IDリストに対応する学生詳細情報を取得する。
     *
     * @param ids 学生IDのリスト
     * @return 学生詳細情報リスト
     */
    List<StudentDetail> findByIdList(@Param("ids") List<Integer> ids);

    /**
     * 学生情報を登録する。
     *
     * @param studentDetail 学生詳細情報
     */
    void save(StudentDetail studentDetail);

    /**
     * 学生情報を更新する。
     *
     * @param studentDetail 学生詳細情報
     */
    void update(StudentDetail studentDetail);

    /**
     * 学生詳細情報を更新する。
     *
     * @param studentDetail 学生詳細情報
     */
    void updateStudentInfo(StudentDetail studentDetail);

    /**
     * 学生のコース情報を削除する。
     *
     * @param studentId 学生ID
     */
    void deleteStudentCourses(@Param("studentId") int studentId);

    /**
     * 学生コース情報を登録する。
     *
     * @param course 学生コース情報
     */
    void insertStudentCourse(@Param("course") raisetech.Student.Management.data.StudentCourse course);

    /**
     * 学生IDに紐づくコース情報を削除する。
     *
     * @param studentId 学生ID
     */
    void deleteCoursesByStudentId(@Param("studentId") int studentId);
}