import javax.swing.SwingUtilities;
import vista.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}