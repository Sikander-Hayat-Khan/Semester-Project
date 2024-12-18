package NSCMS.Database;

import java.sql.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DB2 {
    public static ArrayList<Integer> userIDs;
    public static ArrayList<String> names;
    public static ArrayList<String> types;
    public static ArrayList<BufferedImage> receipts;
    public static ArrayList<Boolean> status;
    final static String DB_NAME = "project";
    final static String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    final static String USERNAME = "root";
    final static String PASSWORD = "Hashim#00789";

    public DB2 () throws IOException {
        userIDs = new ArrayList<>();
        names = new ArrayList<>();
        types = new ArrayList<>();
        receipts = new ArrayList<>();
        status = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "SELECT userId, name, Usertype, Receipt, paymentstatus FROM riding_membership INNER JOIN users ON users.userId = riding_membership.Users_userId;";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            java.sql.Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            try {
                ResultSetMetaData metaData = rs.getMetaData();

                while (rs.next()) {
                    userIDs.add(rs.getInt(1));
                    names.add(rs.getString(2));
                    types.add(rs.getString(3));

                    Blob blob = rs.getBlob(4);
                    InputStream inputStream = blob.getBinaryStream();
                    byte[] imageData = new byte[inputStream.available()];
                    inputStream.read(imageData);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
                    receipts.add(image);

                    status.add(rs.getBoolean(5));
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
            DB2.AdminGUI fk = new AdminGUI();
        });
    }


    private static class AdminGUI extends JFrame {
        private JList<JPanel> panelList;
        private DefaultListModel<JPanel> listModel;

        public AdminGUI() {
            setTitle("Riding Club Admin Dashboard");
            setExtendedState(MAXIMIZED_BOTH);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            final ImageIcon bg = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\CreatedBackground.png");
//            final ImageIcon logo = new ImageIcon("C:/Users/LENOVO/Pictures/logo.png");
//            final ImageIcon button = new ImageIcon("C:/Users/LENOVO/Pictures/button.jpg");
            final int TABPADDING = 30;
            final Font HEADERFONT = new Font("Gill Sans MT", Font.BOLD, 40);
            final Font DATAFONT = new Font("Gill Sans MT", Font.PLAIN, 30);
            final Color DARKER_GRAY = new Color(40, 40, 40);

            // Create a panel for the background
            JPanel backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new BorderLayout());
            setContentPane(backgroundPanel);

            JPanel headers = new JPanel();
            headers.setLayout(new BoxLayout(headers, BoxLayout.X_AXIS));
            headers.setOpaque(false);
            headers.setPreferredSize(new Dimension(getPreferredSize().width, 100));
            JLabel h1 = new JLabel("User ID");
            JLabel h2 = new JLabel("Name");
            JLabel h3 = new JLabel("Type");
            JLabel h4 = new JLabel("Receipt");
            JLabel h5 = new JLabel("Status");

            h1.setFont(HEADERFONT);
            h2.setFont(HEADERFONT);
            h3.setFont(HEADERFONT);
            h4.setFont(HEADERFONT);
            h5.setFont(HEADERFONT);

            h1.setForeground(Color.YELLOW);
            h2.setForeground(Color.YELLOW);
            h3.setForeground(Color.YELLOW);
            h4.setForeground(Color.YELLOW);
            h5.setForeground(Color.YELLOW);

            headers.add(Box.createRigidArea(new Dimension(100, 0)));
            headers.add(h1);
            headers.add(Box.createRigidArea(new Dimension(150, 0)));
            headers.add(h2);
            headers.add(Box.createRigidArea(new Dimension(180, 0)));
            headers.add(h3);
            headers.add(Box.createRigidArea(new Dimension(150, 0)));
            headers.add(h4);
            headers.add(Box.createRigidArea(new Dimension(150, 0)));
            headers.add(h5);

            backgroundPanel.add(headers, BorderLayout.NORTH);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setOpaque(false);

            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            backgroundPanel.add(scrollPane, BorderLayout.CENTER);

            for (int i = 1; i <= DB2.userIDs.size(); i++) {
                final int index = i;

                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setPreferredSize(new Dimension(1000, 200));
                Border border = BorderFactory.createLineBorder(Color.GRAY);
                panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(TABPADDING, TABPADDING, TABPADDING, TABPADDING), border));
                panel.setBackground(new Color(DARKER_GRAY.getRed(), DARKER_GRAY.getGreen(), DARKER_GRAY.getBlue(), 200));

                GridBagConstraints gbc = new GridBagConstraints();

                JLabel l1 = new JLabel(DB2.userIDs.get(i - 1) + "");
                JLabel l2 = new JLabel(DB2.names.get(i - 1));
                JLabel l3 = new JLabel((DB2.types.get(i - 1).equals("S")) ? "Student": "Faculty");

                JButton approve = new JButton((DB2.status.get(i - 1)) ? "Refuse" : "Approve");
                approve.setEnabled(true);
                approve.setBackground((DB2.status.get(i - 1)) ? Color.RED: Color.GREEN);
                approve.setFocusable(false);

                Image receipt = DB2.receipts.get(i-1);
                JLabel l4;
                if (receipt != null) {
                    Image resizedReceipt = receipt.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    l4 = new JLabel(new ImageIcon(resizedReceipt));
                    l4.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JFrame receiptFrame = new JFrame("Receipt for User ID " + DB2.userIDs.get(index-1));
                            receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            Image displayReceipt;
                            double ratio = 1;
                            double img_width = ((BufferedImage)(receipt)).getWidth();
                            double img_height = ((BufferedImage)(receipt)).getHeight();
                            if (img_width > img_height) {
                                ratio = img_height / img_width;
                                receiptFrame.setSize(800, (int)(800.0*ratio));
                                displayReceipt = receipt.getScaledInstance(800, (int)(800.0*ratio), Image.SCALE_SMOOTH);
                            }
                            else if (img_width < img_height) {
                                ratio = img_width / img_height;
                                receiptFrame.setSize((int)(800.0*ratio), 800);
                                displayReceipt = receipt.getScaledInstance((int)(800.0*ratio), 800, Image.SCALE_SMOOTH);
                            }
                            else {
                                receiptFrame.setSize(800, 800);
                                displayReceipt = receipt.getScaledInstance((int)(800.0*ratio), 800, Image.SCALE_SMOOTH);
                            }
                            JLabel receiptLabel = new JLabel(new ImageIcon());
                            receiptFrame.add(new JScrollPane(receiptLabel));

                            receiptFrame.setVisible(true);
                        }
                    });
                } else l4 = new JLabel("N/A");

                l1.setBounds(140, 80, 80, 50);
                l1.setFont(new Font("calibri", Font.BOLD, 25));
                l1.setForeground(Color.WHITE);
                l2.setBounds(340, 80, 300, 50);
                l2.setFont(new Font("calibri", Font.BOLD, 25));
                l2.setForeground(Color.WHITE);
                l3.setBounds(690, 80, 100, 50);
                l3.setFont(new Font("calibri", Font.BOLD, 25));
                l3.setForeground(Color.WHITE);
                l4.setBounds(950, 50, 100, 100);
                l4.setFont(new Font("calibri", Font.BOLD, 25));
                l4.setForeground(Color.WHITE);
                approve.setFont(DATAFONT);

                l1.setForeground(Color.WHITE);
                l2.setForeground(Color.WHITE);
                l3.setForeground(Color.WHITE);
                l4.setForeground(Color.WHITE);
                approve.setForeground(Color.BLACK);

                l1.setBounds(140, 80, 80, 50);
                l2.setBounds(340, 80, 300, 50);
                l3.setBounds(690, 80, 100, 50);
                l4.setBounds(950, 50, 100, 100);
                approve.setBounds(1210, 80, 150, 50);

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 120, 0, 0);
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(l1, gbc);
                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(l2, gbc);

                gbc.gridx = 2;
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(l3, gbc);

                gbc.gridx = 3;
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(l4, gbc);

                gbc.gridx = 4;
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(approve, gbc);

                mainPanel.add(panel);

                approve.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        String sql = "";
                        if (approve.getText().equals("Approve")) {
                            approve.setText("Refuse");
                            approve.setBackground(Color.RED);
                            sql = "UPDATE riding_membership SET paymentstatus = 1 WHERE Users_userId = " + DB2.userIDs.get(index - 1);
                        } else {
                            approve.setText("Approve");
                            approve.setBackground(Color.GREEN);
                            sql = "UPDATE riding_membership SET paymentstatus = 0 WHERE Users_userId = " + DB2.userIDs.get(index - 1);
                        }
                        l4.setBounds(950, 50, 100, 100);
                        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                            try {
                                java.sql.Statement statement = connection.createStatement();
                                statement.executeUpdate(sql);
                                statement.close();
                                DB2.status.set(index - 1, !DB2.status.get(index - 1));
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }

            ImageIcon buttonBackground = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\ButtonBackground.png");

            // Back button
            JPanel bottomPanel = new JPanel();
            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Arial", Font.BOLD, 25));
            backButton.setForeground(Color.BLACK);
            backButton.setIcon(buttonBackground);
            backButton.setHorizontalTextPosition(SwingConstants.CENTER);
            backButton.setVerticalTextPosition(SwingConstants.CENTER);
            backButton.setBorder(new LineBorder(Color.ORANGE, 3));
            backButton.addActionListener(e -> {
                new DatabaseMainPortal();
                dispose(); // Close the current window
            });

            bottomPanel.add(backButton);
            backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

            setVisible(true);
        }
    }
}