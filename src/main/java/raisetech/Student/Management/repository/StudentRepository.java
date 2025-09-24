package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.List;

/**
 * 学生情報とコース情報を操作する MyBatis Mapper
 */
@Mapper
public interface StudentRepository {

    List<StudentDetail> findAllStudentDetails();

    StudentDetail findById(@Param("id") int id);

    void saveStudent(StudentDetail studentDetail);

    void updateStudent(StudentDetail studentDetail);

    void deleteStudent(@Param("id") int id);
}