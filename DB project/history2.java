import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.ActionListener;
    
public class history2 {
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
                  //names.add(rs.getString(2));

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

    class eventhandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    }
}

class admin2 extends JFrame {
    private JList<JPanel> panelList;
    private DefaultListModel<JPanel> listModel;
    JLabel historybackground;
    ImageIcon background = new ImageIcon("E:\\Semester 2\\Databse Systems\\Project\\WhatsApp Image 2024-05-11 at 21.25.49_970c1e6c.jpg");

    

    public admin2() {
        historybackground = new JLabel(background);
        add(historybackground);
        setTitle("Admin Dashboard");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        listModel = new DefaultListModel<>();
        panelList = new JList<>(listModel);
        panelList.setCellRenderer(new PanelListCellRenderer());

        add(new JScrollPane(panelList), BorderLayout.CENTER);

        final int PADDING = 30;
        // Add panels to list model
        for (int i = 1; i <= history2.facility.size(); i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setPreferredSize(new Dimension(1000, 200));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING), border));
            panel.setBackground(Color.WHITE);
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String timestampString = formatter.format(new Date(history2.time.get(i-1).getTime()));

            JLabel l1 = new JLabel(timestampString);
            JLabel l2 = new JLabel(history2.facility.get(i - 1));
            l1.setFont(new Font("Arial", Font.PLAIN, 20));
            l2.setFont(new Font("Arial", Font.PLAIN, 20));
            panel.add(Box.createRigidArea(new Dimension(100, 0))); // Add space between l1 and l2
            panel.add(l1);
            panel.add(Box.createRigidArea(new Dimension(100, 0))); // Add space between l1 and l2
            panel.add(l2);
            panel.add(Box.createRigidArea(new Dimension(100, 0))); // Add space between l1 and l2
            listModel.addElement(panel);

        }
        JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Arial", Font.PLAIN, 20));
            backButton.setPreferredSize(new Dimension(100, 50));
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose(); // Close the current frame
                    new Dashboard(history2.id); // Open the main frame
                }
            });

            // Add back button to frame
            add(backButton, BorderLayout.SOUTH);

    }


    private class PanelListCellRenderer implements ListCellRenderer<JPanel> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
            return value;
        }
    }
}