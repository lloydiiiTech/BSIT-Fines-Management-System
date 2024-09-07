package Fines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationForm extends JFrame {

    // Declare components
    private JTextField schoolIDField, firstNameField, lastNameField, emailField, contactField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> genderBox, yearLevelBox, sectionBox;
    private JButton registerButton, alreadyHaveAccountButton;

    // Database connection instance
    private DbConnect dbConnect;

    public RegistrationForm() {
    	
    	  dbConnect = new DbConnect();
    	  
       
        setTitle("Registration Form");
        setSize(1200, 800); 
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 64, 128)); // Navy blue color
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.setLayout(new GridBagLayout());

        
        ImageIcon imageIcon1 = new ImageIcon(getClass().getResource("/resources/ccs1.png"));
        Image image1 = imageIcon1.getImage();
        Image scaledImage1 = image1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

       
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/ccs2.png"));
        Image image2 = imageIcon2.getImage();
        Image scaledImage2 = image2.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel1 = new JLabel(new ImageIcon(scaledImage1));
        JLabel imageLabel2 = new JLabel(new ImageIcon(scaledImage2));

        GridBagConstraints gbc_imageLabel1 = new GridBagConstraints();
        gbc_imageLabel1.insets = new Insets(10, 10, 10, 5);
        gbc_imageLabel1.gridx = 0;
        gbc_imageLabel1.gridy = 0;
        gbc_imageLabel1.anchor = GridBagConstraints.LINE_END;
        leftPanel.add(imageLabel1, gbc_imageLabel1);

        GridBagConstraints gbc_imageLabel2 = new GridBagConstraints();
        gbc_imageLabel2.insets = new Insets(10, 5, 10, 10);
        gbc_imageLabel2.gridx = 1;
        gbc_imageLabel2.gridy = 0;
        gbc_imageLabel2.anchor = GridBagConstraints.LINE_START;
        leftPanel.add(imageLabel2, gbc_imageLabel2);

        
        JLabel welcomeLabel = new JLabel("<html><div style='text-align:center;'>Welcome<br>to Registration Form</div></html>");
        welcomeLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc_welcomeLabel = new GridBagConstraints();
        gbc_welcomeLabel.insets = new Insets(10, 10, 10, 10);
        gbc_welcomeLabel.gridx = 0;
        gbc_welcomeLabel.gridy = 2;
        gbc_welcomeLabel.gridwidth = 2;
        gbc_welcomeLabel.anchor = GridBagConstraints.CENTER;
        leftPanel.add(welcomeLabel, gbc_welcomeLabel);

       
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(255, 255, 255)); // White color
        rightPanel.setLayout(new GridBagLayout());

        // School ID field
        JLabel schoolIDLabel = new JLabel("School ID:");
        schoolIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_schoolIDLabel = new GridBagConstraints();
        gbc_schoolIDLabel.insets = new Insets(10, 10, 10, 10);
        gbc_schoolIDLabel.gridx = 0;
        gbc_schoolIDLabel.gridy = 0;
        gbc_schoolIDLabel.gridwidth = 1;
        rightPanel.add(schoolIDLabel, gbc_schoolIDLabel);

        schoolIDField = new JTextField(30);
        schoolIDField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_schoolIDField = new GridBagConstraints();
        gbc_schoolIDField.insets = new Insets(10, 10, 10, 10);
        gbc_schoolIDField.gridx = 1;
        gbc_schoolIDField.gridy = 0;
        gbc_schoolIDField.gridwidth = 2;
        rightPanel.add(schoolIDField, gbc_schoolIDField);

        // First Name field
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
        gbc_firstNameLabel.insets = new Insets(10, 10, 10, 10);
        gbc_firstNameLabel.gridx = 0;
        gbc_firstNameLabel.gridy = 1;
        rightPanel.add(firstNameLabel, gbc_firstNameLabel);

        firstNameField = new JTextField(30);
        firstNameField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_firstNameField = new GridBagConstraints();
        gbc_firstNameField.insets = new Insets(10, 10, 10, 10);
        gbc_firstNameField.gridx = 1;
        gbc_firstNameField.gridy = 1;
        gbc_firstNameField.gridwidth = 2;
        rightPanel.add(firstNameField, gbc_firstNameField);

        // Last Name field
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.insets = new Insets(10, 10, 10, 10);
        gbc_lastNameLabel.gridx = 0;
        gbc_lastNameLabel.gridy = 2;
        rightPanel.add(lastNameLabel, gbc_lastNameLabel);

        lastNameField = new JTextField(30);
        lastNameField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = new Insets(10, 10, 10, 10);
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 2;
        gbc_lastNameField.gridwidth = 2;
        rightPanel.add(lastNameField, gbc_lastNameField);

     // Gender field
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_genderLabel = new GridBagConstraints();
        gbc_genderLabel.insets = new Insets(10, 10, 10, 10);
        gbc_genderLabel.gridx = 0;
        gbc_genderLabel.gridy = 3;
        rightPanel.add(genderLabel, gbc_genderLabel);

        genderBox = new JComboBox<>(new String[]{"Select Gender", "Female", "Male", "LGBTQIA+"});
        genderBox.setPreferredSize(new Dimension(schoolIDField.getPreferredSize())); // Match the text field size
        GridBagConstraints gbc_genderBox = new GridBagConstraints();
        gbc_genderBox.insets = new Insets(10, 10, 10, 10);
        gbc_genderBox.gridx = 1;
        gbc_genderBox.gridy = 3;
        gbc_genderBox.gridwidth = 2;
        rightPanel.add(genderBox, gbc_genderBox);

        // Year Level field
        JLabel yearLevelLabel = new JLabel("Year Level:");
        yearLevelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_yearLevelLabel = new GridBagConstraints();
        gbc_yearLevelLabel.insets = new Insets(10, 10, 10, 10);
        gbc_yearLevelLabel.gridx = 0;
        gbc_yearLevelLabel.gridy = 4;
        rightPanel.add(yearLevelLabel, gbc_yearLevelLabel);

        yearLevelBox = new JComboBox<>(new String[]{"Select Year", "1st Year", "2nd Year", "3rd Year", "4th Year"});
        yearLevelBox.setPreferredSize(new Dimension(schoolIDField.getPreferredSize())); // Match the text field size
        GridBagConstraints gbc_yearLevelBox = new GridBagConstraints();
        gbc_yearLevelBox.insets = new Insets(10, 10, 10, 10);
        gbc_yearLevelBox.gridx = 1;
        gbc_yearLevelBox.gridy = 4;
        gbc_yearLevelBox.gridwidth = 2;
        rightPanel.add(yearLevelBox, gbc_yearLevelBox);

        // Section field
        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_sectionLabel = new GridBagConstraints();
        gbc_sectionLabel.insets = new Insets(10, 10, 10, 10);
        gbc_sectionLabel.gridx = 0;
        gbc_sectionLabel.gridy = 5;
        rightPanel.add(sectionLabel, gbc_sectionLabel);

        sectionBox = new JComboBox<>(new String[]{"Select Section", "F1", "F2", "F3", "F4", "F5", "F6"});
        sectionBox.setPreferredSize(new Dimension(schoolIDField.getPreferredSize())); // Match the text field size
        GridBagConstraints gbc_sectionBox = new GridBagConstraints();
        gbc_sectionBox.insets = new Insets(10, 10, 10, 10);
        gbc_sectionBox.gridx = 1;
        gbc_sectionBox.gridy = 5;
        gbc_sectionBox.gridwidth = 2;
        rightPanel.add(sectionBox, gbc_sectionBox);


        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
        gbc_usernameLabel.insets = new Insets(10, 10, 10, 10);
        gbc_usernameLabel.gridx = 0;
        gbc_usernameLabel.gridy = 6;
        rightPanel.add(usernameLabel, gbc_usernameLabel);

        usernameField = new JTextField(30);
        usernameField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_usernameField = new GridBagConstraints();
        gbc_usernameField.insets = new Insets(10, 10, 10, 10);
        gbc_usernameField.gridx = 1;
        gbc_usernameField.gridy = 6;
        gbc_usernameField.gridwidth = 2;
        rightPanel.add(usernameField, gbc_usernameField);

        // Email field
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_emailLabel = new GridBagConstraints();
        gbc_emailLabel.insets = new Insets(10, 10, 10, 10);
        gbc_emailLabel.gridx = 0;
        gbc_emailLabel.gridy = 7;
        rightPanel.add(emailLabel, gbc_emailLabel);

        emailField = new JTextField(30);
        emailField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_emailField = new GridBagConstraints();
        gbc_emailField.insets = new Insets(10, 10, 10, 10);
        gbc_emailField.gridx = 1;
        gbc_emailField.gridy = 7;
        gbc_emailField.gridwidth = 2;
        rightPanel.add(emailField, gbc_emailField);

        // Contact Number field
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_contactLabel = new GridBagConstraints();
        gbc_contactLabel.insets = new Insets(10, 10, 10, 10);
        gbc_contactLabel.gridx = 0;
        gbc_contactLabel.gridy = 8;
        rightPanel.add(contactLabel, gbc_contactLabel);

        contactField = new JTextField(30);
        contactField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_contactField = new GridBagConstraints();
        gbc_contactField.insets = new Insets(10, 10, 10, 10);
        gbc_contactField.gridx = 1;
        gbc_contactField.gridy = 8;
        gbc_contactField.gridwidth = 2;
        rightPanel.add(contactField, gbc_contactField);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
        gbc_passwordLabel.insets = new Insets(10, 10, 10, 10);
        gbc_passwordLabel.gridx = 0;
        gbc_passwordLabel.gridy = 9;
        rightPanel.add(passwordLabel, gbc_passwordLabel);

        passwordField = new JPasswordField(30);
        passwordField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.insets = new Insets(10, 10, 10, 10);
        gbc_passwordField.gridx = 1;
        gbc_passwordField.gridy = 9;
        gbc_passwordField.gridwidth = 2;
        rightPanel.add(passwordField, gbc_passwordField);

        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_confirmPasswordLabel = new GridBagConstraints();
        gbc_confirmPasswordLabel.insets = new Insets(10, 10, 10, 10);
        gbc_confirmPasswordLabel.gridx = 0;
        gbc_confirmPasswordLabel.gridy = 10;
        rightPanel.add(confirmPasswordLabel, gbc_confirmPasswordLabel);

        confirmPasswordField = new JPasswordField(30);
        confirmPasswordField.setForeground(new Color(0, 64, 128));
        GridBagConstraints gbc_confirmPasswordField = new GridBagConstraints();
        gbc_confirmPasswordField.insets = new Insets(10, 10, 10, 10);
        gbc_confirmPasswordField.gridx = 1;
        gbc_confirmPasswordField.gridy = 10;
        gbc_confirmPasswordField.gridwidth = 2;
        rightPanel.add(confirmPasswordField, gbc_confirmPasswordField);

        // Register button
        registerButton = new JButton("Register");
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        registerButton.setBackground(new Color(0, 64, 128));
        GridBagConstraints gbc_registerButton = new GridBagConstraints();
        gbc_registerButton.insets = new Insets(10, 10, 10, 10);
        gbc_registerButton.gridx = 1;
        gbc_registerButton.gridy = 11;
        gbc_registerButton.gridwidth = 1;
        gbc_registerButton.anchor = GridBagConstraints.LINE_START;
        rightPanel.add(registerButton, gbc_registerButton);

        // Add both panels to the frame
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.CENTER);
                
                        // Already have account button
                        alreadyHaveAccountButton = new JButton("Already have an account? Login");
                        alreadyHaveAccountButton.setForeground(new Color(0, 64, 128));
                        alreadyHaveAccountButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                        alreadyHaveAccountButton.setBackground(new Color(255, 255, 255));
                        GridBagConstraints gbc_alreadyHaveAccountButton = new GridBagConstraints();
                        gbc_alreadyHaveAccountButton.insets = new Insets(10, 10, 10, 10);
                        gbc_alreadyHaveAccountButton.gridx = 1;
                        gbc_alreadyHaveAccountButton.gridy = 12;
                        gbc_alreadyHaveAccountButton.gridwidth = 1;
                        gbc_alreadyHaveAccountButton.anchor = GridBagConstraints.LINE_START;
                        rightPanel.add(alreadyHaveAccountButton, gbc_alreadyHaveAccountButton);
                alreadyHaveAccountButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Switch to login page
                        dispose(); // Close the registration form
                        new LoginForm().setVisible(true); // Assuming LoginForm is another class
                    }
                });

        // Action listeners for buttons
        registerButton.addActionListener(new RegisterButtonListener());
    }


    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Retrieve values from form fields
            String schoolID = schoolIDField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = (String) genderBox.getSelectedItem();
            String yearLevel = (String) yearLevelBox.getSelectedItem();
            String section = (String) sectionBox.getSelectedItem();
            String username = usernameField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Check if passwords match
            if (password.equals(confirmPassword)) {
                // Check if email or username already exists
                if (emailExists(email)) {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Email already exists.");
                } else if (usernameExists(username)) {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Username already exists.");
                } else {
                    // Register user logic
                    int studentId = registerUser(schoolID, firstName, lastName, gender, yearLevel, section, username, email, contact, password);

                    if (studentId != -1) {
                        // Insert into tblfines
                        insertIntoFines(studentId);

                        // Add entries to tblattendance for each event
                        addAttendanceEntries(studentId);

                        // Update the amount in tblfines
                        updateFinesAmount(studentId);

                        JOptionPane.showMessageDialog(RegistrationForm.this, "Registration Successful!");
                        dispose(); // Close the registration form
                        new LoginForm().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(RegistrationForm.this, "Registration Failed.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(RegistrationForm.this, "Passwords do not match.");
            }
        }

        private int registerUser(String schoolID, String firstName, String lastName, String gender, String yearLevel, String section, String username, String email, String contact, String password) {
            int studentId = -1;
            String sql = "INSERT INTO tblstudent (School_ID, First_Name, Last_Name, Gender, Year_Level, Section, Contact_Number, Username, Email, Password, Role, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'student', 'active')";
            try (Connection conn = dbConnect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, schoolID);
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, gender);
                pstmt.setString(5, yearLevel);
                pstmt.setString(6, section);
                pstmt.setString(7, contact);
                pstmt.setString(8, username);
                pstmt.setString(9, email);
                pstmt.setString(10, password);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            studentId = generatedKeys.getInt(1);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return studentId;
        }

        private void insertIntoFines(int studentId) {
            String sql = "INSERT INTO tblfines (Student_ID, Amount) VALUES (?, 0)";
            try (Connection conn = dbConnect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void addAttendanceEntries(int studentId) {
            // Step 1: Fetch all Event_IDs and their Attendance values from tblevent
            String selectEventsSql = "SELECT Event_ID, Attendance FROM tblevent";
            
            try (Connection conn = dbConnect.getConnection();
                 PreparedStatement selectStmt = conn.prepareStatement(selectEventsSql);
                 ResultSet rs = selectStmt.executeQuery()) {
                
                // Prepare the SQL for inserting attendance records
                String insertSql = "INSERT INTO tblattendance (Student_ID, Event_ID, Attendance, `In`, `Out`, Balance) " +
                                   "VALUES (?, ?, 0, 0, 0, ?)";
                
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    // Iterate through the result set and insert records into tblattendance
                    while (rs.next()) {
                        int eventId = rs.getInt("Event_ID");
                        int attendance = rs.getInt("Attendance");
                        
                        // Calculate Balance based on Attendance value from tblevent
                        int balance = attendance * 25;

                        insertStmt.setInt(1, studentId);
                        insertStmt.setInt(2, eventId);
                        insertStmt.setInt(3, balance);
                        
                        insertStmt.addBatch(); // Add to batch
                    }
                    
                    // Execute all insert statements in batch
                    insertStmt.executeBatch();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        private void updateFinesAmount(int studentId) {
            String sql = "UPDATE tblfines SET Amount = (SELECT SUM(Attendance) * 25 FROM tblevent WHERE Status = 'ongoing') WHERE Student_ID = ?";
            try (Connection conn = dbConnect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private boolean emailExists(String email) {
            boolean exists = false;
            String sql = "SELECT * FROM tblstudent WHERE Email=? AND Status = 'active'";
            try (Connection conn = dbConnect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        exists = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return exists;
        }

        private boolean usernameExists(String username) {
            boolean exists = false;
            String sql = "SELECT * FROM tblstudent WHERE Username=? And Status = 'active'";
            try (Connection conn = dbConnect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        exists = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return exists;
        }
    }





    public static void main(String[] args) {
        // Launch the registration form
        SwingUtilities.invokeLater(() -> new RegistrationForm().setVisible(true));
    }
}
