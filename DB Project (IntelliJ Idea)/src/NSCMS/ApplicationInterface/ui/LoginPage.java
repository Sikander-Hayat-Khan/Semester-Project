package NSCMS.ApplicationInterface.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

public class LoginPage extends JFrame {
    private final JPanel mainPanel;
    private final JPanel loginPanel;
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
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the window to maximized state
        setMinimumSize(new Dimension(600, 400)); // Set a minimum size to maintain layout

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(53, 53, 53));

        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 255, 255));
//        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(182, 126, 148), 1));
        loginPanel.setBorder(new RoundedBorder(100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // CMS ID Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(new JLabel("CMS ID:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmsIdField = new JTextField(15);
        loginPanel.add(cmsIdField, gbc);

        // Password Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(new JLabel("PASSWORD:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        GradientButton loginButton = new GradientButton("LOGIN");
        loginButton.addActionListener(e -> performLogin());
        loginPanel.add(loginButton, gbc);

        // Back Button
        gbc.gridy = 3;
        GradientButton backButton = new GradientButton("BACK");
        backButton.addActionListener(e -> goBack());
        loginPanel.add(backButton, gbc);

        // Status Label
        gbc.gridy = 4;
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        loginPanel.add(statusLabel, gbc);

        // Add login panel to main panel
        mainPanel.add(loginPanel);

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
                // Recalculate the size and position of the login panel
                Dimension size = getContentPane().getSize();
                int loginPanelWidth = Math.min(400, size.width - 40);
                int loginPanelHeight = Math.min(300, size.height - 40);
                loginPanel.setPreferredSize(new Dimension(loginPanelWidth, loginPanelHeight));
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

        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 14));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
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

            // Create gradient paint
            GradientPaint gp = new GradientPaint(
                    0, 0, isHovered ? new Color(100, 180, 255) : new Color(70, 130, 180),
                    width, height, isHovered ? new Color(70, 130, 180) : new Color(100, 180, 255)
            );

            g2d.setPaint(gp);
            g2d.fill(new RoundRectangle2D.Float(0, 0, width, height, 10, 10));

            g2d.dispose();

            super.paintComponent(g);
        }
    }
}