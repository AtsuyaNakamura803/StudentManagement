package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

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
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(new raisetech.Student.Management.data.Student()); // null回避
        model.addAttribute("studentDetail", studentDetail);
        model.addAttribute("showCancel", false); // 新規登録はキャンセル非表示
        return "registerStudent";
    }

    // ==========================
    // 新規登録 or 編集後の保存
    // ==========================
    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, Model model) {
        try {
            // 編集画面でキャンセルにチェックが入っていれば論理削除
            if (studentDetail.isCancel() && studentDetail.getStudent().getId() != null) {
                studentService.deleteStudent(studentDetail.getStudent().getId());
            } else {
                studentService.saveOrUpdateStudentDetail(studentDetail);
            }
            return "redirect:/studentList";
        } catch (IllegalArgumentException e) {
            model.addAttribute("studentDetail", studentDetail);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("showCancel", studentDetail.getStudent().getId() != null);
            return "registerStudent";
        }
    }

    // ==========================
    // 編集画面を表示
    // ==========================
    @GetMapping("/editStudent/{id}")
    public String editStudent(@PathVariable Integer id, Model model) {
        StudentDetail studentDetail = studentService.findStudentDetailById(id)
                .orElse(new StudentDetail());
        if (studentDetail.getStudent() == null) {
            studentDetail.setStudent(new raisetech.Student.Management.data.Student());
        }
        model.addAttribute("studentDetail", studentDetail);
        model.addAttribute("showCancel", true); // 編集画面はキャンセル表示
        return "registerStudent";
    }

    // ==========================
    // 論理削除済みを復活
    // ==========================
    @PostMapping("/restoreStudent/{id}")
    public String restoreStudent(@PathVariable Integer id) {
        studentService.restoreStudent(id);
        return "redirect:/studentList";
    }
}