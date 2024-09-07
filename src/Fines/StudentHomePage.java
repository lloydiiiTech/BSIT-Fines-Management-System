package Fines;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.miginfocom.swing.MigLayout;

public class StudentHomePage extends JFrame {
    private int studentID;
    private String username;
    private JPanel eventsPanel;
    private JLabel totalBalanceLabel;
    private DefaultTableModel tableModel;
    private JTable eventsTable;

    public StudentHomePage(int studentID, String username) {
        this.studentID = studentID;
        this.username = username;

        setTitle("Student Home - " + username);
        setSize(1200, 800); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        
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

       
        JLabel titleLabel = new JLabel("Student Home Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

       
        bannerPanel.add(logoPanel, BorderLayout.WEST);
        bannerPanel.add(titleLabel, BorderLayout.CENTER);

       
        getContentPane().add(bannerPanel, BorderLayout.NORTH);

      
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 64, 128));
        bannerPanel.add(buttonPanel, BorderLayout.EAST);
        buttonPanel.setLayout(new MigLayout("", "[61px][87px][67px]", "[21px][][][][]"));
        JButton profileButton = new JButton("Profile");
        
               
                buttonPanel.add(profileButton, "cell 0 2,alignx left,aligny top");
                
                        // Add action listener to the Profile button
                        profileButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new StudentProfile(studentID, username).setVisible(true);
                                dispose();
                            }
                        });
        JButton transactionButton = new JButton("Transaction");
        buttonPanel.add(transactionButton, "cell 1 2,alignx left,aligny top");
        
                // Add action listener to the Transaction button
                transactionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new TransactionForm(studentID).setVisible(true);
                    }
                });
        JButton logoutButton = new JButton("Log Out");
        buttonPanel.add(logoutButton, "cell 2 2,alignx left,aligny top");
        
                // Add action listener to the Log Out button
                logoutButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new LoginForm().setVisible(true);
                        dispose();
                    }
                });

       
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new GridLayout(0, 2, 10, 10)); 
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

       
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalBalanceLabel = new JLabel("Total Balance: $0.00");
        totalPanel.add(totalBalanceLabel);
        getContentPane().add(totalPanel, BorderLayout.SOUTH);

        // Initialize table model and load event data
        initializeTableModel();
        loadEventData();
    }

    private void initializeTableModel() {
        // Initial static columns
        String[] columnNames = {"Event Name", "Event Type", "Event Date", "Start", "End", "In 1", "Out 1", "In 2", "Out 2", "Balance", "Event Created by"};
        tableModel = new DefaultTableModel(columnNames, 0);
        eventsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void loadEventData() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();
        String sqlEvents = "SELECT e.Event_ID, e.Event_Name, e.Event_Type, e.Event_Date, " +
                "e.Attendance, e.Start, e.End, a.Balance, a.In, a.Out, s.First_Name AS Admin_First_Name, s.Last_Name AS Admin_Last_Name " +
                "FROM tblEvent e " +
                "JOIN tblAttendance a ON e.Event_ID = a.Event_ID " +
                "JOIN tblstudent s ON e.Admin_ID = s.Student_ID " +
                "WHERE a.Student_ID = ? AND e.Status = 'ongoing'";

        try (PreparedStatement pstmt = con.prepareStatement(sqlEvents)) {
            pstmt.setInt(1, studentID);
            try (ResultSet rs = pstmt.executeQuery()) {
                double totalBalance = 0.0;

                while (rs.next()) {
                    String adminName = rs.getString("Admin_First_Name") + " " + rs.getString("Admin_Last_Name");
                    String eventName = rs.getString("Event_Name");
                    String eventType = rs.getString("Event_Type");
                    String eventDate = rs.getString("Event_Date");
                    String start = rs.getString("Start");
                    String end = rs.getString("End");
                    double balance = rs.getDouble("Balance");
                    int attendance = rs.getInt("Attendance");
                    int inValue = rs.getInt("In");
                    int outValue = rs.getInt("Out");

                    // Create row data based on the attendance
                    String[] rowData = new String[11];
                    rowData[0] = eventName;
                    rowData[1] = eventType;
                    rowData[2] = eventDate;
                    rowData[3] = start;
                    rowData[4] = end;
                    populateInOutColumns(rowData, attendance);
                    
                    if (inValue >= 4) {
       	             rowData[5] = "Present";
       	             inValue -= 4;
	       	         } else {
	       	        	 rowData[5] = "Absent";
	       	         }
	       	         if (inValue == 2) {
	       	             rowData[7] = "Present";
	       	             inValue -= 2;
	       	         } else {
	       	        	 rowData[7] = "Absent";
	       	         }
	       	     
	       	         if (outValue >= 4) {
	       	             rowData[6] = "Present";
	       	             outValue -= 4;
	       	         } else {
	       	        	 rowData[6] = "Absent";
	       	         }
	       	         if (outValue == 2) {
	       	             rowData[8] = "Present";
	       	             outValue -= 2;
	       	         } else {
	       	        	 rowData[8] = "Absent";
	       	         }
	       	         
	       	      for (int i = attendance; i < 4; i++) {
	                  if (i % 2 == 0) {
	                      rowData[5 + i] = "Absent";  // Leave "In" as Absent
	                  } else {
	                      rowData[5 + i] = "";  // Leave "Out" blank if attendance is smaller
	                  }
	              }
       	         

                    rowData[9] = "₱" + String.format("%.2f", balance);
                    rowData[10] = adminName;

                    // Add the row to the table
                    tableModel.addRow(rowData);

                    // Sum up the total balance
                    totalBalance += balance;
                }

                // Update total balance
                totalBalanceLabel.setText("Total Balance: $" + String.format("%.2f", totalBalance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading event data.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }

    private void populateInOutColumns(String[] rowData, int attendance) {
        // Based on the attendance value, fill in the columns for "In" and "Out"
    	
        

        // For columns beyond the attendance value, leave them blank
        for (int i = attendance; i < 4; i++) {
            if (i % 2 == 0) {
                rowData[5 + i] = "Absent";  // Leave "In" as Absent
            } else {
                rowData[5 + i] = "";  // Leave "Out" blank if attendance is smaller
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentHomePage(1, "username").setVisible(true));
    }
}