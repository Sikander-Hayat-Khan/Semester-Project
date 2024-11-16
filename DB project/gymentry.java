import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

class gentryLoginPage
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

    public gentryLoginPage()
    {
        frame = new JFrame("LOGIN PAGE");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon background = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\bg.jpg");
        ImageIcon nustIcon = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\logo.png");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");

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
            }
        });

        loginButton = new JButton("Gym Entry");
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
                            String enteredPassword = passwordTf.getText();
                            final String DB_URL = "jdbc:mysql://localhost:3306/";
                            final String USERNAME = "root";
                            final String PASSWORD = "Hashim#00789";
                            int paymentstatus=0;
                            String sql = "SELECT gymmembership.paymentstatus FROM project.users,project.gymmembership WHERE users.userId=gymmembership.Users_userId AND userId="+id;
                            String sql1 = "SELECT userId FROM project.users WHERE password=md5(\""+enteredPassword+"\");";
                            try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                                try {
                                    java.sql.Statement statement = connection.createStatement();
                                    ResultSet rs = statement.executeQuery(sql);
                                    if(rs.next()){
                                        paymentstatus = rs.getInt("paymentstatus");
                                        rs.close();
                                    }
                                    statement.close();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                                try {
                                    java.sql.Statement statement = connection.createStatement();
                                    ResultSet rs = statement.executeQuery(sql1);
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
                            if (actualid == id && paymentstatus == 1) {
                                try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                                    try {
                                        // Create a PreparedStatement to insert the current timestamp
                                        String query = "INSERT INTO project.entries(time,FacilityUsed,Users_userid)\nVALUES (?,\"gym\","+id+");";
                                        PreparedStatement pstmt = connection.prepareStatement(query);
                                        pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));

                                        // Execute the PreparedStatement
                                        pstmt.executeUpdate();
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                                JOptionPane.showMessageDialog(frame,"You are allowed to use the gym.");
                                idTf.setText("");
                                passwordTf.setText("");
                                return;
                            }
                            else{
                                idTf.setText("");
                                passwordTf.setText("");
                                JOptionPane.showMessageDialog(frame,"The password is wrong or your gym Membership status is inactive");
                                return;
                            }
                    }
                    else{
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
        //frame.add(backButton);
        frame.add(loginButton);
        frame.add(nustLogo);
        frame.add(pageBackground);

        frame.setVisible(true);
    }
}

public class gymentry{
    public static void main(String[] args) {
        new gentryLoginPage();
    }
}