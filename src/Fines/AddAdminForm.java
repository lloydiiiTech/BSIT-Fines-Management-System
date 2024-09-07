package Fines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddAdminForm extends JFrame {
    // Declare components
    private JTextField schoolIDField, firstNameField, lastNameField, emailField, contactField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton submitButton, cancelButton;
    private String adminUsername; // Admin's username
    private DbConnect dbConnect; // Database connection

    public AddAdminForm(String adminUsername, DbConnect dbConnect) {
        this.adminUsername = adminUsername;
        this.dbConnect = dbConnect;

        setTitle("Add Admin");
        setSize(1200, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(0, 64, 128)); // Navy blue background
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

            JLabel titleLabel = new JLabel("Add Admin", JLabel.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);

            bannerPanel.add(logoPanel, BorderLayout.WEST);
            bannerPanel.add(titleLabel, BorderLayout.CENTER);

            getContentPane().add(bannerPanel, BorderLayout.NORTH);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error: Image resources not found. Ensure the images are placed in the correct location.");
        }

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new BorderLayout());

        JPanel personalPanel = new JPanel();
        personalPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));
        personalPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.anchor = GridBagConstraints.WEST;
        
        // Add components to personalPanel with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        personalPanel.add(new JLabel("Admin Username:"), gbc);

        gbc.gridx = 1;
        JLabel adminUsernameLabel = new JLabel(adminUsername);
        personalPanel.add(adminUsernameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("School ID:"), gbc);

        gbc.gridx = 1;
        schoolIDField = new JTextField(20); // Column count to set preferred width
        personalPanel.add(schoolIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        firstNameField = new JTextField(20); // Column count to set preferred width
        personalPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        lastNameField = new JTextField(20); // Column count to set preferred width
        personalPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20); // Column count to set preferred width
        personalPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20); // Column count to set preferred width
        personalPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("Contact Number:"), gbc);

        gbc.gridx = 1;
        contactField = new JTextField(20); // Column count to set preferred width
        personalPanel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20); // Column count to set preferred width
        personalPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalPanel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20); // Column count to set preferred width
        personalPanel.add(confirmPasswordField, gbc);

        JPanel buttonPanel = new JPanel();
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        contentPane.add(personalPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(contentPane);

        submitButton.addActionListener(new AddAdminButtonListener());
        cancelButton.addActionListener(e -> dispose());
    }
    private class AddAdminButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Retrieve values from form fields
            String schoolID = schoolIDField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(AddAdminForm.this, "Passwords do not match.");
                return;
            }

            // Check if username or email already exists
            if (usernameExists(username) || emailExists(email)) {
                JOptionPane.showMessageDialog(AddAdminForm.this, "Username or email already exists.");
                return;
            }

            // Register new admin
            registerAdmin(schoolID, firstName, lastName, username, email, contact, password);
        }
    }

    private boolean usernameExists(String username) {
        boolean exists = false;
        try (Connection con = dbConnect.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM tblstudent WHERE Username = ? AND Status = 'active'")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    exists = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exists;
    }

    private boolean emailExists(String email) {
        boolean exists = false;
        try (Connection con = dbConnect.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM tblstudent WHERE Email = ? AND Status = 'active'")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    exists = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exists;
    }

    private void registerAdmin(String schoolID, String firstName, String lastName, String username, String email, String contact, String password) {
        String sql = "INSERT INTO tblstudent (School_ID, First_Name, Last_Name, Username, Email, Contact_Number, Password, Role, Added_by, Status) VALUES (?, ?, ?, ?, ?, ?, ?, 'admin', ?, 'active')";
        try (Connection con = dbConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, schoolID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, username);
            ps.setString(5, email);
            ps.setString(6, contact);
            ps.setString(7, password);
            ps.setString(8, adminUsername); // Set the admin's username who added this admin
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Admin registered successfully.");
            dispose(); // Close the form after successful registration
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering admin: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a DbConnect instance
            DbConnect dbConnect = new DbConnect();
            new AddAdminForm("admin", dbConnect).setVisible(true);
        });
    }

}
