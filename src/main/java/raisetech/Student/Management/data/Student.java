package raisetech.Student.Management.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Student {

    private long id;
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

    /**
     * 入力値を検証します。
     * 必須項目が空の場合は IllegalArgumentException を投げます。
     */
    public void validate() {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("名前は必須です");
        if (kanaName == null || kanaName.isBlank()) throw new IllegalArgumentException("カナ名前は必須です");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("メールアドレスは必須です");
        if (age != null && age < 0) throw new IllegalArgumentException("年齢は0以上である必要があります");
        if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new IllegalArgumentException("メールアドレスの形式が不正です");
    }

    /**
     * コース文字列をリストに変換します。
     *
     * @param courseNamesStr コース名カンマ区切り
     */
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

    /**
     * コースリストを文字列に変換します。
     *
     * @return カンマ区切りコース名
     */
    public String getCourseNamesAsString() {
        return String.join(",", this.courseNames);
    }

    /**
     * 削除フラグを立てます。
     */
    public void markDeleted() { this.deleted = true; }

    /**
     * 削除済みか判定します。
     *
     * @return true: 削除済み
     */
    public boolean isDeleted() { return this.deleted; }

    /**
     * アクティブか判定します。
     *
     * @return true: 有効
     */
    public boolean isActive() { return !this.deleted; }

    // Getter / Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getKanaName() { return kanaName; }
    public void setKanaName(String kanaName) { this.kanaName = kanaName; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public List<String> getCourseNames() { return courseNames; }
    public void setCourseNames(List<String> courseNames) { this.courseNames = courseNames; }

    public void setDeleted(boolean deleted) { this.deleted = deleted; }

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