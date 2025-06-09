package StudentManagementSystem.view;

import StudentManagementSystem.dao.StudentDao;
import StudentManagementSystem.model.Student;

import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StudentFrame extends JFrame {
    //初始化界面控件对象
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, ageField, genderField, gradeField, emailField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    //声明Dao类对象
    private StudentDao studentDao;

    //定义构造方法
    public StudentFrame() {
        setTitle("学生信息管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        studentDao = new StudentDao();
        initComponents();
        loadStudents();
    }

    //初始化界面
    private void initComponents() {
        //1. 输入面板
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("姓名："));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("年龄："));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("性别："));
        genderField = new JTextField();
        inputPanel.add(genderField);

        inputPanel.add(new JLabel("年级："));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        inputPanel.add(new JLabel("邮箱："));
        emailField = new JTextField();
        inputPanel.add(emailField);

        //2. 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //添加按钮
        JButton addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        buttonPanel.add(addButton);

        //修改按钮
        // 修改按钮（修正变量名）
        updateButton = new JButton("修改"); // 变量名改为 updateButton
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });
        buttonPanel.add(updateButton); // 添加正确的按钮变量

        // 删除按钮（修正变量名）
        deleteButton = new JButton("删除"); // 变量名改为 deleteButton，文本改为"删除"
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        buttonPanel.add(deleteButton); // 添加正确的按钮变量


        //清空按钮
        clearButton = new JButton("清空");  // 变量名改为 clearButton，文本改为"清空"
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearField();
            }
        });
        buttonPanel.add(clearButton);  // 确保添加的是已初始化的按钮


        //3. 表格
        String[] columnNames = {"ID", "姓名", "年龄", "性别", "年级", "邮箱"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    displayStudent(selectedRow);
                }
            }
        });

        // 主面板布局
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER); // 表格占据中心区域
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // 按钮面板放在底部


        add(mainPanel);
    }

    //加载学生信息
    private void loadStudents() {
        try {
            List<Student> students = studentDao.getAllStudents();
            tableModel.setRowCount(0); // 清空表格

            for(Student student:  students){
                Object[] rawData = {
                        student.getId(),
                        student.getName(),
                        student.getAge(),
                        student.getGender(),
                        student.getGrade(),
                        student.getEmail(),
                };
                tableModel.addRow(rawData);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, "加载学生信息失败"+e.getMessage()
                    , "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    // 添加按钮对应的方法
    private void addStudent(){
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderField.getText().trim();
            String grade = gradeField.getText().trim();
            String email = emailField.getText().trim();

            Student student = new Student(name, age, gender, grade, email);
            studentDao.addStudent(student);

            loadStudents();
            clearField();
            JOptionPane.showMessageDialog(this, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "请输入正确的年龄", "错误", JOptionPane.ERROR_MESSAGE);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, "添加学生信息失败"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    // 修改按钮对应的方法
    private void updateStudent(){
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0){
            JOptionPane.showMessageDialog(this, "请选择要修改的学生", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderField.getText().trim();
            String grade = gradeField.getText().trim();
            String email = emailField.getText().trim();

            Student student = new Student(name, age, gender, grade, email);
            student.setId(id);

            studentDao.updateStudent(student);
            loadStudents();
            clearField();
            JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "请输入正确的年龄", "错误", JOptionPane.ERROR_MESSAGE);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, "修改学生信息失败"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    // 删除按钮对应方法
    private void deleteStudent(){
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0){
            JOptionPane.showMessageDialog(this, "请选择要删除的学生", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除吗？\n此操作不可逆！", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION){
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                studentDao.deleteStudent(id);
                loadStudents();
                clearField();
                JOptionPane.showMessageDialog(this, "学生信息删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException e){
                JOptionPane.showMessageDialog(this, "删除学生信息失败"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // 显示学生信息的方法
    private void displayStudent(int selectedRow){
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        int age = (int) tableModel.getValueAt(selectedRow, 2);
        String gender = (String) tableModel.getValueAt(selectedRow, 3);
        String grade = (String) tableModel.getValueAt(selectedRow, 4);
        String email = (String) tableModel.getValueAt(selectedRow, 5);

        nameField.setText(name);
        ageField.setText(String.valueOf(age));
        genderField.setText(gender);
        gradeField.setText(grade);
        emailField.setText(email);
    }
    // 清空学生数据方法
    private void clearField(){
        nameField.setText("");
        ageField.setText("");
        genderField.setText("");
        gradeField.setText("");
        emailField.setText("");
        table.clearSelection();
    }
}