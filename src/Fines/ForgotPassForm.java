package Fines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassForm extends JFrame {
    private DbConnect dbConnect;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ForgotPassForm() {
        dbConnect = new DbConnect();
        setTitle("Password Reset Form");
        setSize(1200, 800); 
        setMinimumSize(new Dimension(800, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        
        getContentPane().setLayout(new BorderLayout());

        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 64, 128)); 
        leftPanel.setPreferredSize(new Dimension(300, 400));
        leftPanel.setLayout(new GridBagLayout()); 

        
        ImageIcon imageIcon1 = new ImageIcon(getClass().getResource("/resources/ccs1.png"));
        Image image1 = imageIcon1.getImage(); 
        Image scaledImage1 = image1.getScaledInstance(100, 100, Image.SCALE_SMOOTH); 

       
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/ccs2.png"));
        Image image2 = imageIcon2.getImage(); 
        Image scaledImage2 = image2.getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
        JLabel imageLabel1 = new JLabel(new ImageIcon(scaledImage1));
        GridBagConstraints gbc_imageLabel1 = new GridBagConstraints();
        gbc_imageLabel1.insets = new Insets(10, 10, 10, 5);
        gbc_imageLabel1.gridx = 0;
        gbc_imageLabel1.gridy = 0;
        gbc_imageLabel1.anchor = GridBagConstraints.LINE_END;
        leftPanel.add(imageLabel1, gbc_imageLabel1);
        JLabel imageLabel2 = new JLabel(new ImageIcon(scaledImage2));
        GridBagConstraints gbc_imageLabel2 = new GridBagConstraints();
        gbc_imageLabel2.insets = new Insets(10, 5, 10, 10);
        gbc_imageLabel2.gridx = 1;
        gbc_imageLabel2.gridy = 0;
        gbc_imageLabel2.anchor = GridBagConstraints.LINE_START;
        leftPanel.add(imageLabel2, gbc_imageLabel2);

        
        JLabel welcomeLabel = new JLabel("<html><div style='text-align:center;'>Password<br>Reset Form</div></html>");
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

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
        gbc_usernameLabel.insets = new Insets(10, 10, 10, 10);
        gbc_usernameLabel.gridx = 0;
        gbc_usernameLabel.gridy = 0;
        gbc_usernameLabel.anchor = GridBagConstraints.LINE_END;
        rightPanel.add(usernameLabel, gbc_usernameLabel);

        usernameField = new JTextField(15);
        usernameField.setForeground(new Color(0, 64, 128));
        usernameField.setBackground(Color.WHITE); // Explicit background
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_usernameField = new GridBagConstraints();
        gbc_usernameField.insets = new Insets(10, 10, 10, 10);
        gbc_usernameField.gridx = 1;
        gbc_usernameField.gridy = 0;
        gbc_usernameField.anchor = GridBagConstraints.LINE_START;
        rightPanel.add(usernameField, gbc_usernameField);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_emailLabel = new GridBagConstraints();
        gbc_emailLabel.insets = new Insets(10, 10, 10, 10);
        gbc_emailLabel.gridx = 0;
        gbc_emailLabel.gridy = 1;
        gbc_emailLabel.anchor = GridBagConstraints.LINE_END;
        rightPanel.add(emailLabel, gbc_emailLabel);

        emailField = new JTextField(15);
        emailField.setForeground(new Color(0, 64, 128));
        emailField.setBackground(Color.WHITE); 
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_emailField = new GridBagConstraints();
        gbc_emailField.insets = new Insets(10, 10, 10, 10);
        gbc_emailField.gridx = 1;
        gbc_emailField.gridy = 1;
        gbc_emailField.anchor = GridBagConstraints.LINE_START;
        rightPanel.add(emailField, gbc_emailField);

        // New Password field
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_newPasswordLabel = new GridBagConstraints();
        gbc_newPasswordLabel.insets = new Insets(10, 10, 10, 10);
        gbc_newPasswordLabel.gridx = 0;
        gbc_newPasswordLabel.gridy = 2;
        gbc_newPasswordLabel.anchor = GridBagConstraints.LINE_END;
        rightPanel.add(newPasswordLabel, gbc_newPasswordLabel);

        newPasswordField = new JPasswordField(15);
        newPasswordField.setForeground(new Color(0, 64, 128));
        newPasswordField.setBackground(Color.WHITE); 
        newPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
        gbc_newPasswordField.insets = new Insets(10, 10, 10, 10);
        gbc_newPasswordField.gridx = 1;
        gbc_newPasswordField.gridy = 2;
        gbc_newPasswordField.anchor = GridBagConstraints.LINE_START;
        rightPanel.add(newPasswordField, gbc_newPasswordField);

        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_confirmPasswordLabel = new GridBagConstraints();
        gbc_confirmPasswordLabel.insets = new Insets(10, 10, 10, 10);
        gbc_confirmPasswordLabel.gridx = 0;
        gbc_confirmPasswordLabel.gridy = 3;
        gbc_confirmPasswordLabel.anchor = GridBagConstraints.LINE_END;
        rightPanel.add(confirmPasswordLabel, gbc_confirmPasswordLabel);

        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setForeground(new Color(0, 64, 128));
        confirmPasswordField.setBackground(Color.WHITE); 
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        GridBagConstraints gbc_confirmPasswordField = new GridBagConstraints();
        gbc_confirmPasswordField.insets = new Insets(10, 10, 10, 10);
        gbc_confirmPasswordField.gridx = 1;
        gbc_confirmPasswordField.gridy = 3;
        gbc_confirmPasswordField.anchor = GridBagConstraints.LINE_START;
        rightPanel.add(confirmPasswordField, gbc_confirmPasswordField);

        // Reset button
        JButton resetButton = new JButton("Reset Password");
        resetButton.setForeground(new Color(255, 255, 255));
        resetButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        resetButton.setBackground(new Color(0, 64, 128)); // Blue color
        GridBagConstraints gbc_resetButton = new GridBagConstraints();
        gbc_resetButton.insets = new Insets(10, 10, 10, 10);
        gbc_resetButton.gridx = 1;
        gbc_resetButton.gridy = 4;
        gbc_resetButton.anchor = GridBagConstraints.LINE_START;
        rightPanel.add(resetButton, gbc_resetButton);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setForeground(new Color(0, 64, 128));
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cancelButton.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc_cancelButton = new GridBagConstraints();
        gbc_cancelButton.insets = new Insets(10, 10, 10, 10);
        gbc_cancelButton.gridx = 1;
        gbc_cancelButton.gridy = 5;
        gbc_cancelButton.anchor = GridBagConstraints.LINE_START;
        rightPanel.add(cancelButton, gbc_cancelButton);

        // Add both panels to the frame
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        resetButton.addActionListener(new ResetButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
    }

    private void customizeButton(JButton button, Color bgColor, Color fgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (newPassword.equals(confirmPassword)) {
                dbConnect.connect();

                if (isUserValid(username, email)) {
                    updatePassword(username, newPassword);
                    JOptionPane.showMessageDialog(ForgotPassForm.this, "Password updated successfully.");
                    dispose();
                    new LoginForm().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(ForgotPassForm.this, "Invalid username or email.");
                }

                dbConnect.close();
            } else {
                JOptionPane.showMessageDialog(ForgotPassForm.this, "Passwords do not match.");
            }
        }

        private boolean isUserValid(String username, String email) {
            boolean isValid = false;
            String sql = "SELECT * FROM tblstudent WHERE Username = ? AND Email = ? AND Status = 'active'";
            try (PreparedStatement pstmt = dbConnect.getConnection().prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    isValid = true;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return isValid;
        }

        private void updatePassword(String username, String newPassword) {
            String sql = "UPDATE tblstudent SET Password = ? WHERE Username = ? AND Status = 'active'";
            try (PreparedStatement pstmt = dbConnect.getConnection().prepareStatement(sql)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, username);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Close the current form and return to the LoginForm
            dispose();
            new LoginForm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ForgotPassForm().setVisible(true));
    }
}
