package gui;

import dao.AdminDao;
import dao.CapocantiereDao;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private static JFrame frame;
    private JPanel loginPanel;
    private JPanel rolePanel;
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JButton loginButton;
    private JComboBox<String> roleCombobox;
    private JLabel roleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel buttonPanel;

    public LoginGUI() {
        super("Login");
        //this.setMinimumSize(new Dimension(600,400));


        this.loginButton.setMargin(new Insets(10, 10, 10, 10));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.pack();

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            char[] password = passwordField.getPassword();

            if (!(String.valueOf(password).isEmpty() || username.isEmpty())) {
                switch (roleCombobox.getSelectedIndex()) {
                    case 0 -> {
                        AdminDao adminDao = new AdminDao();
                        if (adminDao.login(username, String.valueOf(password))) {
                            JOptionPane.showMessageDialog(frame, "Benvenuto admin " + username + ".");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Accesso non consentito.");
                        }
                    }

                    case 1 -> {
                        CapocantiereDao ccDao = new CapocantiereDao();
                        if (ccDao.login(username, String.valueOf(password))) {
                            JOptionPane.showMessageDialog(frame, "Benvenuto capocantiere " + username + ".");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Accesso non consentito.");
                        }

                    }

                    default ->
                            JOptionPane.showMessageDialog(frame, "Accesso non consentito.");
                }
            }
        });
    }

    public static void main (String[] args) {
        frame = new LoginGUI();
        frame.setVisible(true);
    }
}
