package raisetech.Student.Management.data;

import java.util.Objects;

public class Student {

    private Long id;
    private String name;
    private String kanaName;
    private String nickname;
    private String email;
    private String area;
    private Integer age;
    private String sex;
    private String remark;

    public void validate() {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("名前は必須です");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("メールは必須です");
    }

    // Getter / Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
}