package NSCMS.ApplicationInterface.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

class GymMembership
{
    private JFrame frame;
    private JLabel menuBackground;
    private JLabel nustLogo;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel startSubciptionsLabel;

    @SuppressWarnings("unused")
    private int id;

    public GymMembership(int id)
    {
        frame = new JFrame("MAIN MENU");
        this.id=id;
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startSubciptionsLabel = new JLabel("START/RENEW YOUR MEMBERSHIP BY UPLOADING THE RECEIPT");
        startSubciptionsLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 30));
        startSubciptionsLabel.setForeground(Color.ORANGE);
        startSubciptionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon background = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\bg.jpg");
        ImageIcon nustIcon = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\logo.png");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");

        menuBackground = new JLabel(background);
        nustLogo = new JLabel(nustIcon);

        loginButton = new JButton("Back");
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
                new Dashboard(id);
                frame.setVisible(false);
            }
        });

        signupButton = new JButton("Upload Receipt");
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
                JFileChooser fc= new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));
                int returnValue = fc.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    try {
                        //BufferedImage image = ImageIO.read(selectedFile);
                        //ImageIcon icon = new ImageIcon(image);
                        FileInputStream fis = new FileInputStream(selectedFile);
                        final String DB_URL = "jdbc:mysql://localhost:3306/";
                        final String USERNAME = "root";
                        final String PASSWORD = "Hashim#00789";

                        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO project.gymmembership (paymentstatus,Receipt,Users_userId) VALUES (0,?,"+id+")");
                            pstmt.setBinaryStream(1, fis, (int)fis.available());
                            pstmt.executeUpdate();

                            fis.close();
                            pstmt.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error reading image file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(frame,"Your Receipt has been uploaded.");
                    frame.setVisible(false);
                    new Dashboard(id);
                    frame.setVisible(false);
                }
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e)
            {
                menuBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                startSubciptionsLabel.setBounds(frame.getWidth() / 2 - 400, frame.getHeight() / 2 - 550, 800, 500);
                nustLogo.setBounds(frame.getWidth() / 2 - 250, frame.getHeight() / 2 - 250, 500, 500);
                signupButton.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 + 265, 260, 90);
                loginButton.setBounds(frame.getWidth() / 2 + 150, frame.getHeight() / 2 + 265, 260, 90);
            }
        });

        frame.add(startSubciptionsLabel);
        frame.add(signupButton);
        frame.add(loginButton);
        frame.add(nustLogo);
        frame.add(menuBackground);

        frame.setVisible(true);
    }
}