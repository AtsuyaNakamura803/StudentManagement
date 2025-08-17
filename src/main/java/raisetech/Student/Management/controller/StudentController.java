package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;
import raisetech.Student.Management.data.StudentsCourses;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ==========================
    // 受講生一覧画面
    // ==========================
    @GetMapping("/studentList")
    public String studentList(Model model) {
        model.addAttribute("studentDetails", studentService.findAllStudentDetails());
        return "studentList";
    }

    // ==========================
    // 新規登録画面を表示
    // ==========================
    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    // ==========================
    // 新規登録 or 編集後の保存
    // ==========================
    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, Model model) {
        try {
            // Student と StudentsCourses をまとめて保存
            studentService.saveOrUpdateStudentDetail(studentDetail);
            return "redirect:/studentList";
        } catch (IllegalArgumentException e) {
            // 重複メールなどのエラー
            model.addAttribute("studentDetail", studentDetail);
            model.addAttribute("error", e.getMessage());
            return "registerStudent";
        }
    }

    // ==========================
    // 名前クリックで編集画面に飛ぶ
    // ==========================
    @GetMapping("/editStudent/{id}")
    public String editStudent(@PathVariable Integer id, Model model) {
        StudentDetail studentDetail = studentService.findStudentDetailById(id)
                .orElse(new StudentDetail());

        // 編集用に courseNames を再構築
        if (studentDetail.getStudentsCourses() != null && !studentDetail.getStudentsCourses().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
                if (sb.length() > 0) sb.append(",");
                sb.append(sc.getCourseName());
            }
            studentDetail.setCourseNames(sb.toString());
        }

        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }
}