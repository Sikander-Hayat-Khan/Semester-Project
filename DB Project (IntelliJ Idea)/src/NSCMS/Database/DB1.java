package NSCMS.Database;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DB1 {
    public static ArrayList<Integer> userIDs;
    public static ArrayList<String> names;
    public static ArrayList<String> types;
    public static ArrayList<BufferedImage> receipts;
    public static ArrayList<Boolean> status;
    final static String DB_NAME = "project";
    final static String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    final static String USERNAME = "root";
    final static String PASSWORD = "Hashim#00789";

    public DB1() throws IOException{
        userIDs = new ArrayList<>();
        names = new ArrayList<>();
        types = new ArrayList<>();
        receipts = new ArrayList<>();
        status = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver not loaded [" + e.getMessage() + "]");
        }

        String sql = "SELECT userId, name, Usertype, Receipt, paymentstatus FROM swimming_membership INNER JOIN users ON users.userId = swimming_membership.Users_userId;";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            java.sql.Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            try {
                ResultSetMetaData metaData = rs.getMetaData();
                BufferedImage image=null;
                while (rs.next()) {
                    userIDs.add(rs.getInt(1));
                    names.add(rs.getString(2));
                    types.add(rs.getString(3));

                    Blob blob = rs.getBlob(4);
                    if(blob != null){
                        InputStream inputStream = blob.getBinaryStream();
                        byte[] imageData = new byte[inputStream.available()];
                        inputStream.read(imageData);
                        image = ImageIO.read(new ByteArrayInputStream(imageData));

                    }
                    receipts.add(image);
                    status.add(rs.getBoolean(5));
                }

            } catch (SQLException e1) {
                e1.getMessage();
            } finally {
                statement.close();
                rs.close();
            }
        } catch (SQLException e1) {
            e1.getMessage();
        }

        SwingUtilities.invokeLater(() -> {
            DB1.AdminGUI fk = new AdminGUI();
        });
    }


    private static class AdminGUI extends JFrame {
        private JList<JPanel> panelList;
        private DefaultListModel<JPanel> listModel;

        public AdminGUI() {
            setTitle("Swimming Club Admin Dashboard");
            setExtendedState(MAXIMIZED_BOTH);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            final ImageIcon bg = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\CreatedBackground.png");
            final int TABPADDING = 30;
            final Font HEADERFONT = new Font("Gill Sans MT", Font.BOLD, 40);
            final Font DATAFONT = new Font("Gill Sans MT", Font.PLAIN, 30);
            final Color DARKER_GRAY = new Color(40, 40, 40);

            // Background panel
            JPanel backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new BorderLayout());
            setContentPane(backgroundPanel);

            // Headers
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

            // Main panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setOpaque(false);

            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            backgroundPanel.add(scrollPane, BorderLayout.CENTER);

            // Populate main panel
            for (int i = 1; i <= DB1.userIDs.size(); i++) {
                final int index = i;

                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setPreferredSize(new Dimension(1000, 200));
                Border border = BorderFactory.createLineBorder(Color.GRAY);
                panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(TABPADDING, TABPADDING, TABPADDING, TABPADDING), border));
                panel.setBackground(new Color(DARKER_GRAY.getRed(), DARKER_GRAY.getGreen(), DARKER_GRAY.getBlue(), 200));

                JLabel l1 = new JLabel(DB1.userIDs.get(i - 1) + "");
                JLabel l2 = new JLabel(DB1.names.get(i - 1));
                JLabel l3 = new JLabel((DB1.types.get(i - 1).equals("S")) ? "Student" : "Faculty");

                JButton approve = new JButton((DB1.status.get(i - 1)) ? "Refuse" : "Approve");
                approve.setEnabled(true);
                approve.setBackground((DB1.status.get(i - 1)) ? Color.RED : Color.GREEN);
                approve.setFocusable(false);

                Image receipt = DB1.receipts.get(i - 1);
                JLabel l4;
                if (receipt != null) {
                    Image resizedReceipt = receipt.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    l4 = new JLabel(new ImageIcon(resizedReceipt));
                    l4.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            BufferedImage receiptImage = DB1.receipts.get(index - 1);

                            if (receiptImage != null) {
                                // Calculate scaling dimensions
                                double imgWidth = receiptImage.getWidth();
                                double imgHeight = receiptImage.getHeight();
                                double maxDimension = 800.0; // Max size for the new frame
                                double widthRatio = imgWidth > imgHeight ? maxDimension / imgWidth : 1;
                                double heightRatio = imgHeight > imgWidth ? maxDimension / imgHeight : 1;
                                double scaleRatio = Math.min(widthRatio, heightRatio);

                                int newWidth = (int) (imgWidth * scaleRatio);
                                int newHeight = (int) (imgHeight * scaleRatio);

                                // Create scaled image
                                Image scaledImage = receiptImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                                // Create a new frame for displaying the receipt
                                JFrame receiptFrame = new JFrame("Receipt for User ID " + DB1.userIDs.get(index - 1));
                                receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                receiptFrame.setSize(newWidth, newHeight);

                                // Add the scaled receipt image to the frame
                                JLabel receiptLabel = new JLabel(new ImageIcon(scaledImage));
                                JScrollPane scrollPane = new JScrollPane(receiptLabel);
                                receiptFrame.add(scrollPane);

                                receiptFrame.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "No receipt available for this user.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                } else l4 = new JLabel("N/A");

                // Set bounds and add components
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
                approve.setBounds(1210, 80, 150, 50);

                panel.add(l1);
                panel.add(l2);
                panel.add(l3);
                panel.add(l4);
                panel.add(approve);
                mainPanel.add(panel);

                approve.addActionListener(e -> {
                    String sql = "";
                    if (approve.getText().equals("Approve")) {
                        approve.setText("Refuse");
                        approve.setBackground(Color.RED);
                        sql = "UPDATE swimming_membership SET paymentstatus = 1 WHERE Users_userId = " + DB1.userIDs.get(index - 1);
                    } else {
                        approve.setText("Approve");
                        approve.setBackground(Color.GREEN);
                        sql = "UPDATE swimming_membership SET paymentstatus = 0 WHERE Users_userId = " + DB1.userIDs.get(index - 1);
                    }
                    try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                        try (Statement statement = connection.createStatement()) {
                            statement.executeUpdate(sql);
                            DB1.status.set(index - 1, !DB1.status.get(index - 1));
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
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