package Fines;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class UsersForm extends JFrame {
    private JTable studentsTable;
    private JTextField studentIdField;
    private JTextField studentNameField;
    private String selectedStudentId = "";
    private DbConnect dbConnect;

    public UsersForm(int studentID, String adminUsername) {
        setTitle("Manage Students");
        setSize(1200, 800); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        dbConnect = new DbConnect();
        dbConnect.connect();

        
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

            
            JLabel titleLabel = new JLabel("Manage Students", JLabel.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);

            
            bannerPanel.add(logoPanel, BorderLayout.WEST);
            bannerPanel.add(titleLabel, BorderLayout.CENTER);

            
            getContentPane().add(bannerPanel, BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(0, 64, 128));
            bannerPanel.add(buttonPanel, BorderLayout.EAST);
            buttonPanel.setLayout(new MigLayout("", "[59px][67px]", "[21px][][][][]"));
            JButton backButton = new JButton("Home");
            buttonPanel.add(backButton, "cell 0 2,alignx left,aligny top");
            
                    // Back button action
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AdminHomePage(studentID, adminUsername).setVisible(true);
                            dispose();
                        }
                    });
            JButton adminsButton = new JButton("Admins");
            buttonPanel.add(adminsButton, "cell 1 2,alignx left,aligny top");
            
                    // Admins button action
                    adminsButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AdminsForm(studentID, adminUsername).setVisible(true);
                            dispose(); // Close the current form
                        }
                    });

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error: Image resources not found. Ensure the images are placed in the correct location.");
        }

       
        studentsTable = new JTable();

        JScrollPane scrollPane = new JScrollPane(studentsTable);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

       
        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField(15);
        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameField = new JTextField(15);
        JButton removeButton = new JButton("Remove Student");
        removePanel.add(studentIdLabel);
        removePanel.add(studentIdField);
        removePanel.add(studentNameLabel);
        removePanel.add(studentNameField);
        removePanel.add(removeButton);

        getContentPane().add(removePanel, BorderLayout.SOUTH);

        // Load student data into the table
        loadStudentData();

        // Remove button action
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!selectedStudentId.isEmpty()) {
                    removeStudent(selectedStudentId);
                    loadStudentData(); // Refresh table
                    studentIdField.setText(""); // Clear text field
                    studentNameField.setText(""); // Clear name field
                } else {
                    JOptionPane.showMessageDialog(UsersForm.this, "Please select a student.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add a listener to handle row selection
        studentsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = studentsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedStudentId = (String) studentsTable.getValueAt(selectedRow, 0); // Assuming the first column is Student_ID
                    String firstName = (String) studentsTable.getValueAt(selectedRow, 2);
                    String lastName = (String) studentsTable.getValueAt(selectedRow, 3);
                    studentIdField.setText(selectedStudentId);
                    studentNameField.setText(firstName + " " + lastName);
                }
            }
        });
    }


    private void loadStudentData() {
        String sql = "SELECT `Student_ID`, `School_ID`, `First_Name`, `Last_Name`, `Gender`, `Year_Level`, `Section`, `Email`, `Username`, `Contact_Number` " +
                     "FROM `tblstudent` WHERE Role = 'student' AND Status = 'active'";

        try (Connection con = dbConnect.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Create table model
            DefaultTableModel model = new DefaultTableModel(new String[] {"Student_ID", "School_ID", "First_Name", "Last_Name", "Gender", "Year_Level", "Section", "Email", "Username", "Contact_Number"}, 0);

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getString("Student_ID"),
                    rs.getString("School_ID"),
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Gender"),
                    rs.getString("Year_Level"),
                    rs.getString("Section"),
                    rs.getString("Email"),
                    rs.getString("Username"),
                    rs.getString("Contact_Number")
                });
            }

            studentsTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeStudent(String studentId) {
        String sql = "UPDATE `tblstudent` SET `Status` = 'deactivated' WHERE `Student_ID` = ?";

        try (Connection con = dbConnect.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Student removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UsersForm(1, "username").setVisible(true));
    }
}
