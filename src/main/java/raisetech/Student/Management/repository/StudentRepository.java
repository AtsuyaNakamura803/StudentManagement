package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.data.Student;
import java.util.List;

@Mapper
public interface StudentRepository {

    List<Student> findAll();

    Student findById(@Param("id") Long id);

    void insertStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(@Param("id") Long id);
}