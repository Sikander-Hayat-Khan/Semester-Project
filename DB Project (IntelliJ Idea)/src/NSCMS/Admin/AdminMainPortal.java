package NSCMS.Admin;

import NSCMS.ApplicationInterface.Main.App;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class AdminMainPortal extends JFrame {
    private final JPanel mainPanel;

    public AdminMainPortal() {
        setTitle("ADMINISTRATION MAIN MENU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));

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

        // NUST Logo
        ImageIcon nustIcon = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\NUST_Logo-removebg-preview.png");
        Image scaledImage = nustIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel nustLogo = new JLabel(new ImageIcon(scaledImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(nustLogo, gbc);

        // Reset gridwidth for buttons
        gbc.gridwidth = 1;

        // Swimming Admin Button
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        GradientButton swimmingAdminButton = new GradientButton("Swimming Admin");
        swimmingAdminButton.addActionListener(e -> openSwimmingAdminLogin());
        mainPanel.add(swimmingAdminButton, gbc);

        // Riding Admin Button
        gbc.gridx = 1;
        GradientButton ridingAdminButton = new GradientButton("Riding Admin");
        ridingAdminButton.addActionListener(e -> openRidingAdminLogin());
        mainPanel.add(ridingAdminButton, gbc);

        // Gym Admin Button
        gbc.gridx = 2;
        GradientButton gymAdminButton = new GradientButton("Gym Admin");
        gymAdminButton.addActionListener(e -> openGymAdminLogin());
        mainPanel.add(gymAdminButton, gbc);

        // Back Button
        gbc.gridy = 2; // Move to the next row
        gbc.gridx = 1; // Center column
        gbc.gridwidth = 1; // Single column
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
        GradientButton backButton = new GradientButton("Back");
        backButton.addActionListener(e -> {
            new App();
            dispose(); // Close the current frame
        });
        mainPanel.add(backButton, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Add ComponentListener to handle window resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mainPanel.revalidate();
            }
        });

        setVisible(true);
    }

    private void openSwimmingAdminLogin() {
        new sentryLoginPage();
        this.dispose();
    }

    private void openRidingAdminLogin() {
        new rentryLoginPage();
        this.dispose();
    }

    private void openGymAdminLogin() {
        new gentryLoginPage();
        this.dispose();
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

    public static void main(String[] args) {
        new AdminMainPortal();
    }
}
