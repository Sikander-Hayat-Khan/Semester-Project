package NSCMS.ApplicationInterface.ui;

import NSCMS.Admin.History2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Dashboard {
    private JFrame frame;
    private JLabel dashboardBackground;
    private JLabel nustLogo;
    private JButton swimmingButton, gymButton, ridingButton, swimmingSubButton, gymSubButton, ridingSubButton, timingButton, historyButton;
    private JLabel membershipLabel, subcriptionLabel;
    private int id;

    public Dashboard(int id) {
        this.id = id;
        frame = new JFrame("NUST SPORTS MANAGEMENT SYSTEM");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);

        ImageIcon background = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\CreatedBackground.png");
        ImageIcon nustIcon = new ImageIcon("C:\\Users\\LENOVO\\Pictures\\logo.png");
        ImageIcon buttonBackground = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\ButtonBackground.png");

        dashboardBackground = new JLabel(background);
        nustLogo = new JLabel(nustIcon);

        membershipLabel = createLabel("GET A MEMBERSHIP", 50);
        subcriptionLabel = createLabel("SUBSCRIPTIONS DETAILS", 35);

        swimmingButton = createButton("SWIMMING", buttonBackground);
        gymButton = createButton("GYM", buttonBackground);
        ridingButton = createButton("HORSE RIDING", buttonBackground);
        timingButton = createButton("SIGN OUT", buttonBackground);
        historyButton = createButton("ACCESS HISTORY", buttonBackground);

        swimmingSubButton = createButton("SWIMMING SUBSCRIPTION", buttonBackground, 260);
        gymSubButton = createButton("GYM SUBSCRIPTION", buttonBackground, 260);
        ridingSubButton = createButton("HORSE-RIDING SUBSCRIPTION", buttonBackground, 260);

        setupButtonListeners();

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Dynamically scale the background image to fit the frame size
                Image scaledImage = background.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
                dashboardBackground.setIcon(new ImageIcon(scaledImage));
                setComponentBounds(); // Update positions of other components
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

    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Bernard MT Condensed", Font.BOLD, fontSize));
        label.setForeground(Color.ORANGE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JButton createButton(String text, ImageIcon icon) {

        return createButton(text, icon, 260); // Default button width
    }

    private JButton createButton(String text, ImageIcon icon, int width) {
        // Split the text into two lines using HTML
        String formattedText=text;
        if (text.contains("SUBSCRIPTION")) {
            formattedText = "<html>" + text.replace(" ", "<br>") + "</html>";
        }
        JButton button = new JButton(formattedText);
        button.setFont(new Font("Arial", Font.BOLD, 20)); // Font size for better readability
        button.setForeground(Color.BLACK);
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(width, 90));
        button.setBorder(new LineBorder(Color.ORANGE, 3));
        return button;
    }

    private void setupButtonListeners() {
        swimmingButton.addMouseListener(createButtonMouseListener(() -> new SwimmingMembership(id)));
        gymButton.addMouseListener(createButtonMouseListener(() -> new GymMembership(id)));
        ridingButton.addMouseListener(createButtonMouseListener(() -> new RidingMembership(id)));
        swimmingSubButton.addMouseListener(createButtonMouseListener(() -> new SwimmingSubscription(id)));
        gymSubButton.addMouseListener(createButtonMouseListener(() -> new GymSubscription(id)));
        ridingSubButton.addMouseListener(createButtonMouseListener(() -> new RidingSubscription(id)));
        timingButton.addMouseListener(createButtonMouseListener(() -> {
            new MainPortal();
            frame.setVisible(false);
        }));
        historyButton.addMouseListener(createButtonMouseListener(() -> {
            History2.id = id;
            History2.launch();
            frame.setVisible(false);
        }));
    }

    private MouseAdapter createButtonMouseListener(Runnable action) {
        return new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getSource()).setSize(270, 100);
            }
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getSource()).setSize(260, 100);
            }
            public void mouseClicked(MouseEvent e) {
                action.run();
                frame.setVisible(false);
            }
        };
    }

    private void setComponentBounds() {
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

    public static void main(String[] args) {
        new Dashboard(1);
    }
}