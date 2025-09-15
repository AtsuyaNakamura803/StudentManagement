package raisetech.Student.Management.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 受講生の基本情報を保持するクラスです。
 */
public class Student {

    private Long id;

    @NotBlank(message="名前は必須です")
    private String name;

    @NotNull(message="年齢は必須です")
    @Min(value=0, message="年齢は0以上でなければなりません")
    private Integer age;
    private Boolean isDeleted; // 論理削除フラグ

    public Student() {}

    public Student(Long id, String name, Integer age, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(name, student.name) &&
                Objects.equals(age, student.age) &&
                Objects.equals(isDeleted, student.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, isDeleted);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isDeleted=" + isDeleted +
                '}';
    }
}