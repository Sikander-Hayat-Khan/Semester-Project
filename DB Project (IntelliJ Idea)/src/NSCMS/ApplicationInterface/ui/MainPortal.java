package NSCMS.ApplicationInterface.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class MainPortal
{
    private JFrame frame;
    private JLabel menuBackground;
    private JLabel nustLogo;
    private JButton loginButton;
    private JButton signupButton;
    public MainPortal()
    {
        frame = new JFrame("MAIN MENU");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon background = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB project\\Assets\\WhatsApp Image 2024-12-02 at 13.21.07_4d328b63.jpg");
        ImageIcon nustIcon = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB project\\Assets\\NUST_Logo-removebg-preview.png");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");

        menuBackground = new JLabel(background);
        menuBackground.setLayout(null);
        nustLogo = new JLabel(nustIcon);

        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 25));
        loginButton.setForeground(Color.BLACK);
        loginButton.setIcon(buttonBackground);
        loginButton.setHorizontalTextPosition(SwingConstants.CENTER);
        loginButton.setVerticalTextPosition(SwingConstants.CENTER);
        loginButton.setBorder(new LineBorder(Color.ORANGE, 3));
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                loginButton.setSize(260, 90);
            }

            public void mouseClicked(MouseEvent e){
                new LoginPage();
                frame.setVisible(false);
            }
        });

        signupButton = new JButton("SIGN UP");
        signupButton.setFont(new Font("Arial", Font.BOLD, 25));
        signupButton.setForeground(Color.BLACK);
        signupButton.setIcon(buttonBackground);
        signupButton.setHorizontalTextPosition(SwingConstants.CENTER);
        signupButton.setVerticalTextPosition(SwingConstants.CENTER);
        signupButton.setBorder(new LineBorder(Color.ORANGE, 3));
        signupButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                signupButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                signupButton.setSize(260, 90);
            }

            public void mouseClicked(MouseEvent e)
            {
                new SignupPage();
                frame.setVisible(false);
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e)
            {
                menuBackground.setBounds(-100, 0, frame.getWidth(), frame.getHeight());
                nustLogo.setBounds(frame.getWidth() / 2 - 250, frame.getHeight() / 2 - 250, 500, 500);
                signupButton.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 + 265, 260, 90);
                loginButton.setBounds(frame.getWidth() / 2 + 150, frame.getHeight() / 2 + 265, 260, 90);
            }
        });

        frame.add(signupButton);
        frame.add(loginButton);
        frame.add(nustLogo);
        frame.add(menuBackground);

        frame.setVisible(true);
    }
}