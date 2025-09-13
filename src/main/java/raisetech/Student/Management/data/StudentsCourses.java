package raisetech.Student.Management.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

    private long id;
    private long studentId;
    private String courseName;
    private LocalDate courseStartAt;
    private LocalDate courseEndAt;
}