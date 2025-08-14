package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList(){
        // 年齢が20代の人のみを抽出する
        return repository.search().stream()
                .filter(student -> student.getAge() >= 20 && student.getAge() < 30)
                .collect(Collectors.toList());
    }

    public List<StudentsCourses> searchStudentsCourseList(){
        // 「Javaコース」のみを抽出する
        return repository.searchStudentsCourses().stream()
                .filter(course -> "Javaコース".equals(course.getCourseName()))
                .collect(Collectors.toList());
    }
}
