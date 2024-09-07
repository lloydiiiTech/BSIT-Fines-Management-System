package Fines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangePass extends JFrame {
    private JTextField usernameField;
    private JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    private JButton submitButton, cancelButton;

    public ChangePass(String username) {
        setTitle("Change Password");
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

       
        JLabel titleLabel = new JLabel("Change Password", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        
        bannerPanel.add(logoPanel, BorderLayout.WEST);
        bannerPanel.add(titleLabel, BorderLayout.CENTER);

        
        add(bannerPanel, BorderLayout.NORTH);

      
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(username);
        usernameField.setEditable(false);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Old Password:"));
        oldPasswordField = new JPasswordField();
        formPanel.add(oldPasswordField);

        formPanel.add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField();
        formPanel.add(newPasswordField);

        formPanel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        add(formPanel, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);
        cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(ChangePass.this, "New passwords do not match.");
                    return;
                }

                changePassword(username, oldPassword, newPassword);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void changePassword(String username, String oldPassword, String newPassword) {
        // Implement the method to change the user's password
        // Connect to the database and perform the password update

        String sql = "UPDATE tblstudent SET Password = ? WHERE Username = ? AND Password = ? AND Status ='active'";
        try (Connection conn = new DbConnect().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.setString(3, oldPassword);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Password changed successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect old password or username.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while changing the password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChangePass("username").setVisible(true));
    }
}

