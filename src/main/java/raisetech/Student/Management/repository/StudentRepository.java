package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;

/**
 * 受講生テーブルと受講生コース情報テーブルにアクセスする Repository です。
 */
@Mapper
public interface StudentRepository {

    /**
     * 受講生一覧検索です。
     *
     * @return 全受講生のリスト
     */
    @Select("SELECT * FROM students")
    List<Student> searchAllStudents();

    /**
     * 受講生コース情報の全件検索です。
     *
     * @return 全受講生コース情報のリスト
     */
    @Select("SELECT * FROM students_courses")
    List<StudentsCourses> searchAllStudentsCourses();

    /**
     * 受講生検索です。
     * ID に紐づく受講生情報を取得します。
     *
     * @param id 受講生ID
     * @return 該当受講生
     */
    @Select("SELECT * FROM students WHERE id = #{id}")
    Student searchStudent(@Param("id") Long id);

    /**
     * 受講生コース情報検索です。
     * 指定した受講生IDに紐づくコース情報を取得します。
     *
     * @param studentId 受講生ID
     * @return 該当受講生コース情報リスト
     */
    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<StudentsCourses> searchStudentCourses(@Param("studentId") Long studentId);

    /**
     * 受講生登録です。
     * ID は自動採番されます。
     *
     * @param student 登録対象の受講生
     * @return 登録件数
     */
    @Insert("""
      INSERT INTO students
      (name, kanaName, nickname, email, area, age, sex, remark, deleted)
      VALUES (#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)
      """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int registerStudent(Student student);

    /**
     * 受講生コース登録です。
     *
     * @param studentsCourses 登録対象の受講生コース
     * @return 登録件数
     */
    @Insert("""
      INSERT INTO students_courses
      (student_id, course_name, course_start_at, course_end_at)
      VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})
      """)
    int registerStudentsCourses(StudentsCourses studentsCourses);

    /**
     * 受講生更新です。
     *
     * @param student 更新対象の受講生
     * @return 更新件数
     */
    @Update("""
      UPDATE students
      SET name = #{name},
          kanaName = #{kanaName},
          nickname = #{nickname},
          email = #{email},
          area = #{area},
          age = #{age},
          sex = #{sex},
          remark = #{remark},
          deleted = #{deleted}
      WHERE id = #{id}
      """)
    int updateStudent(Student student);

    /**
     * 受講生コース更新です。
     *
     * @param studentsCourses 更新対象の受講生コース
     * @return 更新件数
     */
    @Update("""
      UPDATE students_courses
      SET course_name = #{courseName},
          course_start_at = #{courseStartAt},
          course_end_at = #{courseEndAt}
      WHERE id = #{id}
      """)
    int updateStudentsCourses(StudentsCourses studentsCourses);
}