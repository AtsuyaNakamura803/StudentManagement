package raisetech.Student.Management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<StudentDetail>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetail> getStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.searchStudentById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentDetail studentDetail) {
        studentService.registerStudent(studentDetail);
        Long createdId = studentDetail.getStudent().getId();

        if (createdId == null || createdId <= 0) {
            logger.error("登録後のIDが不正です: {}", studentDetail);
            throw new IllegalStateException("登録後のIDが不正です");
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/student/{id}")
                .buildAndExpand(createdId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody StudentDetail studentDetail) {
        studentService.updateStudent(studentDetail);
        return ResponseEntity.noContent().build();
    }
}