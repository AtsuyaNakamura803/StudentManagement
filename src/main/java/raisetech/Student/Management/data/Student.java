package raisetech.Student.Management.data;

/**
 * 学生情報を表すドメイン
 */
public class Student {

    private int id;
    private String name;
    private String kanaName;
    private String nickname;
    private String email;
    private String area;
    private int age;
    private String sex;
    private String remark;
    private boolean deleted;

    /** ID を取得 */
    public int getId() {
        return id;
    }

    /** ID を設定 */
    public void setId(int id) {
        this.id = id;
    }

    /** 名前を取得 */
    public String getName() {
        return name;
    }

    /** 名前を設定 */
    public void setName(String name) {
        this.name = name;
    }

    /** カナ名を取得 */
    public String getKanaName() {
        return kanaName;
    }

    /** カナ名を設定 */
    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
    }

    /** ニックネームを取得 */
    public String getNickname() {
        return nickname;
    }

    /** ニックネームを設定 */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /** メールを取得 */
    public String getEmail() {
        return email;
    }

    /** メールを設定 */
    public void setEmail(String email) {
        this.email = email;
    }

    /** エリアを取得 */
    public String getArea() {
        return area;
    }

    /** エリアを設定 */
    public void setArea(String area) {
        this.area = area;
    }

    /** 年齢を取得 */
    public int getAge() {
        return age;
    }

    /** 年齢を設定 */
    public void setAge(int age) {
        this.age = age;
    }

    /** 性別を取得 */
    public String getSex() {
        return sex;
    }

    /** 性別を設定 */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /** 備考を取得 */
    public String getRemark() {
        return remark;
    }

    /** 備考を設定 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 削除フラグを取得 */
    public boolean isDeleted() {
        return deleted;
    }

    /** 削除フラグを設定 */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}