package raisetech.Student.Management.controller;

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

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public List<StudentDetail> listStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable("id") Long id) {
        return studentService.searchStudentById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentDetail studentDetail) {
        studentService.registerStudent(studentDetail);

        Long createdId = studentDetail.getStudent().getId();
        if (createdId == null || createdId <= 0) {
            throw new IllegalStateException("生成されたIDが不正です");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody StudentDetail studentDetail) {
        studentService.updateStudent(studentDetail);
        return ResponseEntity.ok().build();
    }
}