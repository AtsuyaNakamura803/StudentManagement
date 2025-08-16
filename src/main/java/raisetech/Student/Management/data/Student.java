package raisetech.Student.Management.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Student {

    private Integer id;
    private String name;
    private String kanaName;
    private String nickname;
    private String email;
    private String area;
    private Integer age;
    private String sex;
    private String remark;

    private boolean deleted;

    private List<String> courseNames = new ArrayList<>();

    public void setCourseNamesFromString(String courseNamesStr) {
        if (courseNamesStr != null && !courseNamesStr.trim().isEmpty()) {
            this.courseNames = Arrays.stream(courseNamesStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } else {
            this.courseNames = new ArrayList<>();
        }
    }

    public String getCourseNamesAsString() {
        return String.join(",", this.courseNames);
    }

    public void markDeleted() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kanaName='" + kanaName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", area='" + area + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                ", deleted=" + deleted +
                ", courseNames=" + courseNames +
                '}';
    }
}