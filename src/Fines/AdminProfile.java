package Fines;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.miginfocom.swing.MigLayout;

public class AdminProfile extends JFrame {
    private JTextField schoolIDField, firstNameField, lastNameField, emailField, contactField, usernameField;
    private JComboBox<String> genderBox;
    private JButton updateButton, deleteButton, homeButton, changePasswordButton, logoutButton, addAdminButton, users;
    private DbConnect dbConnect;

    public AdminProfile(int studentID, String username) {
        dbConnect = new DbConnect();
        dbConnect.connect();

        setTitle("Admin Profile - " + username);
        setSize(1200, 800); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

            
            JLabel titleLabel = new JLabel("Admin Profile", JLabel.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);

        
            bannerPanel.add(logoPanel, BorderLayout.WEST);
            bannerPanel.add(titleLabel, BorderLayout.CENTER);

           
            getContentPane().add(bannerPanel, BorderLayout.NORTH);
            
                 
                    JPanel topPanel = new JPanel();
                    topPanel.setBackground(new Color(0, 64, 128));
                    bannerPanel.add(topPanel, BorderLayout.EAST);
                            topPanel.setLayout(new MigLayout("", "[59px][59px][83px][67px]", "[21px][][][][][]"));
                                    homeButton = new JButton("Home");
                                    
                                            topPanel.add(homeButton, "cell 0 3,alignx left,aligny top");
                                            
                                                    // Add action listeners
                                                    homeButton.addActionListener(e -> {
                                                        new AdminHomePage(studentID, username).setVisible(true);
                                                        dispose();
                                                    });
                                    users = new JButton("Users");
                                    topPanel.add(users, "cell 1 3,alignx left,aligny top");
                                    users.addActionListener(e -> {
                                        new UsersForm(studentID, username).setVisible(true);
                                        dispose();
                                    });
                                    addAdminButton = new JButton("Add Admin");
                                    topPanel.add(addAdminButton, "cell 2 3,alignx left,aligny top");
                                    
                                            addAdminButton.addActionListener(e -> {
                                                new AddAdminForm(username, dbConnect).setVisible(true);
                                            });
                                    logoutButton = new JButton("Log Out");
                                    topPanel.add(logoutButton, "cell 3 3,alignx left,aligny top");
                                    
                                            logoutButton.addActionListener(e -> {
                                                new LoginForm().setVisible(true);
                                                dispose();
                                            });
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error: Image resources not found. Ensure the images are placed in the correct location.");
        }

        // Profile Form
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

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); 

        deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);

        updateButton = new JButton("Update");
        buttonPanel.add(updateButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH); 

        deleteButton.addActionListener(e -> deleteUser(studentID));

        updateButton.addActionListener(e -> updateUser(studentID));

        changePasswordButton.addActionListener(e -> new ChangePass(usernameField.getText()).setVisible(true));

        // Load user data from the database
        loadUserData(studentID);
    }

    private void loadUserData(int studentID) {
        String sql = "SELECT School_ID, First_Name, Last_Name, Gender, Contact_Number, Email, Username FROM tblstudent WHERE Student_ID = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    schoolIDField.setText(rs.getString("School_ID"));
                    firstNameField.setText(rs.getString("First_Name"));
                    lastNameField.setText(rs.getString("Last_Name"));
                    genderBox.setSelectedItem(rs.getString("Gender"));

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
        if (studentID != 1) { // Assuming 1 is the main admin ID that cannot be deleted
            String sql = "UPDATE tblstudent SET Status = 'deactivated' WHERE Student_ID = ?";
            try (Connection conn = dbConnect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentID);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully.");
                    dispose(); // Close the AdminProfile window
                    new LoginForm().setVisible(true); // Open the LoginForm
                } else {
                    JOptionPane.showMessageDialog(this, "User not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "This is the Main Admin; it can't be deleted!");
        }
    }

    private void updateUser(int studentID) {
        // Check if the email or username is already in use by another user
        if (isEmailOrUsernameTaken(emailField.getText(), usernameField.getText(), studentID)) {
            return;
        }

        String sql = "UPDATE tblstudent SET School_ID = ?, First_Name = ?, Last_Name = ?, Gender = ?, Username = ?, Email = ?, Contact_Number = ? WHERE Student_ID = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, schoolIDField.getText());
            pstmt.setString(2, firstNameField.getText());
            pstmt.setString(3, lastNameField.getText());
            pstmt.setString(4, (String) genderBox.getSelectedItem());
            pstmt.setString(5, usernameField.getText());
            pstmt.setString(6, emailField.getText());
            pstmt.setString(7, contactField.getText());
            pstmt.setInt(8, studentID);

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

    private boolean isEmailOrUsernameTaken(String email, String username, int currentStudentID) {
        String sql = "SELECT COUNT(*) FROM tblstudent WHERE (Email = ? OR Username = ?) AND Student_ID != ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setInt(3, currentStudentID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Email or Username is already in use by another user.", "Error", JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminProfile(1, "username").setVisible(true));
    }
}
