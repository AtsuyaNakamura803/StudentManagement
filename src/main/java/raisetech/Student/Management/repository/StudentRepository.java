package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.Student.Management.data.Student;

import java.util.List;

/**
 * 学生リポジトリ
 */
@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM students WHERE deleted = FALSE")
    List<Student> findAll();

    @Select("SELECT * FROM students WHERE id = #{id} AND deleted = FALSE")
    Student findById(Long id);

    void insertStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Long id);
}