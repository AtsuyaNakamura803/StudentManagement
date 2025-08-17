package raisetech.Student.Management.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public int saveStudentDetail(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        int studentId = studentRepository.getMaxId() + 1;
        student.setId(studentId);
        studentRepository.insert(student);

        List<StudentsCourses> courses = studentDetail.getStudentsCourses();
        if (courses != null && !courses.isEmpty()) {
            LocalDate start = LocalDate.now();
            LocalDate end = start.plusMonths(1);
            for (StudentsCourses course : courses) {
                studentRepository.insertStudentCourse(studentId, course.getCourseName(), start, end);
            }
        }

        return studentId;
    }

    public List<StudentDetail> findAllStudentDetails() {
        List<Student> students = studentRepository.search();

        return students.stream().map(student -> {
            StudentDetail detail = new StudentDetail();
            detail.setStudent(student);

            List<StudentsCourses> courses = studentRepository.findCoursesByStudentId(student.getId());
            detail.setStudentsCourses(courses);

            if (courses != null && !courses.isEmpty()) {
                String courseNames = courses.stream()
                        .map(StudentsCourses::getCourseName)
                        .collect(Collectors.joining(","));
                detail.setCourseNames(courseNames);
            }

            return detail;
        }).collect(Collectors.toList());
    }
}