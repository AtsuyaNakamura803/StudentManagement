package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import java.util.List;

@Mapper
public interface StudentRepository {

    // ==========================
    // 受講生全件検索
    // ==========================
    @Select("SELECT * FROM students")
    List<Student> searchAllStudents();

    // ==========================
    // 受講生コース全件検索
    // ==========================
    @Select("SELECT * FROM students_courses")
    List<StudentsCourses> searchAllStudentsCourses();

    // ==========================
    // 受講生情報取得処理
    // ==========================
    @Select("SELECT * FROM students WHERE id = #{id}")
    Student searchStudent(@Param("id") int id);

    // ==========================
    // 受講生情報取得処理(コース)
    // ==========================
    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<StudentsCourses> searchStudentCourses(@Param("studentId") int studentId);

    // ==========================
    // 受講生登録処理
    // ==========================
    @Insert("""
      INSERT INTO students
      (name, kanaName, nickname, email, area, age, sex, remark, deleted)
      VALUES (#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)
      """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int registerStudent(Student student);

    // ==========================
    // 受講生登録処理(コース)
    // ==========================
    @Insert("""
      INSERT INTO students_courses
      (student_id, course_name, course_start_at, course_end_at)
      VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})
      """)
    int registerStudentsCourses(StudentsCourses studentsCourses);

    // ==========================
    // 受講生更新処理
    // ==========================
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

    // ==========================
    // 受講生更新処理(コース)
    // ==========================
    @Update("""
      UPDATE students_courses
      SET course_name = #{courseName},
          course_start_at = #{courseStartAt},
          course_end_at = #{courseEndAt}
      WHERE id = #{id}
      """)
    int updateStudentsCourses(StudentsCourses studentsCourses);

}