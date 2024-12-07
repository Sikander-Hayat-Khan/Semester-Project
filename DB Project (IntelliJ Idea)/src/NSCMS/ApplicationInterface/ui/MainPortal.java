package NSCMS.ApplicationInterface.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class MainPortal extends JFrame {
    private JPanel mainPanel, contentPanel;
    private GradientButton loginButton, signupButton;
    private JLabel nustLogo;

    public MainPortal() {
        setTitle("MAIN MENU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(53, 53, 53));

        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
//        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(136, 0, 0), 1));
        contentPanel.setBorder(new RoundedBorder(100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // NUST Logo
        ImageIcon nustIcon = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB project\\Assets\\NUST_Logo-removebg-preview.png");
        Image scaledImage = nustIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        nustLogo = new JLabel(new ImageIcon(scaledImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(nustLogo, gbc);

        // Login Button
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginButton = new GradientButton("LOGIN");
        loginButton.addActionListener(e -> openLoginPage());
        contentPanel.add(loginButton, gbc);

        // Signup Button
        gbc.gridx = 1;
        signupButton = new GradientButton("SIGN UP");
        signupButton.addActionListener(e -> openSignupPage());
        contentPanel.add(signupButton, gbc);

        // Add content panel to main panel
        mainPanel.add(contentPanel);

        // Add main panel to frame
        add(mainPanel);

        // Add ComponentListener to handle window resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Recalculate the size and position of the content panel
                Dimension size = getContentPane().getSize();
                int contentPanelWidth = Math.min(600, size.width - 40);
                int contentPanelHeight = Math.min(400, size.height - 40);
                contentPanel.setPreferredSize(new Dimension(contentPanelWidth, contentPanelHeight));
                mainPanel.revalidate();
            }
        });

        setVisible(true);
    }

    private void openLoginPage() {
        new LoginPage();
        this.dispose();
    }

    private void openSignupPage() {
        new SignupPage();
        this.dispose();
    }

    private static class GradientButton extends JButton {
        private boolean isHovered = false;

        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 16));
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