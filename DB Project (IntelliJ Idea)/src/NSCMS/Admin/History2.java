package NSCMS.Admin;

import NSCMS.ApplicationInterface.ui.Dashboard;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class History2 {
    public static ArrayList<Timestamp> time;
    public static ArrayList<String> facility;
    public ArrayList<Blob> receipts;
    public ArrayList<JCheckBox> status;
    public static int id;

    public static void launch() {
        time = new ArrayList<>();
        facility = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        final String DB_NAME = "project";
        final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
        final String USERNAME = "root";
        final String PASSWORD = "Hashim#00789";
        String sql = "SELECT FacilityUsed,time FROM project.entries where Users_userId = " + id;

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            java.sql.Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            try {
                @SuppressWarnings("unused")
                ResultSetMetaData metaData = rs.getMetaData();

                while (rs.next()) {
                    facility.add(rs.getString("FacilityUsed"));
                    time.add(rs.getTimestamp("time"));
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                statement.close();
                rs.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new admin2();
        });
    }
}

class admin2 extends JFrame {
    private JList<JPanel> panelList;
    private DefaultListModel<JPanel> listModel;
    private JLabel backgroundLabel;
    private ImageIcon backgroundImage = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\AccessHistoryBG2.png");

    public admin2() {
        setTitle("Admin Dashboard");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a layered pane to hold background and content
        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        // Set up background
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Set up content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(contentPanel, JLayeredPane.PALETTE_LAYER);

        listModel = new DefaultListModel<>();
        panelList = new JList<>(listModel);
        panelList.setCellRenderer(new PanelListCellRenderer());
        panelList.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(panelList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        final int PADDING = 10;
        // Add panels to list model
        for (int i = 1; i <= History2.facility.size(); i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setPreferredSize(new Dimension(getWidth() - 100, 100));
            Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING), border));
            panel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestampString = formatter.format(History2.time.get(i - 1));

            JLabel l1 = new JLabel(timestampString);
            JLabel l2 = new JLabel(History2.facility.get(i - 1));
            l1.setFont(new Font("Arial", Font.BOLD, 16));
            l2.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(Box.createRigidArea(new Dimension(50, 0)));
            panel.add(l1);
            panel.add(Box.createRigidArea(new Dimension(50, 0)));
            panel.add(l2);
            panel.add(Box.createHorizontalGlue());
            listModel.addElement(panel);
        }
        ImageIcon buttonBackground = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\ButtonBackground.png");
        // Create and set up the back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 25));
        backButton.setForeground(Color.BLACK);
        backButton.setIcon(buttonBackground);
        backButton.setHorizontalTextPosition(SwingConstants.CENTER);
        backButton.setVerticalTextPosition(SwingConstants.CENTER);
        backButton.setBorder(new LineBorder(Color.ORANGE, 3));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                new Dashboard(History2.id); // Open the main frame
            }
        });
        layeredPane.add(backButton, JLayeredPane.DRAG_LAYER); // Add to a higher layer

        // Ensure the background image resizes with the frame
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
                contentPanel.setBounds(0, 0, getWidth(), getHeight());
                backButton.setBounds(20, getHeight() - 80, 100, 40); // Update button position
            }
        });

        setVisible(true);
    }

    private class PanelListCellRenderer implements ListCellRenderer<JPanel> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
            return value;
        }
    }
}

