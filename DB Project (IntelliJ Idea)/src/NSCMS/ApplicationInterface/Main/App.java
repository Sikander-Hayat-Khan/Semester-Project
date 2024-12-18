package NSCMS.ApplicationInterface.Main;

import NSCMS.Admin.AdminMainPortal;
import NSCMS.ApplicationInterface.ui.MainPortal;
import NSCMS.Database.DatabaseMainPortal;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

class SplashScreen extends JFrame {
    private JProgressBar progressBar;

    public SplashScreen() {
        setTitle("Loading...");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // Add an icon above the progress bar
        try {
            ImageIcon gifIcon = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\waiting-7579_256.gif");
            JLabel iconLabel = new JLabel(gifIcon);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(iconLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add progress bar
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(70, 130, 180));
        add(progressBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateProgress(int value) {
        progressBar.setValue(value);
    }
}

public class App extends JFrame {
    private JComboBox<String> categoryDropdown;
    private JButton navigateButton;
    private JLabel logoLabel;

    public App() {
        setTitle("Main Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Set background image
        try {
            final Image backgroundImage = ImageIO.read(new File("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\CreatedBackground.png"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create and add logo
        try {
            Image logoImage = ImageIO.read(new File("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\NUST_Logo-removebg-preview.png"));
            logoLabel = new JLabel(new ImageIcon(logoImage));
            add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create dropdown menu
        String[] categories = {"User", "Admin", "Database Manager"};
        categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setFont(new Font("Arial", Font.BOLD, 16));
        categoryDropdown.setBackground(new Color(230, 230, 250));
        categoryDropdown.setForeground(new Color(70, 130, 180));

        // Create stylish button
        navigateButton = new JButton("Access Portal");
        navigateButton.setFont(new Font("Arial", Font.BOLD, 18));
        navigateButton.setBackground(new Color(70, 130, 180));
        navigateButton.setForeground(Color.WHITE);
        navigateButton.setFocusPainted(false);
        navigateButton.setBorder(BorderFactory.createRaisedBevelBorder());

        // Add hover effect to button
        navigateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navigateButton.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                navigateButton.setBackground(new Color(70, 130, 180));
            }
        });

        // Add action listener to button
        navigateButton.addActionListener(e -> {
            String selectedCategory = (String) categoryDropdown.getSelectedItem();
            if ("User".equals(selectedCategory)) {
                new MainPortal();
                dispose();
            } else if ("Admin".equals(selectedCategory)) {
                new AdminMainPortal();
                dispose();
            } else if ("Database Manager".equals(selectedCategory)) {
                new DatabaseMainPortal();
                dispose();
            }
        });

        // Create panel for dropdown and button
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.add(categoryDropdown);
        controlPanel.add(navigateButton);

        // Add control panel to the bottom of the frame
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Set the FlatLaf look and feel
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Show splash screen
            SplashScreen splash = new SplashScreen();

            // Simulate loading progress
            new Timer(100, new ActionListener() {
                int progress = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (progress < 100) {
                        progress += 2;
                        splash.updateProgress(progress);
                    } else {
                        ((Timer) e.getSource()).stop();
                        splash.dispose(); // Close splash screen
                        new App();       // Launch main application
                    }
                }
            }).start();
        });
    }
}
