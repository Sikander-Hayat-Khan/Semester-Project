package NSCMS.ApplicationInterface.ui;

import NSCMS.ApplicationInterface.Main.App;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.border.LineBorder;

public class MainPortal {
    private JFrame frame;
    private JLabel menuBackground;
    private JLabel nustLogo;
    private JButton loginButton;
    private JButton signupButton;
    private JButton backButton; // New Back Button

    public MainPortal() {
        frame = new JFrame("MAIN MENU");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon background = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\CreatedBackground.png");
        ImageIcon nustIcon = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\NUST_Logo-removebg-preview.png");
        ImageIcon buttonBackground = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\ButtonBackground.png");

        menuBackground = new JLabel(background);
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

            public void mouseClicked(MouseEvent e) {
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

            public void mouseClicked(MouseEvent e) {
                new SignupPage();
                frame.setVisible(false);
            }
        });

        // Add a back button
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 25));
        backButton.setForeground(Color.BLACK);
        backButton.setIcon(buttonBackground);
        backButton.setHorizontalTextPosition(SwingConstants.CENTER);
        backButton.setVerticalTextPosition(SwingConstants.CENTER);
        backButton.setBorder(new LineBorder(Color.ORANGE, 3));
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                backButton.setSize(260, 90);
            }

            public void mouseClicked(MouseEvent e) {
                new App();
                frame.setVisible(false);
            }
        });

        // Adjust components on resize
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                menuBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                nustLogo.setBounds(frame.getWidth() / 2 - 250, frame.getHeight() / 2 - 250, 500, 500);
                signupButton.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 + 265, 260, 90);
                loginButton.setBounds(frame.getWidth() / 2 + 150, frame.getHeight() / 2 + 265, 260, 90);
                backButton.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() - 150, 260, 90); // Bottom center
            }
        });

        frame.add(signupButton);
        frame.add(loginButton);
        frame.add(backButton); // Add back button to the frame
        frame.add(nustLogo);
        frame.add(menuBackground);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainPortal();
    }
}
