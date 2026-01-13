package raisetech.Student.Management.domain;

import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生情報 + コース情報をまとめたドメインクラス
 *
 * <p>REST API で返却・受け取りするデータ構造として利用。
 */
public class StudentDetail {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String gender; // Student の sex を変換
    private List<CourseDetail> courses = new ArrayList<>();

    /** デフォルトコンストラクタ */
    public StudentDetail() {}

    /**
     * Student と StudentCourse のリストから生成
     *
     * @param student Student データ
     * @param studentCourses コース情報リスト
     */
    public StudentDetail(Student student, List<StudentCourse> studentCourses) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.age = student.getAge();
        this.gender = student.getSex();

        if (studentCourses != null) {
            for (StudentCourse c : studentCourses) {
                this.courses.add(new CourseDetail(c));
            }
        }
    }

    // --- Getter / Setter ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public List<CourseDetail> getCourses() { return courses; }
    public void setCourses(List<CourseDetail> courses) { this.courses = courses; }

    // --- Student / StudentCourse 変換用メソッド ---

    /** StudentDetail → Student 変換 */
    public Student toStudent() {
        Student student = new Student();
        student.setId(this.id);
        student.setName(this.name);
        student.setEmail(this.email);
        student.setAge(this.age);
        student.setSex(this.gender);
        student.setDeleted(false);
        return student;
    }

    /** StudentDetail → List<StudentCourse> 変換 */
    public List<StudentCourse> toStudentCourses(Long studentId) {
        List<StudentCourse> studentCourses = new ArrayList<>();
        if (this.courses != null) {
            for (CourseDetail c : this.courses) {
                StudentCourse sc = new StudentCourse();
                sc.setStudentId(studentId);
                sc.setCourseName(c.getCourseName());
                sc.setCourseStartAt(c.getCourseStartAt().atStartOfDay());
                sc.setCourseEndAt(c.getCourseEndAt().atStartOfDay());
                sc.setDeleted(false);
                studentCourses.add(sc);
            }
        }
        return studentCourses;
    }

    // --- 内部クラス CourseDetail ---
    public static class CourseDetail {
        private String courseName;
        private LocalDate courseStartAt;
        private LocalDate courseEndAt;

        public CourseDetail() {}

        public CourseDetail(StudentCourse course) {
            this.courseName = course.getCourseName();
            if (course.getCourseStartAt() != null) {
                this.courseStartAt = course.getCourseStartAt().toLocalDate();
            }
            if (course.getCourseEndAt() != null) {
                this.courseEndAt = course.getCourseEndAt().toLocalDate();
            }
        }

        public String getCourseName() { return courseName; }
        public void setCourseName(String courseName) { this.courseName = courseName; }

        public LocalDate getCourseStartAt() { return courseStartAt; }
        public void setCourseStartAt(LocalDate courseStartAt) { this.courseStartAt = courseStartAt; }

        public LocalDate getCourseEndAt() { return courseEndAt; }
        public void setCourseEndAt(LocalDate courseEndAt) { this.courseEndAt = courseEndAt; }
    }
}