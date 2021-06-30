import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private JComboBox<String> roleComboBox;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JLabel roleLabel;
    private JLabel UsernameLabel;
    private JLabel passwordLabel;
    private JPanel loginPanel;

    public LoginGUI() {
        super("Login");

        // Configurazione Combobox
        roleComboBox.addItem("Admin");
        roleComboBox.addItem("Capocantiere");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.setMinimumSize(new Dimension(600,400));
        this.pack();

    }

    public static void main (String[] args) {
        JFrame frame = new LoginGUI();
        frame.setVisible(true);
    }
}
