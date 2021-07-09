package gui;

import javax.swing.*;

public class MainGUI extends JFrame {
    private static JFrame frame;
    private JPanel mainPanel;
    private JSplitPane splitPane;
    private JPanel masterPanel;
    private JPanel detailPanel;
    private JPanel logoPanel;
    private JPanel buttonListPanel;
    private JButton logoutButton;
    private JButton cantieriButton;
    private JButton capocantieriButton;
    private JButton operaiButton;
    private JButton sensoriButton;
    private JButton panoramicaButton;
    private JPanel loginPanel;
    private JPanel panoramicaPanel;
    private JPanel cantieriPanel;
    private JPanel capocantieriPanel;
    private JPanel operaiPanel;
    private JPanel sensoriPanel;

    public MainGUI() {
        super("Gestionale Cantieri");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);


        this.pack();
    }

    public static void main(String[] args) {
        frame = new MainGUI();
        frame.setVisible(true);
    }
}
