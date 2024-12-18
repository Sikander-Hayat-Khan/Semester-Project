package NSCMS.ApplicationInterface.ui;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class LoginPage extends JFrame {
    private final JPanel mainPanel;
    private final JTextField cmsIdField;
    private final JPasswordField passwordField;
    private final JLabel statusLabel;

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Hashim#00789";

    public LoginPage() {
        setTitle("LOGIN WINDOW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 400));

        mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = ImageIO.read(new File("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\CreatedBackground.png"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                    setBackground(new Color(53, 53, 53)); // Fallback to grey if image fails to load
                }
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Logo
        ImageIcon logoIcon = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\NUST_Logo-removebg-preview.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(logoLabel, gbc);

        // Reset gridwidth and update gridy for other components
        gbc.gridwidth = 1;
        gbc.gridy++;

        // CMS ID Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel cmsIdLabel = new JLabel("CMS ID:");
        cmsIdLabel.setFont(new Font("calibri", Font.BOLD, 20));
        cmsIdLabel.setForeground(Color.WHITE);
        mainPanel.add(cmsIdLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmsIdField = new JTextField(25);
        cmsIdField.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(cmsIdField, gbc);

        // Password Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setFont(new Font("calibri", Font.BOLD, 20));
        passwordLabel.setForeground(Color.WHITE);
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(25);
        passwordField.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        GradientButton loginButton = new GradientButton("LOGIN");
        loginButton.addActionListener(e -> performLogin());
        mainPanel.add(loginButton, gbc);

        // Back Button
        gbc.gridy = 4;
        GradientButton backButton = new GradientButton("BACK");
        backButton.addActionListener(e -> goBack());
        mainPanel.add(backButton, gbc);

        // Status Label
        gbc.gridy = 5;
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        mainPanel.add(statusLabel, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load JDBC driver.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add ComponentListener to handle window resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mainPanel.revalidate();
            }
        });

        setVisible(true);
    }

    private void performLogin() {
        String cmsId = cmsIdField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (cmsId.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both CMS ID and password.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT userId FROM users WHERE userId = ? AND password = MD5(?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(cmsId));
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("userId");
                        JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        openDashboard(userId);
                    } else {
                        statusLabel.setText("Invalid CMS ID or password.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error occurred.");
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid CMS ID format.");
        }
    }

    private void openDashboard(int userId) {
        // TODO: Implement dashboard opening logic
        System.out.println("Opening dashboard for user ID: " + userId);
        new Dashboard(userId);
        this.dispose(); // Close the login window
    }

    private void goBack() {
        // TODO: Implement back functionality
        System.out.println("Going back to main portal");
        new MainPortal();
        this.dispose(); // Close the login window
    }

    private class GradientButton extends JButton {
        private boolean isHovered = false;
        private Image backgroundImage;
        private final int NORMAL_WIDTH = 250;
        private final int NORMAL_HEIGHT = 50;
        private final int HOVER_INCREASE = 10;

        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(true);
            setOpaque(false);
            setForeground(Color.BLACK);
            setFont(new Font("Arial", Font.BOLD, 25));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Set yellow border
            setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));

            // Load the background image
            try {
                backgroundImage = ImageIO.read(new File("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\ButtonBackground.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    setSize(NORMAL_WIDTH + HOVER_INCREASE, NORMAL_HEIGHT + HOVER_INCREASE);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    setSize(NORMAL_WIDTH, NORMAL_HEIGHT);
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Draw background image if available, otherwise use a semi-transparent black background
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, width, height, null);
            } else {
                // Semi-transparent dark background
                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRect(0, 0, width, height);
            }

            // Add a subtle hover effect
            if (isHovered) {
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillRect(0, 0, width, height);
            }

            g2d.dispose();

            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            // Set the button's preferred size
            return new Dimension(NORMAL_WIDTH, NORMAL_HEIGHT);
        }

        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new LoginPage());
//    }
}

