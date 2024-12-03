package NSCMS.ApplicationInterface.ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoginPage
{
    private JFrame frame;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel pageBackground;
    private JLabel nustLogo;
    private JButton backButton;
    private JButton loginButton;
    private JTextField idTf;
    private JPasswordField passwordTf;
    int id;
    int actualid;

    public LoginPage()
    {
        frame = new JFrame("LOGIN PAGE");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon background = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\bg.jpg");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");
        ImageIcon nustIcon = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\logo.png");

        nustLogo = new JLabel(nustIcon);

        idTf = new JTextField(20);
        passwordTf = new JPasswordField(30);

        idTf.setBackground(Color.ORANGE);
        idTf.setForeground(Color.BLACK);
        passwordTf.setBackground(Color.ORANGE);
        passwordTf.setForeground(Color.BLACK);


        idLabel = new JLabel("ENTER YOUR CMS");
        idLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18));
        idLabel.setForeground(Color.ORANGE);
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);

        passwordLabel = new JLabel("ENTER PASSWORD");
        passwordLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18));
        passwordLabel.setForeground(Color.ORANGE);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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

            public void mouseClicked(MouseEvent e)
            {
                new MainPortal();
                frame.setVisible(false);
            }
        });

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

            public void mouseClicked(MouseEvent e)
            {
                loginButton.addActionListener(new ActionListener() {

                    @SuppressWarnings("deprecation")
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!idTf.getText().trim().isEmpty() && !passwordTf.getText().trim().isEmpty()){
                            id=Integer.parseInt(idTf.getText());
                            final String DB_URL = "jdbc:mysql://localhost:3306/";
                            final String USERNAME = "root";
                            final String PASSWORD = "Hashim#00789";
                            String enteredPassword = passwordTf.getText();
                            String sql = "SELECT userId FROM project.users WHERE password=md5(\""+enteredPassword+"\");";

                            try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                                try {
                                    java.sql.Statement statement = connection.createStatement();
                                    ResultSet rs = statement.executeQuery(sql);
                                    if(rs.next()){
                                        actualid = rs.getInt("userId");
                                        rs.close();
                                    }
                                    statement.close();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            if (id==actualid) {
                                close();
                                new Dashboard(id);
                            }
                            else{
                                JOptionPane.showMessageDialog(frame,"Login Unsuccessful.");
                                idTf.setText("");
                                passwordTf.setText("");
                                return;
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(frame,"Please enter both cms and password.");
                            idTf.setText("");
                            passwordTf.setText("");
                            return;
                        }
                    }});
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e)
            {
                pageBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                nustLogo.setBounds(frame.getWidth() / 2 - 250, frame.getHeight() / 2 - 250, 500, 500);

                idLabel.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 - 400, 260, 90);
                idTf.setBounds(frame.getWidth() / 2 - 250, frame.getHeight() / 2 - 375, 550, 30);

                passwordLabel.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 - 350, 260, 90);
                passwordTf.setBounds(frame.getWidth() / 2 - 250, frame.getHeight() / 2 - 325, 550, 30);

                backButton.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 + 265, 260, 90);
                loginButton.setBounds(frame.getWidth() / 2 + 150, frame.getHeight() / 2 + 265, 260, 90);
            }
        });

        pageBackground = new JLabel(background);

        frame.add(idLabel);
        frame.add(idTf);
        frame.add(passwordLabel);
        frame.add(passwordTf);
        frame.add(backButton);
        frame.add(loginButton);
        frame.add(nustLogo);
        frame.add(pageBackground);

        frame.setVisible(true);
    }

    public void close(){
        frame.setVisible(false);
    }
}