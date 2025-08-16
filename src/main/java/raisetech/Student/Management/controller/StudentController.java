package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/studentList")
    public String showStudentList(Model model) {
        List<StudentDetail> studentList = studentService.findAllStudentDetails();
        model.addAttribute("studentList", studentList);
        return "studentList";
    }

    @GetMapping("/newStudent")
    public String showForm(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail) {
        studentService.saveStudentDetail(studentDetail);
        return "redirect:/studentList";
    }
}