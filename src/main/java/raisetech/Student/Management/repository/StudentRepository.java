package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;

import java.util.List;

/**
 * 学生テーブル操作用 Repository
 */
@Mapper
public interface StudentRepository {

    List<Student> findAll();

    Student findById(@Param("id") Long id);

    void insertStudent(@Param("student") Student student);

    void updateStudent(@Param("student") Student student);

    void deleteStudent(@Param("id") Long id);
}