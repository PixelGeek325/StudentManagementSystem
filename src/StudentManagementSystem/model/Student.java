package StudentManagementSystem.model;

/**
 * 创建student类用于表示学生实体，以下所有属性对应学生的基本信息
 * 这些属性与数据库中的字段一一对应，用private限制外部直接访问
 * 保证数据的安全性
 */
public class Student {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String grade;
    private String email;

    // 无参构造方法
    public Student(){

    }
    // 有参构造方法，快速初始化学生信息，id作为主键，通常由数据库自动生成，为此，参数不包含id属性
    public Student(String name, int age, String gender, String grade, String email) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
        this.email = email;
    }
    // 编写Getter和Setter方法，为了方便访问和修改属性

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // 重写object类方法，方便打印学生信息
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", grade='" + grade + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getGrade() {
        return grade;
    }

    public String getEmail() {
        return email;
    }
}
/**
 * 以上Student类是典型的JavaBean（实体类），用于在程序中表示和传递学生信息数据
 * 常和数据库的表（如SQL中的students）映射，实现数据持久化
 */