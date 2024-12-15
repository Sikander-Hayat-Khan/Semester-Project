package NSCMS.Database;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class DatabaseMainPortal extends JFrame {
    private JPanel mainPanel;
    private GradientButton swimmingButton, ridingButton, gymButton;
    private JLabel nustLogo;

    public DatabaseMainPortal() {
        setTitle("DATABASE MAIN MENU");
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
        nustLogo = new JLabel(new ImageIcon(scaledImage));
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
        swimmingButton = new GradientButton("Swimming Database");
        swimmingButton.addActionListener(e -> openSwimmingDatabase());
        mainPanel.add(swimmingButton, gbc);

        // Riding Admin Button
        gbc.gridx = 1;
        ridingButton = new GradientButton("Riding Database");
        ridingButton.addActionListener(e -> openRidingDatabase());
        mainPanel.add(ridingButton, gbc);

        // Gym Admin Button
        gbc.gridx = 2;
        gymButton = new GradientButton("Gym Database");
        gymButton.addActionListener(e -> openGymDatabase());
        mainPanel.add(gymButton, gbc);

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

    private void openSwimmingDatabase() {
        try {
            new DB1();
            this.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openRidingDatabase() {
        try {
            new DB2();
            this.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openGymDatabase() {
        try {
            new DB3();
            this.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            g2d.fillRect(0, 0, width, height);

            g2d.dispose();

            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        new DatabaseMainPortal();
    }
}

