package raisetech.Student.Management.data;

import jakarta.validation.constraints.*;

/**
 * 学生情報を表すドメイン
 */
public class Student {

    private int id;

    @NotBlank(message = "名前は必須です")
    private String name;

    private String kanaName;

    private String nickname;

    @Email(message = "メール形式が不正です")
    private String email;

    private String area;

    @Min(value = 0, message = "年齢は0以上である必要があります")
    private int age;

    private String sex;

    private String remark;

    private boolean deleted;

    // getter/setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
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
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}