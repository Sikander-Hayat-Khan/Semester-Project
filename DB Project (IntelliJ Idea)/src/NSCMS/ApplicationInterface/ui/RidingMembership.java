package NSCMS.ApplicationInterface.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RidingMembership
{
    private final JFrame frame;
    private final JLabel menuBackground;
    private final JLabel nustLogo;
    private final JButton backButton;
    private final JButton uploadReceipt;
    private final JButton generateReceiptButton;
    private final JLabel startSubciptionsLabel;
    private final int id;

    final String DB_URL = "jdbc:mysql://localhost:3306/";
    final String USERNAME = "root";
    final String PASSWORD = "Hashim#00789";

    int challanNo = 1;  // This should be fetched dynamically
    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

    public RidingMembership(int id)
    {
        frame = new JFrame("MAIN MENU");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.id=id;
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startSubciptionsLabel = new JLabel("START/RENEW YOUR MEMBERSHIP BY UPLOADING THE RECEIPT");
        startSubciptionsLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 30));
        startSubciptionsLabel.setForeground(Color.ORANGE);
        startSubciptionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon background = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\CreatedBackground.png");
        ImageIcon nustIcon = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\NUST_Logo-removebg-preview.png");
        ImageIcon buttonBackground = new ImageIcon("D:\\SEECS\\3rd Semester\\Database Systems\\Sem Project\\zzzzZZZZ\\DB Project (IntelliJ Idea)\\src\\Assets\\ButtonBackground.png");

        menuBackground = new JLabel(background);
        nustLogo = new JLabel();
        nustLogo.setIcon(nustIcon);

        backButton = new JButton("Back");
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

            public void mouseClicked(MouseEvent e){
                new Dashboard(id);
                frame.setVisible(false);
            }
        });

        uploadReceipt = new JButton("Upload Receipt");
        uploadReceipt.setFont(new Font("Arial", Font.BOLD, 25));
        uploadReceipt.setForeground(Color.BLACK);
        uploadReceipt.setIcon(buttonBackground);
        uploadReceipt.setHorizontalTextPosition(SwingConstants.CENTER);
        uploadReceipt.setVerticalTextPosition(SwingConstants.CENTER);
        uploadReceipt.setBorder(new LineBorder(Color.ORANGE, 3));
        uploadReceipt.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                uploadReceipt.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                uploadReceipt.setSize(260, 90);
            }

            public void mouseClicked(MouseEvent e) {
                boolean paymentStatus = false;
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));
                int returnValue = fc.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath().replace("\\", "/");
                    System.out.println("Selected File: " + selectedFile);
                    System.out.println("Formatted File Path: " + filePath);

                    try (FileInputStream fis = new FileInputStream(selectedFile)) {

                        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                            String sql7 = "SELECT riding_membership.paymentstatus FROM project.riding_membership WHERE Users_userId = ?";
                            try (PreparedStatement pstmt = connection.prepareStatement(sql7)) {
                                pstmt.setInt(1, id);

                                try (ResultSet rs = pstmt.executeQuery()) {
                                    if (rs.next()) {
                                        paymentStatus = rs.getBoolean("paymentstatus"); // Assuming the column name is 'name'
                                    }
                                }
                            }
                            PreparedStatement pstmt;
                            if (!paymentStatus) {
                                pstmt = connection.prepareStatement("INSERT INTO project.riding_membership (paymentstatus, Receipt, Users_userId, payment_date) VALUES (0, ?, ?, ?)");
                                pstmt.setBinaryStream(1, fis, (int) selectedFile.length());
                                pstmt.setInt(2, id);
                                pstmt.setDate(3, currentDate);
                                int rowsAffected = pstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    String updateQuery = "UPDATE project.riding_membership SET paymentstatus = 1 WHERE Users_userId = ?";
                                    PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                                    updateStmt.setInt(1, id);
                                    updateStmt.executeUpdate();
                                    updateStmt.close();
                                }

                                pstmt.close();
                            }
                            if (paymentStatus) {
                                System.out.println("ridingmonthlypayment");
                                pstmt = connection.prepareStatement("INSERT INTO project.ridingmonthlypayment (user_ID, paymentstatus) VALUES (?, 1)");
                                pstmt.setInt(1, id);
                                int rowsAffected = pstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    String updateQuery = "UPDATE project.ridingmonthlypayment SET paymentstatus = 1 WHERE user_ID = ?";
                                    PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                                    updateStmt.setInt(1, id);
                                    updateStmt.executeUpdate();
                                    updateStmt.close();
                                }

                                pstmt.close();
                            }
                        }

                        JOptionPane.showMessageDialog(frame, "Your Receipt has been uploaded and payment status updated.");
                        new Dashboard(id);
                        frame.setVisible(false);
                    } catch (IOException | SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Generate Receipt Button
        generateReceiptButton = new JButton("Generate Receipt");
        generateReceiptButton.setFont(new Font("Arial", Font.BOLD, 25));
        generateReceiptButton.setForeground(Color.BLACK);
        generateReceiptButton.setIcon(buttonBackground);
        generateReceiptButton.setHorizontalTextPosition(SwingConstants.CENTER);
        generateReceiptButton.setVerticalTextPosition(SwingConstants.CENTER);
        generateReceiptButton.setBorder(new LineBorder(Color.ORANGE, 3));
        generateReceiptButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                generateReceiptButton.setSize(270, 100);
            }

            public void mouseExited(MouseEvent e) {
                generateReceiptButton.setSize(260, 90);
            }

            public void mouseClicked(MouseEvent e) {
                generateReceipt();
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                menuBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                startSubciptionsLabel.setBounds(frame.getWidth() / 2 - 400, frame.getHeight() / 2 - 550, 800, 500);
                nustLogo.setBounds(frame.getWidth() / 2 - 115, frame.getHeight() / 2 - 250, 500, 500);
                uploadReceipt.setBounds(frame.getWidth() / 2 - 450, frame.getHeight() / 2 + 265, 260, 90);
                backButton.setBounds(frame.getWidth() / 2 + 150, frame.getHeight() / 2 + 265, 260, 90);
                generateReceiptButton.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 + 265, 260, 90);
            }
        });

        frame.add(startSubciptionsLabel);
        frame.add(uploadReceipt);
        frame.add(backButton);
        frame.add(generateReceiptButton);
        frame.add(nustLogo);
        frame.add(menuBackground);


        frame.setVisible(true);
    }
    private void generateReceipt() {
        String userName="";
        String school="";
        String class_name="";
        String facilityUsing="Horse Riding";

        boolean paymentStatus = false;

        Date issuanceDate = new Date();
        Date dueDate = new Date();
        Date paymentDate = new Date();

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT users.name FROM project.users WHERE userId = ?";
            String sql2 = "SELECT users.school FROM project.users WHERE userId = ?";
            String sql3 = "SELECT student.Class FROM project.student WHERE userId = ?";
            String sql4 = "SELECT receipt_dynamic.issuance_date FROM project.receipt_dynamic WHERE facility = ?";
            String sql5 = "SELECT receipt_dynamic.due_date FROM project.receipt_dynamic WHERE facility = ?";
            String sql6 = "SELECT swimming_membership.payment_date FROM project.swimming_membership WHERE Users_userId = ?";
            String sql7 = "SELECT swimming_membership.paymentstatus FROM project.swimming_membership WHERE Users_userId = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userName = rs.getString("name"); // Assuming the column name is 'name'
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        school = rs.getString("school"); // Assuming the column name is 'name'
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql3)) {
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        class_name = rs.getString("Class"); // Assuming the column name is 'name'
                    }
                }
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql4)) {
                pstmt.setString(1,"Swimming");

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        issuanceDate = rs.getDate("issuance_date"); // Assuming the column name is 'name'
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql5)) {
                pstmt.setString(1,"Swimming");

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        dueDate = rs.getDate("due_date"); // Assuming the column name is 'name'
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql6)) {
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        paymentDate = rs.getDate("payment_date"); // Assuming the column name is 'name'
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql7)) {
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        paymentStatus = rs.getBoolean("paymentstatus"); // Assuming the column name is 'name'
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userName);
        // Fetch the required data from the database
        String studentId = String.valueOf(id);  // Pass the user ID
        final double amount = 1500.00;  // Example amount, you can retrieve this from your system

        double fine = 0;
        if (paymentDate.after(dueDate) && paymentStatus) {
            double differenceInMillis = paymentDate.getTime() - dueDate.getTime();
            double differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24); // Convert millis to days
            System.out.println(differenceInDays);
            fine = differenceInDays * 70; // Example fine of Rs. 70 per day
        }
        double totalAmount = amount + fine;
        System.out.println(fine);

        // Create invoice instance and generate the invoice
        RidingInvoice invoice = new RidingInvoice();
        invoice.generateInvoice(challanNo++, facilityUsing, userName, studentId, school, class_name, totalAmount, issuanceDate, dueDate);
    }

    public static void main(String[] args) {
        new RidingMembership(464544);
    }
}

class RidingInvoice extends JFrame implements Printable {
    private String challanNo;
    private String facilityUsing;
    private Date dueDate;
    private Date issuanceDate;
    private String studentName;
    private String studentId;
    private String school;
    private String studentClass;
    private double amount;

    public RidingInvoice() {
        setTitle("NUST Sports Complex Management System - Invoice Generator");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void generateInvoice(int challanNo, String facilityUsing, String studentName, String studentId,
                                String school, String studentClass, double amount, Date issuanceDate, Date dueDate) {
        this.challanNo = challanNo != 0 ? String.valueOf(challanNo) : "N/A";
        this.facilityUsing = facilityUsing;
        this.studentName = studentName != null ? studentName : "N/A";
        this.studentId = studentId != null ? studentId : "N/A";
        this.school = school != null ? school : "N/A";
        this.studentClass = studentClass != null ? studentClass : "N/A";
        this.amount = amount;
        this.issuanceDate = issuanceDate;
        this.dueDate = dueDate;

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Set font
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        Font normalFont = new Font("Arial", Font.PLAIN, 12);

        // Draw header
        g2d.setFont(titleFont);
        g2d.drawString("NUST Sports Complex Management System", 50, 30);

        // Draw lines for table structure
        g2d.drawLine(50, 50, 550, 50);

        // Draw content
        g2d.setFont(normalFont);

        // First row
        g2d.drawString("Challan #", 60, 70);
        g2d.drawString(challanNo, 300, 70);

        // Second row
        g2d.drawString("Facility", 60, 90);
        g2d.drawString(facilityUsing, 300, 90);

        // Bank instruction
        g2d.drawString("Pay the dues through invoice in any Askari Bank Branch (Only).", 60, 110);

        // Dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        g2d.drawString("Due Date", 60, 140);
        g2d.drawString(dateFormat.format(dueDate), 300, 140);

        g2d.drawString("Issuance Date", 60, 160);
        g2d.drawString(dateFormat.format(issuanceDate), 300, 160);

        // Student details
        g2d.drawString("Student ID", 60, 180);
        g2d.drawString(studentId, 300, 180);

        g2d.drawString("Student Name", 60, 200);
        g2d.drawString(studentName, 300, 200);

        g2d.drawString("School", 60, 220);
        g2d.drawString(school, 300, 220);

        g2d.drawString("Class", 60, 240);
        g2d.drawString(studentClass, 300, 240);

        // Fee details
        g2d.drawString("Description", 60, 280);
        g2d.drawString("Amount (Rs.)", 300, 280);
        g2d.drawString("Swimming Fee", 60, 300);
        g2d.drawString(String.format("%.2f", amount), 300, 300);

        // Footer
        g2d.drawString("For any information/support please contact:", 60, 340);
        g2d.drawString("nusthostelmessbills@gmail.com", 60, 360);

        return PAGE_EXISTS;
    }
}