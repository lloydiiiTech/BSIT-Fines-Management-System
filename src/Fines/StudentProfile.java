package Fines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.miginfocom.swing.MigLayout;

public class StudentProfile extends JFrame {
    private JTextField schoolIDField, firstNameField, lastNameField, emailField, contactField, usernameField;
    private JComboBox<String> genderBox, yearLevelBox, sectionBox;
    private JButton updateButton, deleteButton, homeButton, changePasswordButton, logoutButton;
    private int studentID;
    private DbConnect dbConnect;

    public StudentProfile(int studentID, String username) {
        this.studentID = studentID;
        dbConnect = new DbConnect();
        dbConnect.connect();

        setTitle("Edit Profile - " + username);
        setSize(1200, 800); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        
        JLabel titleLabel = new JLabel("Student Profile Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        
        bannerPanel.add(logoPanel, BorderLayout.WEST);
        bannerPanel.add(titleLabel, BorderLayout.CENTER);

        
        getContentPane().add(bannerPanel, BorderLayout.NORTH);

       
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 64, 128));
        bannerPanel.add(topPanel, BorderLayout.EAST);
        topPanel.setLayout(new MigLayout("", "[59px][67px]", "[21px][][][][]"));
        homeButton = new JButton("Home");
        
               
                topPanel.add(homeButton, "cell 0 2,alignx left,aligny top");
                
                        // Add action listeners
                        homeButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new StudentHomePage(studentID, username).setVisible(true);
                                dispose();
                            }
                        });
        logoutButton = new JButton("Log Out");
        topPanel.add(logoutButton, "cell 1 2,alignx left,aligny top");
        
                logoutButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new LoginForm().setVisible(true);
                        dispose();
                    }
                });

       
        JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        profilePanel.add(new JLabel("School ID:"));
        schoolIDField = new JTextField();
        profilePanel.add(schoolIDField);

        profilePanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        profilePanel.add(firstNameField);

        profilePanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        profilePanel.add(lastNameField);

        profilePanel.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Select Gender", "Female", "Male", "LGBTQIA+"});
        profilePanel.add(genderBox);

        profilePanel.add(new JLabel("Year Level:"));
        yearLevelBox = new JComboBox<>(new String[]{"Select Year", "1st Year", "2nd Year", "3rd Year", "4th Year"});
        profilePanel.add(yearLevelBox);

        profilePanel.add(new JLabel("Section:"));
        sectionBox = new JComboBox<>(new String[]{"Select Section", "F1", "F2", "F3", "F4", "F5", "F6"});
        profilePanel.add(sectionBox);

        profilePanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        profilePanel.add(usernameField);

        profilePanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        profilePanel.add(emailField);

        profilePanel.add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        profilePanel.add(contactField);

        // Change Password Button
        changePasswordButton = new JButton("Change Password");
        profilePanel.add(changePasswordButton);

        getContentPane().add(profilePanel, BorderLayout.CENTER);
        
                updateButton = new JButton("Update");
                profilePanel.add(updateButton);
                
                        updateButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                updateUser(studentID);
                            }
                        });

        // Bottom Panel for the Update button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // Margin at the bottom

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePass(usernameField.getText()).setVisible(true);
            }
        });

        // Load user data from the database
        loadUserData(studentID);
    }
    private void loadUserData(int studentID) {
        String sql = "SELECT School_ID, First_Name, Last_Name, Gender, Year_Level, Section, Contact_Number, Email, Username FROM tblstudent WHERE Student_ID = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    schoolIDField.setText(rs.getString("School_ID"));
                    firstNameField.setText(rs.getString("First_Name"));
                    lastNameField.setText(rs.getString("Last_Name"));
                    genderBox.setSelectedItem(rs.getString("Gender"));
                    yearLevelBox.setSelectedItem(rs.getString("Year_Level"));
                    sectionBox.setSelectedItem(rs.getString("Section"));
                    usernameField.setText(rs.getString("Username"));
                    emailField.setText(rs.getString("Email"));
                    contactField.setText(rs.getString("Contact_Number"));
                } else {
                    JOptionPane.showMessageDialog(this, "User not found.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteUser(int studentID) {
        String sql = "DELETE FROM tblstudent WHERE Student_ID = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User deleted successfully.");
                dispose();
                new StudentHomePage(studentID, usernameField.getText()).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateUser(int studentID) {
        String sql = "UPDATE tblstudent SET School_ID = ?, First_Name = ?, Last_Name = ?, Gender = ?, Year_Level = ?, Section = ?, Username = ?, Email = ?, Contact_Number = ? WHERE Student_ID = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, schoolIDField.getText());
            pstmt.setString(2, firstNameField.getText());
            pstmt.setString(3, lastNameField.getText());
            pstmt.setString(4, (String) genderBox.getSelectedItem());
            pstmt.setString(5, (String) yearLevelBox.getSelectedItem());
            pstmt.setString(6, (String) sectionBox.getSelectedItem());
            pstmt.setString(7, usernameField.getText());
            pstmt.setString(8, emailField.getText());
            pstmt.setString(9, contactField.getText());
            pstmt.setInt(10, studentID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentProfile(1, "username").setVisible(true));
    }
}
