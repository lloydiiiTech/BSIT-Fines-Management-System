package Fines;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class PaymentForm extends JFrame {
    private int studentID;
    private String adminUsername;
    private JTable finesTable;
    private JLabel schoolIDLabel;
    private JLabel fullNameLabel;
    private JLabel yearLevelLabel;
    private JLabel sectionLabel;
    private JLabel amountLabel;
    private JLabel adminLabel;
    private JTextField paymentField;
    private JCheckBox allEventsCheckBox;
    private ArrayList<JCheckBox> eventCheckBoxes;
    private JButton homeButton;
    private JButton logoutButton;
    private JPanel checkBoxPanel;
    private JButton historyButton;
    private JSeparator separator;
    double[] value;
    public PaymentForm(int studentID, String adminUsername) {
        this.studentID = studentID;
        this.adminUsername = adminUsername;
        this.eventCheckBoxes = new ArrayList<>();

        setTitle("Payment Form - " + adminUsername);
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

       
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(0, 64, 128)); 
        bannerPanel.setLayout(new BorderLayout());
        bannerPanel.setPreferredSize(new Dimension(getWidth(), 120));

        try {
            
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

           
            JLabel titleLabel = new JLabel("Payment Form", JLabel.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);

            
            bannerPanel.add(logoPanel, BorderLayout.WEST);
            bannerPanel.add(titleLabel, BorderLayout.CENTER);

           
            getContentPane().add(bannerPanel, BorderLayout.NORTH);
                    JPanel topPanel = new JPanel();
                    topPanel.setBackground(new Color(0, 64, 128));
                    bannerPanel.add(topPanel, BorderLayout.EAST);
                            topPanel.setLayout(new MigLayout("", "[59px][63px][67px]", "[21px][][][][]"));
                                            homeButton = new JButton("Home");
                                            
                                                    topPanel.add(homeButton, "cell 0 2,alignx left,aligny top");
                                                    historyButton = new JButton("History");
                                                    topPanel.add(historyButton, "cell 1 2,alignx left,aligny top");
                                                    logoutButton = new JButton("Log Out");
                                                    topPanel.add(logoutButton, "cell 2 2,alignx left,aligny top");
                                                    
                                                            logoutButton.addActionListener(e -> {
                                                                new LoginForm().setVisible(true);
                                                                dispose();
                                                            });
                                                    
                                                            historyButton.addActionListener(e -> {
                                                                new PaymentHistoryForm(adminUsername).setVisible(true);
                                                            });
                            
                             homeButton.addActionListener(e -> {
                                 new AdminHomePage(studentID, adminUsername).setVisible(true);
                                 dispose();
                             });

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error: Image resources not found. Ensure the images are placed in the correct location.");
        }

        // Panel for student and fine information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // Table to display fines
        finesTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(finesTable);
        infoPanel.add(tableScrollPane);
                        
                        separator = new JSeparator();
                        tableScrollPane.setColumnHeaderView(separator);

        // Add table row click event to display selected row details
        finesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = finesTable.getSelectedRow();
                if (selectedRow != -1) {
                    schoolIDLabel.setText("School ID: " + finesTable.getValueAt(selectedRow, 1).toString());
                    fullNameLabel.setText("Full Name: " + finesTable.getValueAt(selectedRow, 2).toString());
                    yearLevelLabel.setText("Year Level: " + finesTable.getValueAt(selectedRow, 3).toString());
                    sectionLabel.setText("Section: " + finesTable.getValueAt(selectedRow, 4).toString());
                    amountLabel.setText("Amount: ₱" + finesTable.getValueAt(selectedRow, 5 + eventCheckBoxes.size()).toString());

                    // Update event checkboxes based on the selected row
                    for (int i = 0; i < eventCheckBoxes.size(); i++) {
                        int attendanceValue = (int) finesTable.getValueAt(selectedRow, 5 + i);
                        eventCheckBoxes.get(i).setSelected(attendanceValue == 0);
                    }

                    // Calculate the payment based on selected checkboxes
                    updatePaymentField();
                }
            }
        });

        // Labels to display details when a row is selected
        schoolIDLabel = new JLabel("School ID: ");
        fullNameLabel = new JLabel("Full Name: ");
        yearLevelLabel = new JLabel("Year Level: ");
        sectionLabel = new JLabel("Section: ");
        amountLabel = new JLabel("Amount: ");
        adminLabel = new JLabel("Admin Name: ");

        // Add labels to infoPanel
        infoPanel.add(schoolIDLabel);
        infoPanel.add(fullNameLabel);
        infoPanel.add(yearLevelLabel);
        infoPanel.add(sectionLabel);
        infoPanel.add(amountLabel);
        infoPanel.add(adminLabel);

        // Add infoPanel to the center of the frame
        getContentPane().add(infoPanel, BorderLayout.CENTER);

        // Panel for payment information, checkboxes, and action buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // Panel for checkboxes
        checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(checkBoxPanel);

        allEventsCheckBox = new JCheckBox("All Events");
        allEventsCheckBox.addItemListener(new AllEventsCheckBoxListener());
        checkBoxPanel.add(allEventsCheckBox);

        // Payment panel
        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paymentField = new JTextField(10);
        JButton payButton = new JButton("Make Payment");

        paymentPanel.add(new JLabel("Payment Amount:"));
        paymentPanel.add(paymentField);
        paymentPanel.add(payButton);

        payButton.addActionListener(e -> {
            int selectedRow = finesTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "No row selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int selectedStudentID = (Integer) finesTable.getValueAt(selectedRow, 0);

            double paymentAmount;
            try {
                paymentAmount = Double.parseDouble(paymentField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid payment amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DbConnect dbConnect = new DbConnect();
            try (Connection con = dbConnect.getConnection()) {
                // Update tblattendance and tblfines
                for (int i = 0; i < eventCheckBoxes.size(); i++) {
                    JCheckBox checkBox = eventCheckBoxes.get(i);
                    if (checkBox.isSelected()) {
                        Integer eventID = (Integer) checkBox.getClientProperty("Event_ID");
                        if (eventID != null) {
                            try (PreparedStatement updateStmt = con.prepareStatement(
                                    "UPDATE tblattendance SET Balance = Balance - ? WHERE Student_ID = ? AND Event_ID = ?")) {
                                double amountToSubtract = getEventAmount(i, selectedStudentID);
                                updateStmt.setDouble(1, amountToSubtract);
                                updateStmt.setInt(2, selectedStudentID);
                                updateStmt.setInt(3, eventID);
                                updateStmt.executeUpdate();
                            }
                        }
                    }
                }

                try (PreparedStatement finesStmt = con.prepareStatement(
                        "UPDATE tblfines SET Amount = Amount - ? WHERE Student_ID = ?")) {
                    finesStmt.setDouble(1, paymentAmount);
                    finesStmt.setInt(2, selectedStudentID);
                    finesStmt.executeUpdate();
                }

                // Insert into tblpayment for each selected event
                
                for (int i = 0; i < eventCheckBoxes.size(); i++) {
                    JCheckBox checkBox = eventCheckBoxes.get(i);
                    if (checkBox.isSelected()) {
                        Integer eventID = (Integer) checkBox.getClientProperty("Event_ID");
                        double insert = value[i];
                        System.out.println("Checkbox" + i + " " +value);
                        if (eventID != null && insert != 0) {
                            try (PreparedStatement paymentStmt = con.prepareStatement(
                                    "INSERT INTO tblpayment (Student_ID, Amount, Payment_Date, Payment_Time, Admin_ID, Event_ID) VALUES (?, ?, NOW(), NOW(), (SELECT Student_ID FROM tblstudent WHERE Username = ? AND Role = 'admin'), ?)")) {
                                paymentStmt.setInt(1, selectedStudentID);
                                paymentStmt.setDouble(2, insert); // Assuming equal payment for each event
                                paymentStmt.setString(3, adminUsername);
                                paymentStmt.setInt(4, eventID);
                                paymentStmt.executeUpdate();
                            }
                        }
                    }
                    
                }

                // Generate digital receipt
                String studentName = (String) finesTable.getValueAt(selectedRow, 2);
                String schoolID = (String) finesTable.getValueAt(selectedRow, 1);
                String yearLevel = (String) finesTable.getValueAt(selectedRow, 3);
                String section = (String) finesTable.getValueAt(selectedRow, 4);
                String paymentDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

                String adminName = null;
                try (PreparedStatement adminStmt = con.prepareStatement(
                        "SELECT CONCAT(First_Name, ' ', Last_Name) AS Admin_Name FROM tblstudent WHERE Role = 'admin' AND Username = ?")) {
                    adminStmt.setString(1, adminUsername);
                    ResultSet adminRs = adminStmt.executeQuery();
                    if (adminRs.next()) {
                        adminName = adminRs.getString("Admin_Name");
                    }
                }

                String receipt = String.format(
                        "School ID: %s\nStudent Name: %s\nYear Level: %s\nSection: %s\nAmount Paid: ₱%.2f\nPayment Date: %s\nAdmin Name: %s",
                        schoolID, studentName, yearLevel, section, paymentAmount, paymentDate, adminName != null ? adminName : "N/A");

                // Show receipt and refresh data upon closing
                JOptionPane.showMessageDialog(this, receipt, "Digital Receipt", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the form
                refreshData();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error processing payment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(paymentPanel);

        // Add bottomPanel to the bottom of the frame
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Load fines data on the EDT
        SwingUtilities.invokeLater(this::loadFines);
    }
    
    private void loadFines() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();

        try {
            StringBuilder eventQuery = new StringBuilder("SELECT Event_ID, Event_Name FROM tblevent WHERE Status = 'ongoing'");
            PreparedStatement eventStmt = con.prepareStatement(eventQuery.toString());
            ResultSet eventRs = eventStmt.executeQuery();

            ArrayList<String> eventNames = new ArrayList<>();
            ArrayList<Integer> eventIDs = new ArrayList<>();
            while (eventRs.next()) {
                eventIDs.add(eventRs.getInt("Event_ID"));
                eventNames.add(eventRs.getString("Event_Name"));
            }

            StringBuilder studentQuery = new StringBuilder("SELECT s.Student_ID, s.School_ID, CONCAT(s.First_Name, ' ', s.Last_Name) AS Student_Name, s.Year_Level, s.Section FROM tblstudent s WHERE s.Role = 'student' AND Status = 'active'");
            PreparedStatement studentStmt = con.prepareStatement(studentQuery.toString());
            ResultSet studentRs = studentStmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Student ID");
            tableModel.addColumn("School ID");
            tableModel.addColumn("Student Name");
            tableModel.addColumn("Year Level");
            tableModel.addColumn("Section");

            for (String eventName : eventNames) {
                tableModel.addColumn(eventName);
            }

            tableModel.addColumn("Amount");

            while (studentRs.next()) {
                int studentID = studentRs.getInt("Student_ID");
                Object[] row = new Object[tableModel.getColumnCount()];
                row[0] = studentID;
                row[1] = studentRs.getString("School_ID");
                row[2] = studentRs.getString("Student_Name");
                row[3] = studentRs.getString("Year_Level");
                row[4] = studentRs.getString("Section");

                for (int i = 0; i < eventIDs.size(); i++) {
                    int eventID = eventIDs.get(i);
                    try (PreparedStatement attendanceStmt = con.prepareStatement(
                            "SELECT Balance FROM tblattendance WHERE Student_ID = ? AND Event_ID = ?")) {
                        attendanceStmt.setInt(1, studentID);
                        attendanceStmt.setInt(2, eventID);
                        ResultSet attendanceRs = attendanceStmt.executeQuery();
                        if (attendanceRs.next()) {
                            row[5 + i] = attendanceRs.getInt(1);
                        }
                    }
                }

                try (PreparedStatement finesStmt = con.prepareStatement(
                        "SELECT Amount FROM tblfines WHERE Student_ID = ?")) {
                    finesStmt.setInt(1, studentID);
                    ResultSet finesRs = finesStmt.executeQuery();
                    if (finesRs.next()) {
                        row[5 + eventNames.size()] = finesRs.getDouble(1);
                    }
                }

                tableModel.addRow(row);
            }
            value = new double[eventIDs.size()];
            System.out.println(value.length);

            finesTable.setModel(tableModel);

            String adminName = null;
            try (PreparedStatement adminStmt = con.prepareStatement(
                    "SELECT CONCAT(First_Name, ' ', Last_Name) AS Admin_Name FROM tblstudent WHERE Role = 'admin' AND Username = ?")) {
                adminStmt.setString(1, adminUsername);
                ResultSet adminRs = adminStmt.executeQuery();
                if (adminRs.next()) {
                    adminName = adminRs.getString("Admin_Name");
                }
            }
            if (adminName != null) {
                adminLabel.setText("Admin Name: " + adminName);
            } else {
                adminLabel.setText("Admin Name: N/A");
            }

            for (int i = 0; i < eventNames.size(); i++) {
                String eventName = eventNames.get(i);
                JCheckBox eventCheckBox = new JCheckBox(eventName);
                int eventID = eventIDs.get(i);
                eventCheckBox.putClientProperty("Event_ID", eventID); // Store Event_ID in the checkbox
                eventCheckBox.addItemListener(new EventCheckBoxListener());
                eventCheckBoxes.add(eventCheckBox);
                checkBoxPanel.add(eventCheckBox);
            }



        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading fines.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePaymentField() {
        int selectedRow = finesTable.getSelectedRow();
        if (selectedRow == -1) {
            return; // No row selected
        }

        int studentID = (Integer) finesTable.getValueAt(selectedRow, 0); // Get Student_ID from the selected row
        double totalAmount = 0.0;
        double result = 0.0;
        for (int i = 0; i < eventCheckBoxes.size(); i++) {
            JCheckBox checkBox = eventCheckBoxes.get(i);
            if (checkBox.isSelected()) {
                totalAmount += getEventAmount(i, studentID); // Pass Student_ID
            }
            result = getEventAmount(i, studentID);
            value[i] = result;
            System.out.println(result);
        }
        
        System.out.println(value.length);
        
        paymentField.setText(String.format("%.2f", totalAmount));
        System.out.println("Total Amount: " + totalAmount); // Debug
        
        
        
    }


    private double getEventAmount(int index, int studentID) {
        double amount = 0.0;
        JCheckBox checkBox = eventCheckBoxes.get(index);
        Integer eventID = (Integer) checkBox.getClientProperty("Event_ID");

        System.out.println("Fetching amount for Student_ID: " + studentID + ", Event_ID: " + eventID);

        if (eventID != null) {
            try (Connection con = new DbConnect().getConnection();
                 PreparedStatement stmt = con.prepareStatement(
                    "SELECT Balance FROM tblattendance WHERE Student_ID = ? AND Event_ID = ?")) {
                stmt.setInt(1, studentID);
                stmt.setInt(2, eventID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    amount = rs.getDouble("Balance");
                } else {
                    System.out.println("No result found for Student_ID: " + studentID + ", Event_ID: " + eventID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Event_ID is null for index: " + index);
        }
        System.out.println("Event ID: " + eventID + ", Amount: " + amount);
        return amount;
    }


    


    private class AllEventsCheckBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
            for (JCheckBox checkBox : eventCheckBoxes) {
                checkBox.setSelected(isSelected);
            }
            updatePaymentField();
        }
    }
    private void refreshData() {
        // Reload fines data
        SwingUtilities.invokeLater(this::loadFines);
    }

    private class EventCheckBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            updatePaymentField();
        }
    }
}

