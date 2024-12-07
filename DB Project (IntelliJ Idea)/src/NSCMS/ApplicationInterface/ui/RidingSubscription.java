package NSCMS.ApplicationInterface.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.net.URI;

class RidingSubscription {
    private JFrame frame;
    private JPanel panel;
    private JButton backButton, websiteForm;
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

        ImageIcon icon1 = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\Docs\\Saddle_1_new.png");
        ImageIcon icon2 = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\Docs\\Saddle_2_new.png");
        ImageIcon icon3 = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\Docs\\Saddle_3_new.png");
        ImageIcon icon4 = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\Docs\\Saddle_4_new.png");
        ImageIcon icon5 = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\Docs\\Saddle_5_new.png");
        ImageIcon buttonBackground = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\ButtonBackground.png");

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

        websiteForm = new JButton("Form On Website");
        websiteForm.setFont(new Font("Arial", Font.BOLD, 25));
        websiteForm.setForeground(Color.BLACK);
        websiteForm.setIcon(buttonBackground);
        websiteForm.setHorizontalTextPosition(SwingConstants.CENTER);
        websiteForm.setVerticalTextPosition(SwingConstants.CENTER);
        websiteForm.setBorder(new LineBorder(Color.ORANGE, 3));
        websiteForm.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                websiteForm.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                websiteForm.setSize(260, 90);
            }

            public void mouseClicked(MouseEvent e)
            {
                websiteForm.addActionListener(f -> openURL("https://nust.edu.pk/wp-content/uploads/2021/06/Saddle-Club-Form-and-SOPs.pdf#page=6"));
            }
        });

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(backButton);
        panel.add(websiteForm);

        JScrollPane scrollPane = new JScrollPane(panel);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.ORANGE);

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setBackground(Color.ORANGE);

        frame.add(scrollPane);

        frame.setVisible(true);
    }
    private void openURL(String url) {
        try {
            URI uri = new URI(url);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            } else {
                JOptionPane.showMessageDialog(null, "Desktop operations aren't supported.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to open the link.");
        }
    }
}