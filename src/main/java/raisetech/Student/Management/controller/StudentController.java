package raisetech.Student.Management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<StudentDetail> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDetail getStudent(@PathVariable Long id) {
        return service.searchStudentById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentDetail studentDetail) {
        service.registerStudent(studentDetail);
        Long createdId = studentDetail.getStudent().getId();
        return ResponseEntity.created(URI.create("/student/" + createdId)).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok().build();
    }
}