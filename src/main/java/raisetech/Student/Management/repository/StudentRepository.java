package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.List;
import java.time.LocalDate;

@Mapper
public interface StudentRepository {

    // 論理削除されていない学生のみ取得
    @Select("SELECT * FROM students WHERE deleted = FALSE")
    List<Student> search();

    // 全学生取得（削除済み含む）
    @Select("SELECT * FROM students")
    List<Student> findAll();

    @Select("SELECT COALESCE(MAX(id), 0) FROM students")
    int getMaxId();

    @Insert("""
        INSERT INTO students (name, kanaName, nickname, email, area, age, sex, remark, deleted)
        VALUES (#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, #{deleted})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Student student);

    @Insert("""
        INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at)
        VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})
    """)
    void insertStudentCourse(@Param("studentId") int studentId,
                             @Param("courseName") String courseName,
                             @Param("courseStartAt") LocalDate courseStartAt,
                             @Param("courseEndAt") LocalDate courseEndAt);

    @Select("""
        SELECT student_id, course_name, course_start_at, course_end_at
        FROM students_courses
        WHERE student_id = #{studentId}
    """)
    List<StudentsCourses> findCoursesByStudentId(@Param("studentId") int studentId);

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
    void updateStudent(Student student);

    @Delete("""
        DELETE FROM students_courses
        WHERE student_id = #{studentId}
    """)
    void deleteCoursesByStudentId(@Param("studentId") int studentId);

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student findById(@Param("id") int id);

    // 論理削除されていないレコードでメールを検索（重複チェック用）
    @Select("SELECT * FROM students WHERE email = #{email} AND deleted = FALSE")
    Student findByEmailAndNotDeleted(@Param("email") String email);

    // 削除済み含めてメールで検索（復活処理用）
    @Select("SELECT * FROM students WHERE email = #{email}")
    List<Student> findByEmailAll(@Param("email") String email);
}