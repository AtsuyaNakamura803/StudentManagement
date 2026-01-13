package raisetech.Student.Management.data;

/**
 * 学生情報を表すデータクラス
 */
public class Student {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String sex; // genderではなくsexに統一
    private boolean deleted;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}