package raisetech.Student.Management.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentsCourses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class StudentDetail {

    private Student student;
    private List<StudentsCourses> studentsCourses;

    private String courseNames;

    public List<StudentsCourses> getStudentsCourses() {
        if (studentsCourses == null) {
            studentsCourses = new ArrayList<>();
            if (courseNames != null && !courseNames.isEmpty()) {
                String[] arr = courseNames.split(",");
                for (String name : arr) {
                    StudentsCourses sc = new StudentsCourses();
                    sc.setCourseName(name.trim());
                    studentsCourses.add(sc);
                }
            }
        }
        return studentsCourses;
    }

    public void setStudentsCourses(List<StudentsCourses> studentsCourses) {
        this.studentsCourses = studentsCourses;
    }
}