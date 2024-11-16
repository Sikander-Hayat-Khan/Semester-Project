
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

class MainPortal
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

        ImageIcon background = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\bg.jpg");
        ImageIcon nustIcon = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\logo.png");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");

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
                new SignUpPage();
                frame.setVisible(false);
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e)
            {
                menuBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
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

class LoginPage
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

class SignUpPage
{
    private JFrame frame;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel schoolLabel;
    private JLabel genderLabel;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JLabel phone1JLabel;
    private JLabel phone2JLabel;
    private JLabel phone3JLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel pageBackground;
    private JLabel userTypeLabel;
    private JLabel maleLabel;
    private JLabel femaleLabel;
    private JLabel studentLabel;
    private JLabel facultyLabel;
    private JRadioButton studentButton;
    private JRadioButton facultyButton;
    private JButton backButton;
    private JButton registerButton;
    private ButtonGroup genderGroup;
    private ButtonGroup userTypeGroup;
    private JTextField idTf;
    private JTextField nameTf;
    private JTextField schoolTf;
    private JTextField phone1Tf;
    private JTextField phone2Tf;
    private JTextField phone3Tf;
    private JTextField emailTf;
    private JPasswordField passwordTf;
    private JTextField studentClassTf;
    private JTextField facultyOfficeTf;
    private JLabel stdClassLabel;
    private JLabel fclOfficeLabel;
    private JLabel designationLabel;
    private JTextField designationTf;
    private int id;
    private String name;
    private String email;
    private String gender;
    private String phone1;
    private String phone2;
    private String phone3;
    private String password;
    private String school;
    private char userType;

    public SignUpPage()
    {
        frame = new JFrame("SIGN UP PAGE");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon background = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\bg.jpg");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");

        idTf = new JTextField(20);
        nameTf = new JTextField(40);
        schoolTf = new JTextField(30);
        phone1Tf = new JTextField(20);
        phone2Tf = new JTextField(20);
        phone3Tf = new JTextField(20);
        emailTf = new JTextField(40);
        passwordTf = new JPasswordField(30);
        studentClassTf = new JTextField(30);
        facultyOfficeTf = new JTextField(40);
        designationTf = new JTextField(40);

        studentClassTf.setEditable(false);
        facultyOfficeTf.setEditable(false);
        designationTf.setEditable(false);

        

        idTf.setBackground(Color.ORANGE);
        idTf.setForeground(Color.BLACK);
        nameTf.setBackground(Color.ORANGE);
        nameTf.setForeground(Color.BLACK);
        schoolTf.setBackground(Color.ORANGE);
        schoolTf.setForeground(Color.BLACK);
        phone1Tf.setBackground(Color.ORANGE);
        phone1Tf.setForeground(Color.BLACK);
        phone2Tf.setBackground(Color.ORANGE);
        phone2Tf.setForeground(Color.BLACK);
        phone3Tf.setBackground(Color.ORANGE);
        phone3Tf.setForeground(Color.BLACK);
        emailTf.setBackground(Color.ORANGE);
        emailTf.setForeground(Color.BLACK);
        passwordTf.setBackground(Color.ORANGE);
        passwordTf.setForeground(Color.BLACK);
        studentClassTf.setBackground(Color.ORANGE);
        studentClassTf.setForeground(Color.BLACK);
        facultyOfficeTf.setBackground(Color.ORANGE);
        facultyOfficeTf.setForeground(Color.BLACK);
        designationTf.setBackground(Color.ORANGE);
        designationTf.setForeground(Color.BLACK);
        stdClassLabel = new JLabel("ENTER CLASS");
        stdClassLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        stdClassLabel.setForeground(Color.ORANGE);
        stdClassLabel.setHorizontalAlignment(SwingConstants.CENTER);

        fclOfficeLabel = new JLabel("ENTER OFFICE");
        fclOfficeLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        fclOfficeLabel.setForeground(Color.ORANGE);
        fclOfficeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        designationLabel = new JLabel("ENTER DESIGNATION");
        designationLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        designationLabel.setForeground(Color.ORANGE);
        designationLabel.setHorizontalAlignment(SwingConstants.CENTER);


        idLabel = new JLabel("ENTER YOUR CMS");
        idLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        idLabel.setForeground(Color.ORANGE);
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel = new JLabel("ENTER YOUR NAME");
        nameLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        nameLabel.setForeground(Color.ORANGE);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        schoolLabel = new JLabel("ENTER SCHOOL");
        schoolLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        schoolLabel.setForeground(Color.ORANGE);
        schoolLabel.setHorizontalAlignment(SwingConstants.CENTER);

        genderLabel = new JLabel("GENDER");
        genderLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        genderLabel.setForeground(Color.ORANGE);
        genderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        maleLabel = new JLabel("MALE");
        maleLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        maleLabel.setForeground(Color.ORANGE);
        maleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        femaleLabel = new JLabel("FEMALE");
        femaleLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        femaleLabel.setForeground(Color.ORANGE);
        femaleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        studentLabel = new JLabel("STUDENT");
        studentLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        studentLabel.setForeground(Color.ORANGE);
        studentLabel.setHorizontalAlignment(SwingConstants.CENTER);

        facultyLabel = new JLabel("FACULTY");
        facultyLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        facultyLabel.setForeground(Color.ORANGE);
        facultyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        userTypeLabel = new JLabel("USER TYPE");
        userTypeLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        userTypeLabel.setForeground(Color.ORANGE);
        userTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        phone1JLabel = new JLabel("FIRST CONTACT NUMBER");
        phone1JLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        phone1JLabel.setForeground(Color.ORANGE);
        phone1JLabel.setHorizontalAlignment(SwingConstants.CENTER);

        phone2JLabel = new JLabel("SECOND CONTACT NUMBER");
        phone2JLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        phone2JLabel.setForeground(Color.ORANGE);
        phone2JLabel.setHorizontalAlignment(SwingConstants.CENTER);

        phone3JLabel = new JLabel("THIRD CONTACT NUMBER");
        phone3JLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        phone3JLabel.setForeground(Color.ORANGE);
        phone3JLabel.setHorizontalAlignment(SwingConstants.CENTER);

        emailLabel = new JLabel("ENTER EMAIL");
        emailLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        emailLabel.setForeground(Color.ORANGE);
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);

        passwordLabel = new JLabel("ENTER PASSWORD");
        passwordLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 18)); 
        passwordLabel.setForeground(Color.ORANGE);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
        studentClassTf.setVisible(false);
        facultyOfficeTf.setVisible(false);
        stdClassLabel.setVisible(false);
        fclOfficeLabel.setVisible(false);
        designationLabel.setVisible(false);
        designationTf.setVisible(false);
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 25));
        registerButton.setForeground(Color.BLACK);
        registerButton.setIcon(buttonBackground);
        registerButton.setHorizontalTextPosition(SwingConstants.CENTER);
        registerButton.setVerticalTextPosition(SwingConstants.CENTER);
        registerButton.setBorder(new LineBorder(Color.ORANGE, 3));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        registerButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                registerButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                registerButton.setSize(260, 90);
            }

            public void mouseClicked(MouseEvent e)
            {
                registerButton.addActionListener(new ActionListener() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!validateInput()) {
                                JOptionPane.showMessageDialog(frame,"Please fill all the mandatory fields");
                                return;
                            }

                            id = Integer.parseInt(idTf.getText());
                            name = nameTf.getText();
                            school = schoolTf.getText();
                            phone1 = phone1Tf.getText();
                            phone2 = phone2Tf.getText();
                            phone3 = phone3Tf.getText();
                            email = emailTf.getText();
                            password = passwordTf.getText();
                            String sql2;
                            String sql = "INSERT INTO project.users\nVALUES("+id+",\""+name+"\",\""+school+"\",\""+gender+"\",\""+phone1+"\",\""+phone2+"\",\""+phone3+"\",\""+email+"\",md5(\""+password+"\"),\""+userType+"\");";
                            if(userType == 'S')
                                sql2 = "INSERT INTO project.student\nVALUES("+id+",\""+studentClassTf.getText()+"\");";
                            else
                                sql2 = "INSERT INTO project.faculty\nVALUES("+id+",\""+facultyOfficeTf.getText()+"\",\""+designationTf.getText()+"\");";
                            final String DB_URL = "jdbc:mysql://localhost:3306/";
                            final String USERNAME = "root";
                            final String PASSWORD = "Hashim#00789";

                            try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                                try {
                                    System.out.println("Connection established");
                                    java.sql.Statement statement = connection.createStatement();
                                    statement.executeUpdate(sql);
                                    statement.executeUpdate(sql2);
                                    statement.close();
                                } catch (SQLException e1) {
                                    System.out.println("Connection failed");
                                    e1.printStackTrace();
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(frame,"SignUp Successful.");
                            new MainPortal();
                            frame.setVisible(false);
                        }
                });
            }
        });

        maleButton = new JRadioButton();
        maleButton.setFont(new Font("Arial", Font.BOLD, 16));
        maleButton.setForeground(Color.BLACK);
        maleButton.setHorizontalTextPosition(SwingConstants.CENTER);
        maleButton.setVerticalTextPosition(SwingConstants.CENTER);

        femaleButton = new JRadioButton();
        femaleButton.setFont(new Font("Arial", Font.BOLD, 16));
        femaleButton.setForeground(Color.BLACK);
        femaleButton.setHorizontalTextPosition(SwingConstants.CENTER);
        femaleButton.setVerticalTextPosition(SwingConstants.CENTER);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        maleButton.addItemListener(new ItemListener() {            
            @Override
            public void itemStateChanged(ItemEvent e) {
                gender = "Male";
            }
        });

        femaleButton.addItemListener(new ItemListener() {            
            @Override
            public void itemStateChanged(ItemEvent e) {
                gender = "Female";
            }
        });

        studentButton = new JRadioButton();
        studentButton.setFont(new Font("Arial", Font.BOLD, 16));
        studentButton.setForeground(Color.BLACK);
        studentButton.setHorizontalTextPosition(SwingConstants.CENTER);
        studentButton.setVerticalTextPosition(SwingConstants.CENTER);

        facultyButton = new JRadioButton();
        facultyButton.setFont(new Font("Arial", Font.BOLD, 16));
        facultyButton.setForeground(Color.BLACK);
        facultyButton.setHorizontalTextPosition(SwingConstants.CENTER);
        facultyButton.setVerticalTextPosition(SwingConstants.CENTER);

        userTypeGroup = new ButtonGroup();
        userTypeGroup.add(studentButton);
        userTypeGroup.add(facultyButton);

        studentButton.addItemListener(new ItemListener() {            
            @Override
            public void itemStateChanged(ItemEvent e) {
                userType = 'S';
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    studentClassTf.setVisible(true);
                    stdClassLabel.setVisible(true);
                    studentClassTf.setEditable(true);
                }
                else{ 
                    studentClassTf.setVisible(false);
                    stdClassLabel.setVisible(false);
                    studentClassTf.setEditable(false);
                }
            }
        });

        facultyButton.addItemListener(new ItemListener() {            
            @Override
            public void itemStateChanged(ItemEvent e) {
                userType = 'F';
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    facultyOfficeTf.setVisible(true);
                    designationTf.setVisible(true);
                    fclOfficeLabel.setVisible(true);
                    designationLabel.setVisible(true);
                    facultyOfficeTf.setEditable(true);
                    designationTf.setEditable(true);
                }
                else {
                    facultyOfficeTf.setVisible(false);
                    designationTf.setVisible(false);
                    fclOfficeLabel.setVisible(false);
                    designationLabel.setVisible(false);
                    facultyOfficeTf.setEditable(false);
                    designationTf.setEditable(false);
                }
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e)
            {
                pageBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                
                idLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 350, 260, 90);
                idTf.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 - 325, 550, 30);

                nameLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 310, 260, 90);
                nameTf.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 - 285, 550, 30);

                schoolLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 270, 260, 90);
                schoolTf.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 - 245, 550, 30);

                genderLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 230, 260, 90);
                maleLabel.setBounds(frame.getWidth() / 2 - 200, frame.getHeight() / 2 - 230, 260, 90);
                maleButton.setBounds(frame.getWidth() / 2 - 25, frame.getHeight() / 2 - 200, 25, 25);
                femaleLabel.setBounds(frame.getWidth() / 2 - 40, frame.getHeight() / 2 - 230, 260, 90);
                femaleButton.setBounds(frame.getWidth() / 2 + 135, frame.getHeight() / 2 - 200, 25, 25);

                phone1JLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 190, 260, 90);
                phone1Tf.setBounds(frame.getWidth() / 2 - 130, frame.getHeight() / 2 - 165, 550, 30);

                phone2JLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 150, 260, 90);
                phone2Tf.setBounds(frame.getWidth() / 2 - 120, frame.getHeight() / 2 - 125, 550, 30);

                phone3JLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 110, 260, 90);
                phone3Tf.setBounds(frame.getWidth() / 2 - 120, frame.getHeight() / 2 - 85, 550, 30);

                emailLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 70, 260, 90);
                emailTf.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 - 45, 550, 30);

                passwordLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 - 30, 260, 90);
                passwordTf.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 - 5, 550, 30);

                userTypeLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 + 10, 260, 90);
                studentLabel.setBounds(frame.getWidth() / 2 - 200, frame.getHeight() / 2 + 10, 260, 90);
                studentButton.setBounds(frame.getWidth() / 2 - 25, frame.getHeight() / 2 + 40, 25, 25);

                facultyLabel.setBounds(frame.getWidth() / 2 - 40, frame.getHeight() / 2 + 10, 260, 90);
                facultyButton.setBounds(frame.getWidth() / 2 + 135, frame.getHeight() / 2 + 40, 25, 25);

                stdClassLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 + 40, 260, 90);
                studentClassTf.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 + 65, 550, 30);


                fclOfficeLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 + 70, 260, 90);
                facultyOfficeTf.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 + 100, 550, 30);

                designationLabel.setBounds(frame.getWidth() / 2 - 350, frame.getHeight() / 2 + 100, 260, 90);
                designationTf.setBounds(frame.getWidth() / 2 - 140, frame.getHeight() / 2 + 135, 550, 30);

                backButton.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 + 265, 260, 90);
                registerButton.setBounds(frame.getWidth() / 2 + 150, frame.getHeight() / 2 + 265, 260, 90);
            }
    });


        

        pageBackground = new JLabel(background);

        frame.add(idLabel);
        frame.add(idTf);
        frame.add(nameLabel);
        frame.add(nameTf);
        frame.add(schoolLabel);
        frame.add(schoolTf);
        frame.add(genderLabel);
        frame.add(maleLabel);
        frame.add(maleButton);
        frame.add(femaleLabel);
        frame.add(femaleButton);
        frame.add(phone1JLabel);
        frame.add(phone1Tf);
        frame.add(phone2JLabel);
        frame.add(phone2Tf);
        frame.add(phone3JLabel);
        frame.add(phone3Tf);
        frame.add(emailLabel);
        frame.add(emailTf);
        frame.add(passwordLabel);
        frame.add(passwordTf);
        frame.add(userTypeLabel);
        frame.add(studentLabel);
        frame.add(studentButton);
        frame.add(facultyLabel);
        frame.add(facultyButton);
        frame.add(stdClassLabel);
        frame.add(fclOfficeLabel);
        frame.add(studentClassTf);
        frame.add(facultyOfficeTf);
        frame.add(designationLabel);
        frame.add(designationTf);
        frame.add(backButton);
        frame.add(registerButton);
        frame.add(pageBackground);
        frame.setVisible(true);
    }

    @SuppressWarnings("deprecation")
    private boolean validateInput() {
        
        if (idTf.getText().trim().isEmpty() || nameTf.getText().trim().isEmpty() || schoolTf.getText().trim().isEmpty() || phone1Tf.getText().trim().isEmpty() || emailTf.getText().trim().isEmpty() || passwordTf.getText().trim().isEmpty() || !(studentButton.isSelected() || facultyButton.isSelected()) || !(maleButton.isSelected() || femaleButton.isSelected()) || !(studentButton.isSelected() || facultyButton.isSelected())) {
            return false;
        }
        return true;
    }
}

class Dashboard
{
    private JFrame frame;
    private JLabel dashboardBackground;
    private JLabel nustLogo;
    private JButton swimmingButton;
    private JButton gymButton;
    private JButton ridingButton;
    private JButton swimmingSubButton;
    private JButton gymSubButton;
    private JButton ridingSubButton;
    private JButton timingButton;
    private JButton historyButton;
    private JLabel membershipLabel;
    private JLabel subcriptionLabel;
    @SuppressWarnings("unused")
    private int id;

    public Dashboard(int id)
    {
        frame = new JFrame("NUST SPORTS MANAGEMENT SYSTEM");
        this.id=id;
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);

        ImageIcon background = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\bg.jpg");
        ImageIcon nustIcon = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\logo.png");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");


        dashboardBackground = new JLabel(background);
        nustLogo = new JLabel(nustIcon);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e)
            {
                dashboardBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                nustLogo.setBounds(frame.getWidth() / 2 - 250, frame.getHeight() / 2 - 250, 500, 500);
                membershipLabel.setBounds(frame.getWidth() / 2 - 700, frame.getHeight() / 2 - 300, 400, 80);
                subcriptionLabel.setBounds(frame.getWidth() / 2 + 300, frame.getHeight() / 2 - 300, 400, 80);
                swimmingButton.setBounds(frame.getWidth() / 2 - 645, frame.getHeight() / 2 - 200, 260, 90);
                gymButton.setBounds(frame.getWidth() / 2 - 645, frame.getHeight() / 2 - 75, 260, 90);
                ridingButton.setBounds(frame.getWidth() / 2 - 645, frame.getHeight() / 2 + 50, 260, 90);
                swimmingSubButton.setBounds(frame.getWidth() / 2 + 375, frame.getHeight() / 2 - 200, 260, 90);
                gymSubButton.setBounds(frame.getWidth() / 2 + 375, frame.getHeight() / 2 - 75, 260, 90);
                ridingSubButton.setBounds(frame.getWidth() / 2 + 375, frame.getHeight() / 2 + 50, 260, 90);
                timingButton.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 + 265, 260, 90);
                historyButton.setBounds(frame.getWidth() / 2 + 150, frame.getHeight() / 2 + 265, 260, 90);
            }
        });

        membershipLabel = new JLabel("GET A MEMBERSHIP");
        membershipLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 50)); 
        membershipLabel.setForeground(Color.ORANGE);
        membershipLabel.setHorizontalAlignment(SwingConstants.CENTER);

        subcriptionLabel = new JLabel("SUBSCRIPTIONS DETAILS");
        subcriptionLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 35)); 
        subcriptionLabel.setForeground(Color.ORANGE);
        subcriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        swimmingButton = new JButton("SWIMMING");
        gymButton = new JButton("GYM");
        ridingButton = new JButton("HORSE RIDING");
        timingButton = new JButton("SIGN OUT");
        historyButton = new JButton("ACCESS HISTORY");
        swimmingSubButton = new JButton("SWIMMING SUBSCRIPTION");
        gymSubButton = new JButton("GYM SUBSCRIPTION");
        ridingSubButton = new JButton("HORSE RIDING SUBSCRIPTION");

        swimmingButton.setFont(new Font("Arial", Font.BOLD, 25));
        swimmingButton.setForeground(Color.BLACK);
        swimmingButton.setIcon(buttonBackground);
        swimmingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        swimmingButton.setVerticalTextPosition(SwingConstants.CENTER);
        swimmingButton.setBorder(new LineBorder(Color.ORANGE, 3));
        swimmingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                swimmingButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                swimmingButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                new SwimmingMembership(id);
                frame.setVisible(false);
            }
        });

        gymButton.setFont(new Font("Arial", Font.BOLD, 25));
        gymButton.setForeground(Color.BLACK);
        gymButton.setIcon(buttonBackground);
        gymButton.setHorizontalTextPosition(SwingConstants.CENTER);
        gymButton.setVerticalTextPosition(SwingConstants.CENTER);
        gymButton.setBorder(new LineBorder(Color.ORANGE, 3));
        gymButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                gymButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                gymButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                new GymMembership(id);
                frame.setVisible(false);
            }
        });

        ridingButton.setFont(new Font("Arial", Font.BOLD, 25));
        ridingButton.setForeground(Color.BLACK);
        ridingButton.setIcon(buttonBackground);
        ridingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        ridingButton.setVerticalTextPosition(SwingConstants.CENTER);
        ridingButton.setBorder(new LineBorder(Color.ORANGE, 3));
        ridingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                ridingButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                ridingButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                new RidingMembership(id);
                frame.setVisible(false);
            }
        });

        swimmingSubButton.setFont(new Font("Arial", Font.BOLD, 18));
        swimmingSubButton.setForeground(Color.BLACK);
        swimmingSubButton.setIcon(buttonBackground);
        swimmingSubButton.setHorizontalTextPosition(SwingConstants.CENTER);
        swimmingSubButton.setVerticalTextPosition(SwingConstants.CENTER);
        swimmingSubButton.setBorder(new LineBorder(Color.ORANGE, 3));
        swimmingSubButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                swimmingSubButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                swimmingSubButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                new SwimmingSubscription(id);
                frame.setVisible(false);
            }
        });

        gymSubButton.setFont(new Font("Arial", Font.BOLD, 18));
        gymSubButton.setForeground(Color.BLACK);
        gymSubButton.setIcon(buttonBackground);
        gymSubButton.setHorizontalTextPosition(SwingConstants.CENTER);
        gymSubButton.setVerticalTextPosition(SwingConstants.CENTER);
        gymSubButton.setBorder(new LineBorder(Color.ORANGE, 3));
        gymSubButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                gymSubButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                gymSubButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                new GymSubscription(id);
                frame.setVisible(false);
            }
        });

        ridingSubButton.setFont(new Font("Arial", Font.BOLD, 16));
        ridingSubButton.setForeground(Color.BLACK);
        ridingSubButton.setIcon(buttonBackground);
        ridingSubButton.setHorizontalTextPosition(SwingConstants.CENTER);
        ridingSubButton.setVerticalTextPosition(SwingConstants.CENTER);
        ridingSubButton.setBorder(new LineBorder(Color.ORANGE, 3));
        ridingSubButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                ridingSubButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                ridingSubButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                new RidingSubscription(id);
                frame.setVisible(false);
            }
        });

        timingButton.setFont(new Font("Arial", Font.BOLD, 25));
        timingButton.setForeground(Color.BLACK);
        timingButton.setIcon(buttonBackground);
        timingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        timingButton.setVerticalTextPosition(SwingConstants.CENTER);
        timingButton.setBorder(new LineBorder(Color.ORANGE, 3));
        timingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                timingButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                timingButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                new MainPortal();
                frame.setVisible(false);
            }
        });

        historyButton.setFont(new Font("Arial", Font.BOLD, 25));
        historyButton.setForeground(Color.BLACK);
        historyButton.setIcon(buttonBackground);
        historyButton.setHorizontalTextPosition(SwingConstants.CENTER);
        historyButton.setVerticalTextPosition(SwingConstants.CENTER);
        historyButton.setBorder(new LineBorder(Color.ORANGE, 3));
        historyButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                historyButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                historyButton.setSize(260, 90);
            }
            public void mouseClicked(MouseEvent e){
                history2.id=id;
                history2.launch();
                frame.setVisible(false);
            }
        });

        frame.add(nustLogo);
        frame.add(membershipLabel);
        frame.add(subcriptionLabel);
        frame.add(swimmingButton);
        frame.add(gymButton);
        frame.add(ridingButton);
        frame.add(swimmingSubButton);
        frame.add(gymSubButton);
        frame.add(ridingSubButton);
        frame.add(timingButton);
        frame.add(historyButton);
        frame.add(dashboardBackground);
        frame.setVisible(true);
    }
}

class RidingMembership
{
    private JFrame frame;
    private JLabel menuBackground;
    private JLabel nustLogo;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel startSubciptionsLabel; 
    @SuppressWarnings("unused")
    private int id;

    public RidingMembership(int id)
    {
        frame = new JFrame("MAIN MENU");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.id=id;
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
                                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO project.ridingmembership (paymentstatus,Receipt,Users_userId) VALUES (0,?,"+id+")");
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

class SwimmingMembership
{
    private JFrame frame;
    private JLabel menuBackground;
    private JLabel nustLogo;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel startSubciptionsLabel; 
    @SuppressWarnings("unused")
    private int id;

    public SwimmingMembership(int id)
    {
        frame = new JFrame("MAIN MENU");
        this.id=id;
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon background = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\bg.jpg");
        ImageIcon nustIcon = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\logo.png");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");

        menuBackground = new JLabel(background);
        nustLogo = new JLabel(nustIcon);

        startSubciptionsLabel = new JLabel("START/RENEW YOUR MEMBERSHIP BY UPLOADING THE RECEIPT");
        startSubciptionsLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 30)); 
        startSubciptionsLabel.setForeground(Color.ORANGE);
        startSubciptionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
                                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO project.swimmingmembership (paymentstatus,Receipt,Users_userId) VALUES (0,?,"+id+")");
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

public class app{
    public static void main(String[] args)
    {
        new MainPortal();
    }
}

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

class SwimmingSubscription {
    private JFrame frame;
    private JPanel panel;
    private JButton backButton;
    @SuppressWarnings("unused")
    private int id;

    public SwimmingSubscription(int id) {
        this.id=id;
        frame = new JFrame("SWIMMING SOPS");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        ImageIcon icon1 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0021.jpg");
        ImageIcon icon2 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0022.jpg");
        ImageIcon icon3 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0019.jpg"); 
        ImageIcon icon4 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0025.jpg");
        ImageIcon icon5 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0024.jpg");
        ImageIcon icon6 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0026.jpg");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");
        
        JLabel label1 = new JLabel(icon1);
        JLabel label2 = new JLabel(icon2);
        JLabel label3 = new JLabel(icon3);
        JLabel label4 = new JLabel(icon4);
        JLabel label5 = new JLabel(icon5);
        JLabel label6 = new JLabel(icon6);
        
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
                new Dashboard(id);
                frame.setVisible(false);
            }
        });

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(label6);
        panel.add(backButton);

        JScrollPane scrollPane = new JScrollPane(panel);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.ORANGE);

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setBackground(Color.ORANGE);

        frame.add(scrollPane);

        frame.setVisible(true);
    }
}

class GymSubscription {
    private JFrame frame;
    private JPanel panel;
    private JButton backButton;
    @SuppressWarnings("unused")
    private int id;

    public GymSubscription(int id) {
        this.id=id;
        frame = new JFrame("GYM SOPS");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        ImageIcon icon1 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0015.jpg");
        ImageIcon icon2 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0017.jpg");
        ImageIcon icon3 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0012.jpg"); 
        ImageIcon icon4 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0013.jpg");
        ImageIcon icon5 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0011.jpg");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");
        
        JLabel label1 = new JLabel(icon1);
        JLabel label2 = new JLabel(icon2);
        JLabel label3 = new JLabel(icon3);
        JLabel label4 = new JLabel(icon4);
        JLabel label5 = new JLabel(icon5);

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
                new Dashboard(id);
                frame.setVisible(false);
            }
        });

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(backButton);

        JScrollPane scrollPane = new JScrollPane(panel);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.ORANGE);

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setBackground(Color.ORANGE);

        frame.add(scrollPane);

        frame.setVisible(true);
    }
}

class RidingSubscription {
    private JFrame frame;
    private JPanel panel;
    private JButton backButton;
    @SuppressWarnings("unused")
    private int id;

    public RidingSubscription(int id) {
        frame = new JFrame("RIDING SOPS");
        this.id = id;
        frame.setSize(1000, 1000);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        ImageIcon icon1 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0010.jpg");
        ImageIcon icon2 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0020.jpg");
        ImageIcon icon3 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0018.jpg"); 
        ImageIcon icon4 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0014.jpg");
        ImageIcon icon5 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0016.jpg");
        ImageIcon icon6 = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\IMG-20240520-WA0023.jpg");
        ImageIcon buttonBackground = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\button.jpg");
        
        JLabel label1 = new JLabel(icon1);
        JLabel label2 = new JLabel(icon2);
        JLabel label3 = new JLabel(icon3);
        JLabel label4 = new JLabel(icon4);
        JLabel label5 = new JLabel(icon5);
        JLabel label6 = new JLabel(icon6);

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
                new Dashboard(id);
                frame.setVisible(false);
            }
        });

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(label6);
        panel.add(backButton);

        JScrollPane scrollPane = new JScrollPane(panel);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.ORANGE);

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setBackground(Color.ORANGE);

        frame.add(scrollPane);

        frame.setVisible(true);
    }
}
