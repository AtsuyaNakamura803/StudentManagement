package raisetech.Student.Management.data;

public class Student {

    private Long id; // Long に統一
    private String name;
    private String email;

    public void validate() {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("名前は必須です");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("メールは必須です");
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}