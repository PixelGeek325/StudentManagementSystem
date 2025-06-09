package StudentManagementSystem.dao;

import StudentManagementSystem.model.DatabaseConnection;
import StudentManagementSystem.model.Student;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 创建StudentDao类目的是实现学生信息和数据库的交互操作
 * 总结：
 * 1. 封装性：将数据库的操作封装为独立的Dao类，隔离业务逻辑和数据访问层，符合分层架构设计原则。
 * 2. 预编译语句（PreparedStatement）：除了getAllStudents()方法之外，其他方法均使用了预编译语句
 *      目的是防止SQL注入并提升性能（数据可缓存执行计划）
 * 3. 资源自动管理：所有数据库的链接（Connection）、语句（Statement）和结果集（ResultSet）
 *      均通过try-with-resources语句进行自动管理，避免手动关闭导致的泄露问题
 * 4. 异常处理：通过throw SQLException抛出异常，保证方法简洁性，同时强制调用者
 *      处理数据库操作中的异常
 */
public class StudentDao {
    // 添加学生
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students(name,age,gender,grade,email)" +
                "VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getGender());
            stmt.setString(4, student.getGrade());
            stmt.setString(5, student.getEmail());
            stmt.executeUpdate();
        }
    }

    /**
     * 获取所有的学生信息
     */
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setGender(rs.getString("gender"));
                student.setGrade(rs.getString("grade"));
                student.setEmail(rs.getString("email"));
                students.add(student);
            }
        }
        return students;
    }
    /**
     * 更新学生信息
     * 1. 条件更新：通过 where id=?指定更新条件，确保修改对应ID的学生信息
     * 2. 参数顺序：注意占位符顺序setXxx()方法的参数顺序一直（前五个更新字段，第六个为ID）
     */
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name=?, age=?, gender=?, grade=?, email=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getGender());
            stmt.setString(4, student.getGrade());
            stmt.setString(5, student.getEmail());
            stmt.setInt(6, student.getId());  // 补全id参数
            stmt.executeUpdate();  // 添加执行语句
        }
    }
    /**
     * 删除学生信息：
     * 根据传入的ID删除对应记录，确保数据唯一性（通过主键ID定位）
     */
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // 绑定删除条件中的ID
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    /**
     * 通过ID获取学生信息
     */
    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id=?";
        Student student = new Student();
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setAge(rs.getInt("age"));
                    student.setGender(rs.getString("gender"));
                    student.setGrade(rs.getString("grade"));
                    student.setEmail(rs.getString("email"));
                }
            }
        }
        return student;
    }
}