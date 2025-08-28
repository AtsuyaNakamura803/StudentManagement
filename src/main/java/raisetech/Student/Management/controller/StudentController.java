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
    public String studentList(@RequestParam(name = "showAll", defaultValue = "false") boolean showAll, Model model) {
        if (showAll) {
            model.addAttribute("studentDetails", studentService.findAllStudentDetails());
        } else {
            model.addAttribute("studentDetails", studentService.findAllActiveStudentDetails());
        }
        model.addAttribute("showAll", showAll);
        return "studentList";
    }

    // ==========================
    // 新規登録画面を表示
    // ==========================
    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        model.addAttribute("showCancel", false); // 新規登録はキャンセル非表示
        return "registerStudent";
    }

    // ==========================
    // 新規登録 or 編集後の保存
    // ==========================
    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, Model model) {
        try {
            studentService.saveOrUpdateStudentDetail(studentDetail);
            return "redirect:/studentList";
        } catch (IllegalArgumentException e) {
            model.addAttribute("studentDetail", studentDetail);
            model.addAttribute("errorMessage", e.getMessage());
            // 編集画面か新規画面かでキャンセル表示を切り替え
            model.addAttribute("showCancel", studentDetail.getStudent().getId() != null);
            return "registerStudent";
        }
    }

    // ==========================
    // 編集画面を表示（無効なら一覧へリダイレクト）
    // ==========================
    @GetMapping("/editStudent/{id}")
    public String editStudent(@PathVariable Integer id, Model model) {
        return studentService.findStudentDetailById(id)
                .map(studentDetail -> {
                    model.addAttribute("studentDetail", studentDetail);
                    model.addAttribute("showCancel", true); // 編集画面はキャンセル表示
                    return "registerStudent";
                })
                .orElse("redirect:/studentList?error=notfound"); // 無効や存在しない場合は一覧へ
    }

    // ==========================
    // 復活処理
    // ==========================
    @PostMapping("/restoreStudent/{id}")
    public String restoreStudent(@PathVariable Integer id) {
        studentService.restoreStudent(id);
        return "redirect:/studentList?showAll=true";
    }
}