package Fines;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class AttendanceForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private int maxAttendance;

    public AttendanceForm(int eventId, String eventName, int studentID, String username) {
        setTitle("Attendance for Event - " + eventName);
        setSize(1200, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(0, 64, 128)); // Navy blue background
        bannerPanel.setLayout(new BorderLayout());
        bannerPanel.setPreferredSize(new Dimension(getWidth(), 120));

        
        ImageIcon ccs1Icon = new ImageIcon(getClass().getResource("/resources/ccs1.png"));
        Image ccs1Image = ccs1Icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel ccs1Label = new JLabel(new ImageIcon(ccs1Image));

        ImageIcon ccs2Icon = new ImageIcon(getClass().getResource("/resources/ccs2.png"));
        Image ccs2Image = ccs2Icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel ccs2Label = new JLabel(new ImageIcon(ccs2Image));

      
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false); 
        logoPanel.add(ccs1Label);
        logoPanel.add(ccs2Label);

        
        JLabel titleLabel = new JLabel("Attendance Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

      
        bannerPanel.add(logoPanel, BorderLayout.WEST);
        bannerPanel.add(titleLabel, BorderLayout.CENTER);

        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(bannerPanel, BorderLayout.NORTH);
        
                
                JPanel topPanel = new JPanel();
                topPanel.setBackground(new Color(0, 64, 128));
                bannerPanel.add(topPanel, BorderLayout.EAST);
                        topPanel.setLayout(new MigLayout("", "[59px][71px][67px]", "[21px][][][][]"));
                        JButton homeButton = new JButton("Home");
                        
                                topPanel.add(homeButton, "cell 0 2,alignx left,aligny top");
                                
                                     
                                        homeButton.addActionListener(e -> {
                                            new AdminHomePage(studentID, username).setVisible(true);
                                            dispose();
                                        });
                        JButton paymentButton = new JButton("Payment");
                        topPanel.add(paymentButton, "cell 1 2,alignx left,aligny top");
                        paymentButton.addActionListener(e -> {
                            new PaymentForm(studentID, username).setVisible(true);
                            dispose();
                        });
                        JButton logoutButton = new JButton("Log Out");
                        topPanel.add(logoutButton, "cell 2 2,alignx left,aligny top");
                        logoutButton.addActionListener(e -> {
                            new LoginForm().setVisible(true);
                            dispose();
                        });

       
        getContentPane().add(topContainer, BorderLayout.NORTH);

        // Initialize the table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 5 && column < (5 + maxAttendance); // Editable for attendance checkboxes
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 5 && columnIndex < (5 + maxAttendance)) {
                    return Boolean.class;  // Checkbox columns
                }
                return String.class;  // Other columns
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Load the attendance data for the event
        loadAttendanceData(eventId);

        // Bottom panel with Edit and Save buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Edit Event");
        JButton saveButton = new JButton("Save");

        // Save attendance data
        saveButton.addActionListener(e -> {
            saveAttendanceData(eventId); // Save attendance data
            updateFines(eventId);
            // Update fines after saving attendance data
        });

        // Open Edit Event form
        editButton.addActionListener(e -> {
            new EditEventForm(eventId, studentID, username).setVisible(true);
        });

        bottomPanel.add(editButton);
        bottomPanel.add(saveButton);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadAttendanceData(int eventId) {
        try (Connection con = new DbConnect().getConnection()) {
            String attendanceQuery = "SELECT Attendance FROM tblEvent WHERE Event_ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(attendanceQuery)) {
                pstmt.setInt(1, eventId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        maxAttendance = rs.getInt("Attendance");
                    }
                }

                if (maxAttendance < 1) {
                    maxAttendance = 1;
                }

                String sql = "SELECT s.Student_ID, s.School_ID, s.First_Name, s.Last_Name, s.Year_Level, s.Section, a.Attendance, a.Balance, a.In, a.Out " +
                        "FROM tblattendance a " +
                        "JOIN tblstudent s ON a.Student_ID = s.Student_ID " +
                        "WHERE a.Event_ID = ? AND s.Role = 'student' AND s.Status = 'active'";
                try (PreparedStatement pstmt2 = con.prepareStatement(sql)) {
                    pstmt2.setInt(1, eventId);
                    try (ResultSet rs2 = pstmt2.executeQuery()) {
                        // Define column headers
                        String[] headers = new String[6 + maxAttendance];
                        headers[0] = "";  // Hidden column for Student_ID
                        headers[1] = "School ID";
                        headers[2] = "Student Name";
                        headers[3] = "Year Level";
                        headers[4] = "Section";
                        
                        for (int i = 0; i < maxAttendance; i++) {
                            headers[5 + i] = (i % 2 == 0) ? "In " + (i / 2 + 1) : "Out " + (i / 2 + 1);
                        }
                        headers[5 + maxAttendance] = "Balance";
                        tableModel.setColumnIdentifiers(headers);

                        // Clear previous data
                        tableModel.setRowCount(0);
                        double balance=0.0;
                        while (rs2.next()) {
                            Object[] rowData = new Object[6 + maxAttendance];
                            rowData[0] = rs2.getString("Student_ID");
                            rowData[1] = rs2.getString("School_ID");
                            rowData[2] = rs2.getString("First_Name") + " " + rs2.getString("Last_Name");
                            rowData[3] = rs2.getString("Year_Level");
                            rowData[4] = rs2.getString("Section");
                            balance = rs2.getDouble("Balance"); 

                            int inValue = rs2.getInt("In");
                            int outValue = rs2.getInt("Out");

                            
                                
                                    if (inValue >= 4) {
                                        rowData[5] = true;
                                        inValue -= 4;
                                    } 
                                    if (inValue == 2) {
                                        rowData[7] = true;
                                        inValue -= 2;
                                    } 
                                
                                    if (outValue >= 4) {
                                        rowData[6] = true;
                                        outValue -= 4;
                                    } 
                                    if (outValue == 2) {
                                        rowData[8] = true;
                                        outValue -= 2;
                                    } 
                                 
                            

                            rowData[5 + maxAttendance] = "₱" + String.format("%.2f", balance); 

                            tableModel.addRow(rowData);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading attendance data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void saveAttendanceData(int eventId) {
        try (Connection con = new DbConnect().getConnection()) {
            String checkPaymentExistsSql = "SELECT Payment_ID FROM tblpayment WHERE Student_ID = ? AND Event_ID = ?";
            String updateSql = "UPDATE tblattendance SET Attendance = ?, `In` = ?, `Out` = ?, Balance = ? WHERE Student_ID = ? AND Event_ID = ?";

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                int attendy = 0;
                int inValue = 0;
                int outValue = 0;

                for (int i = 5; i < tableModel.getColumnCount(); i++) {
                    Boolean checkbox = (tableModel.getValueAt(row, i) instanceof Boolean) ? (Boolean) tableModel.getValueAt(row, i) : false;

                    // Checkbox 1 and 3 add to `In`
                    if ((i == 5 || i == 7) && checkbox) { // Checkbox 1 (i==5), Checkbox 3 (i==7)
                        inValue += (i == 5) ? 4 : 2;
                    }

                    // Checkbox 2 and 4 add to `Out`
                    if ((i == 6 || i == 8) && checkbox) { // Checkbox 2 (i==6), Checkbox 4 (i==8)
                        outValue += (i == 6) ? 4 : 2;
                    }

                    if (checkbox) {
                        attendy++;
                    }
                }

                int balance = (maxAttendance - attendy) * 25;
                int studentId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());

                // Check if the payment record already exists
                boolean paymentExists = false;
                try (PreparedStatement checkPstmt = con.prepareStatement(checkPaymentExistsSql)) {
                    checkPstmt.setInt(1, studentId);
                    checkPstmt.setInt(2, eventId);

                    try (ResultSet rs = checkPstmt.executeQuery()) {
                        if (rs.next()) {
                            paymentExists = rs.getInt(1) > 0;
                        }
                    }
                }

                // Update attendance if payment record does not exist
                if (!paymentExists) {
                    try (PreparedStatement pstmt = con.prepareStatement(updateSql)) {
                        pstmt.setInt(1, attendy);
                        pstmt.setInt(2, inValue);
                        pstmt.setInt(3, outValue);
                        pstmt.setInt(4, balance);
                        pstmt.setInt(5, studentId);
                        pstmt.setInt(6, eventId);

                        pstmt.executeUpdate();
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Attendance data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving attendance data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateFines(int eventId) {
        try (Connection con = new DbConnect().getConnection()) {
            String checkPaymentExistsSql = "SELECT Payment_ID FROM tblpayment WHERE Student_ID = ? AND Event_ID = ?";
            String updateFineQuery = "UPDATE tblfines SET Amount = (SELECT SUM(Balance) FROM tblattendance WHERE Student_ID = ? AND Event_ID IN (SELECT Event_ID FROM tblevent WHERE Status = 'ongoing')) WHERE Student_ID = ?";
            
            String studentQuery = "SELECT DISTINCT Student_ID FROM tblattendance WHERE Event_ID = ?";

            try (PreparedStatement studentPstmt = con.prepareStatement(studentQuery)) {
                studentPstmt.setInt(1, eventId);
                try (ResultSet rs = studentPstmt.executeQuery()) {
                    while (rs.next()) {
                        int studentId = rs.getInt("Student_ID");

                        // Check if the payment record already exists
                        boolean paymentExists = false;
                        try (PreparedStatement checkPstmt = con.prepareStatement(checkPaymentExistsSql)) {
                            checkPstmt.setInt(1, studentId);
                            checkPstmt.setInt(2, eventId);

                            try (ResultSet checkRs = checkPstmt.executeQuery()) {
                                if (checkRs.next()) {
                                    paymentExists = checkRs.getInt(1) > 0;
                                    loadAttendanceData(eventId);
                                }
                            }
                        }

                        if (!paymentExists) {
                            // Update fines if payment record does not exist
                            try (PreparedStatement updatePstmt = con.prepareStatement(updateFineQuery)) {
                                updatePstmt.setInt(1, studentId);
                                updatePstmt.setInt(2, studentId);

                                updatePstmt.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating fines.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void refreshData() {
        loadAttendanceData(tableModel.getRowCount()); // Adjusted to refresh data
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int eventId = 1;
            String eventName = "Sample Event";
            new AttendanceForm(eventId, eventName, 1, "username").setVisible(true);
        });
    }
}