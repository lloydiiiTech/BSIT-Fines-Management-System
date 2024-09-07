package Fines;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class EditEventForm extends JFrame {
    private JTextField nameField, typeField, descField, dateField, startTimeField, endTimeField;
    private JCheckBox inCheckBox1, outCheckBox1, inCheckBox2, outCheckBox2;
    private int eventId;
    private String username = "";
    private int studid = 0;

    public EditEventForm(int eventId, int studentid, String username) {
        this.eventId = eventId;
        this.studid = studentid;
        this.username = username;

        setTitle("Edit Event");
        setSize(1200, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
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

        
        JLabel titleLabel = new JLabel("Edit Event Form", JLabel.CENTER);
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
        JPanel attendancePanel = new JPanel(new GridLayout(1, 4, 5, 5)); // Adjusted for checkboxes layout
        inCheckBox1 = new JCheckBox("IN");
        outCheckBox1 = new JCheckBox("OUT");
        inCheckBox2 = new JCheckBox("IN");
        outCheckBox2 = new JCheckBox("OUT");
        attendancePanel.add(inCheckBox1);
        attendancePanel.add(outCheckBox1);
        attendancePanel.add(inCheckBox2);
        attendancePanel.add(outCheckBox2);
        panel.add(attendancePanel);

        // Update and Achieve buttons
        JButton updateButton = new JButton("Update");
        JButton achieveButton = new JButton("Archive Event");
        panel.add(updateButton);
        panel.add(achieveButton);

        // Add the form panel to the frame
        add(panel, BorderLayout.CENTER);

        // Load event details
        loadEventDetails();

        // Update button action
        updateButton.addActionListener(e -> updateEventDetails());

        // Achieve Event button action
        achieveButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(EditEventForm.this,
                "Are you sure you want to mark this event as achieved?",
                "Confirm Achieve Event",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                achieveEvent();
            }
        });
    }
    private void loadEventDetails() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();

        try {
            String sql = "SELECT Event_Name, Event_Type, Event_Description, Event_Date, Start, End, `In`, `Out` " +
                         "FROM tblevent WHERE Event_ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, eventId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nameField.setText(rs.getString("Event_Name"));
                    typeField.setText(rs.getString("Event_Type"));
                    descField.setText(rs.getString("Event_Description"));
                    dateField.setText(rs.getString("Event_Date"));
                    startTimeField.setText(rs.getString("Start"));
                    endTimeField.setText(rs.getString("End"));

                    // Set the attendance checkboxes
                    int in = rs.getInt("In");
                    if (in >= 4) {
                        inCheckBox1.setSelected(true);
                        in -= 4;
                    }
                    if (in == 2) {
                        inCheckBox2.setSelected(true);
                    }
                    int out = rs.getInt("Out");
                    if (out >= 4) {
                        outCheckBox1.setSelected(true);
                        out -= 4;
                    }
                    if (out == 2) {
                        outCheckBox2.setSelected(true);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading event details.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }

    private void updateEventDetails() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();

        try {
            // Update tblevent with the new event details
            String updateEventSql = "UPDATE tblevent SET Event_Name = ?, Event_Type = ?, Event_Description = ?, Event_Date = ?, " +
                                    "Start = ?, End = ?, Attendance = ?, `In` = ?, `Out` = ? WHERE Event_ID = ?";
            
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
            
            try (PreparedStatement pstmt = con.prepareStatement(updateEventSql)) {
                pstmt.setString(1, nameField.getText());
                pstmt.setString(2, typeField.getText());
                pstmt.setString(3, descField.getText());
                pstmt.setString(4, dateField.getText());
                pstmt.setString(5, startTimeField.getText());
                pstmt.setString(6, endTimeField.getText());
                pstmt.setInt(7, attendance);
                pstmt.setInt(8, in);
                pstmt.setInt(9, out);
                pstmt.setInt(10, eventId);

                pstmt.executeUpdate();
            }

            // After updating the event, update tblAttendance and tblFines
            String selectAttendanceSql = "SELECT Student_ID, Balance, Attendance FROM tblattendance WHERE Event_ID = ?";
            try (PreparedStatement selectPstmt = con.prepareStatement(selectAttendanceSql)) {
                selectPstmt.setInt(1, eventId);
                ResultSet rs = selectPstmt.executeQuery();

                while (rs.next()) {
                    int studentId = rs.getInt("Student_ID");
                    int studentAttendance = rs.getInt("Attendance");
                    int balance = rs.getInt("Balance");

                    // Update tblattendance Balance
                    String updateAttendanceSql = "UPDATE tblattendance SET Balance = ? WHERE Student_ID = ? AND Event_ID = ?";
                    
                    
                    int newBalance = ((attendance - studentAttendance)*25);

                    try (PreparedStatement updateAttendancePstmt = con.prepareStatement(updateAttendanceSql)) {
                        updateAttendancePstmt.setInt(1, newBalance);
                        updateAttendancePstmt.setInt(2, studentId);
                        updateAttendancePstmt.setInt(3, eventId);
                        updateAttendancePstmt.executeUpdate();
                    }

                    // Update tblfines Amount
                    String updateFinesSql = "UPDATE tblfines SET Amount = (SELECT SUM(Balance) FROM tblattendance WHERE Student_ID = ? AND Event_ID IN (SELECT Event_ID FROM tblevent WHERE Status = 'ongoing')) WHERE Student_ID = ?"
;

                    try (PreparedStatement updateFinesPstmt = con.prepareStatement(updateFinesSql)) {
                        updateFinesPstmt.setInt(1, studentId);
                        updateFinesPstmt.setInt(2, studentId);
                        updateFinesPstmt.executeUpdate();
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Event details and related records updated successfully.");

            dispose(); // Close the form

            AdminHomePage adminHomePage = new AdminHomePage(studid, username);
            adminHomePage.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating event details and related records.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }


    private void achieveEvent() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();

        try {
            // 1. Retrieve the attendance value from tblevent for the specific event
            String selectEventAttendanceSql = "SELECT Attendance FROM tblevent WHERE Event_ID = ?";
            int eventAttendance = 0;

            try (PreparedStatement pstmt = con.prepareStatement(selectEventAttendanceSql)) {
                pstmt.setInt(1, eventId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    eventAttendance = rs.getInt("Attendance");
                }
            }

            

            // 4. Mark the event as done in tblevent
            String achieveEventSql = "UPDATE tblevent SET Status = 'done' WHERE Event_ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(achieveEventSql)) {
                pstmt.setInt(1, eventId);
                pstmt.executeUpdate();
            }
            
         // 2. Retrieve attendance and student ID from tblAttendance for the specific event
            String selectAttendanceSql = "SELECT Attendance, Student_ID FROM tblattendance WHERE Event_ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(selectAttendanceSql)) {
                pstmt.setInt(1, eventId);
                ResultSet rs = pstmt.executeQuery();

                // 3. Iterate through each student, calculate the fine deduction, and update tblfines
                while (rs.next()) {
                    int studentAttendance = rs.getInt("Attendance");
                    int studentId = rs.getInt("Student_ID");

                    // Calculate fine deduction
                    int fineDeduction = (eventAttendance - studentAttendance) * 25;

                    // Update tblfines for the current student
                    String updateFinesSql = "UPDATE tblfines SET Amount = (SELECT SUM(Balance) FROM tblattendance WHERE Student_ID = ? AND Event_ID IN (SELECT Event_ID FROM tblevent WHERE Status = 'ongoing')) WHERE Student_ID = ?";
                    try (PreparedStatement updatePstmt = con.prepareStatement(updateFinesSql)) {
                        updatePstmt.setInt(1, studentId);
                        updatePstmt.setInt(2, studentId);
                        updatePstmt.executeUpdate();
                    }
                }
            }

            // 5. Show success message and close the form
            JOptionPane.showMessageDialog(this, "Event achieved and fines updated successfully for all students.");

            dispose(); // Close the form
            
            AdminHomePage adminHomePage = new AdminHomePage(studid, username);
            adminHomePage.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating event status and fines.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }


}

