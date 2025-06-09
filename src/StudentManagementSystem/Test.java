package StudentManagementSystem;
import StudentManagementSystem.view.StudentFrame;

public class Test {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentFrame().setVisible(true);
            }
        });
    }
}