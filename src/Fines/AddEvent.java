package Fines;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AddEvent extends JFrame {
    private JTextField nameField, typeField, descField, dateField, startTimeField, endTimeField;
    private JCheckBox inCheckBox1, outCheckBox1, inCheckBox2, outCheckBox2;
    private int adminId;
    int student;
    String user;
    
    public AddEvent(int studentID, String username) {
        student = studentID;
        user = username;
        this.adminId = studentID;
        setTitle("Add New Event by " + username);
        setSize(1200, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(0, 64, 128)); 
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

        
        JLabel titleLabel = new JLabel("Add Event Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        
        bannerPanel.add(logoPanel, BorderLayout.WEST);
        bannerPanel.add(titleLabel, BorderLayout.CENTER);

       
        add(bannerPanel, BorderLayout.NORTH);

       
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10)); 

        panel.add(new JLabel("Event Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Event Type:"));
        typeField = new JTextField();
        panel.add(typeField);

        panel.add(new JLabel("Description:"));
        descField = new JTextField();
        panel.add(descField);

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        panel.add(dateField);

        panel.add(new JLabel("Start Time (HH:MM):"));
        startTimeField = new JTextField();
        panel.add(startTimeField);

        panel.add(new JLabel("End Time (HH:MM):"));
        endTimeField = new JTextField();
        panel.add(endTimeField);

        panel.add(new JLabel("Attendance:"));
        JPanel attendancePanel = new JPanel(new GridLayout(1, 4, 5, 5)); 
        inCheckBox1 = new JCheckBox("IN");
        outCheckBox1 = new JCheckBox("OUT");
        inCheckBox2 = new JCheckBox("IN");
        outCheckBox2 = new JCheckBox("OUT");
        attendancePanel.add(inCheckBox1);
        attendancePanel.add(outCheckBox1);
        attendancePanel.add(inCheckBox2);
        attendancePanel.add(outCheckBox2);
        panel.add(attendancePanel);

        
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        panel.add(submitButton);
        panel.add(cancelButton);

       
        add(panel, BorderLayout.CENTER);

       
        submitButton.addActionListener(e -> {
            addEvent();
        });

        
        cancelButton.addActionListener(e -> {
            new AdminHomePage(student, user).setVisible(true);
            dispose();
        });
    }
    private void addEvent() {
        String eventName = nameField.getText();
        String eventType = typeField.getText();
        String description = descField.getText();
        String date = dateField.getText();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        // Calculate total attendance
        int attendance = 0, in = 0, out = 0;
        if (inCheckBox1.isSelected()) {
            attendance += 1; 
            in += 4;
        }
        if (outCheckBox1.isSelected()) {
            attendance += 1;
            out += 4;
        }
        if (inCheckBox2.isSelected()) {
            attendance += 1;
            in += 2;
        }
        if (outCheckBox2.isSelected()) {
            attendance += 1;
            out += 2;
        }

        int amount = attendance * 25; // Calculate amount

        String sqlEvent = "INSERT INTO tblevent (Event_Name, Event_Type, Event_Description, Event_Date, Start, End, Attendance, Admin_ID, Status, `In`, `Out`) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'ongoing', ?, ?)";

        DbConnect dbConnect = new DbConnect();
        Connection con = null;

        try {
            con = dbConnect.getConnection();
            con.setAutoCommit(false); // Start transaction

            // Validate admin ID existence
            String checkAdminSql = "SELECT COUNT(Student_ID) FROM tblstudent WHERE Student_ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(checkAdminSql)) {
                pstmt.setInt(1, student);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    JOptionPane.showMessageDialog(this, "Invalid Admin_ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if Admin_ID is not valid
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error checking Admin_ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method on error
            }

            // Insert event
            int eventId = 0;
            try (PreparedStatement pstmt = con.prepareStatement(sqlEvent, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, eventType);
                pstmt.setString(3, description);
                pstmt.setString(4, date);
                pstmt.setString(5, startTime);
                pstmt.setString(6, endTime);
                pstmt.setInt(7, attendance);
                pstmt.setInt(8, student); 
                pstmt.setInt(9, in);
                pstmt.setInt(10, out);

                pstmt.executeUpdate();

                // Get generated Event_ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    eventId = generatedKeys.getInt(1);
                }
            }

            // Insert into tblAttendance
            insertAttendanceRecords(con, eventId, amount);

            // Insert into tblAmount
            insertAmountRecords(con, amount);

            con.commit(); // Commit transaction
            JOptionPane.showMessageDialog(this, "Event added successfully.");

            new AdminHomePage(student, user).setVisible(true);
            dispose(); // Close the AddEvent window

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding event.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close(); // Close connection
        }
    }

    private void insertAttendanceRecords(Connection con, int eventId, int amount) throws SQLException {
        String sqlAttendance = "INSERT INTO tblattendance (Student_ID, Event_ID, Attendance, `In`, `Out`, Balance) " +
                               "SELECT Student_ID, ?, 0, 0, 0, ? FROM tblstudent WHERE Role = 'student' AND Status = 'active'";

        try (PreparedStatement pstmt = con.prepareStatement(sqlAttendance)) {
            pstmt.setInt(1, eventId);
            pstmt.setInt(2, amount);
            pstmt.executeUpdate();
        }
    }

    private void insertAmountRecords(Connection con, int amount) throws SQLException {
        String sqlUpdateAmount = "UPDATE tblfines " +
                                 "SET Amount = Amount + ? " +
                                 "WHERE Student_ID IN (SELECT Student_ID FROM tblstudent WHERE Role = 'student' AND Status = 'active')";

        try (PreparedStatement pstmt = con.prepareStatement(sqlUpdateAmount)) {
            pstmt.setInt(1, amount);
            pstmt.executeUpdate();
        }
    }
}
