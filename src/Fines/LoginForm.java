package Fines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private DbConnect dbConnect;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        dbConnect = new DbConnect();
        setTitle("Login Form");
        setSize(1200, 800); 
        setMinimumSize(new Dimension(800, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        getContentPane().setLayout(new BorderLayout());

        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 64, 128)); // Blue color
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
        imageLabel2.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc_imageLabel2 = new GridBagConstraints();
        gbc_imageLabel2.insets = new Insets(10, 5, 10, 10);
        gbc_imageLabel2.gridx = 1;
        gbc_imageLabel2.gridy = 0;
        gbc_imageLabel2.anchor = GridBagConstraints.LINE_START;
        leftPanel.add(imageLabel2, gbc_imageLabel2);

        
        JLabel welcomeLabel = new JLabel("<html><div style='text-align:center;'>Welcome<br>to CCS FinesMonitoring System</div></html>");
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
        rightPanel.setBackground(new Color(255, 255, 255));
        SpringLayout sl_rightPanel = new SpringLayout();
        rightPanel.setLayout(sl_rightPanel);

        // Login title
        JLabel lblLogin = new JLabel("Login");
        sl_rightPanel.putConstraint(SpringLayout.NORTH, lblLogin, 232, SpringLayout.NORTH, rightPanel);
        sl_rightPanel.putConstraint(SpringLayout.WEST, lblLogin, 403, SpringLayout.WEST, rightPanel);
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblLogin.setForeground(new Color(0, 64, 128));
        rightPanel.add(lblLogin);

        // Email field
        JLabel usernameLabel = new JLabel("Email");
        sl_rightPanel.putConstraint(SpringLayout.NORTH, usernameLabel, 296, SpringLayout.NORTH, rightPanel);
        sl_rightPanel.putConstraint(SpringLayout.WEST, usernameLabel, 264, SpringLayout.WEST, rightPanel);
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rightPanel.add(usernameLabel);

        usernameField = new JTextField(20);
        sl_rightPanel.putConstraint(SpringLayout.NORTH, usernameField, 293, SpringLayout.NORTH, rightPanel);
        sl_rightPanel.putConstraint(SpringLayout.WEST, usernameField, 329, SpringLayout.WEST, rightPanel);
        usernameField.setForeground(new Color(0, 64, 128));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rightPanel.add(usernameField);

        // Password field
        JLabel passwordLabel = new JLabel("Password");
        sl_rightPanel.putConstraint(SpringLayout.NORTH, passwordLabel, 347, SpringLayout.NORTH, rightPanel);
        sl_rightPanel.putConstraint(SpringLayout.WEST, passwordLabel, 231, SpringLayout.WEST, rightPanel);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rightPanel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        sl_rightPanel.putConstraint(SpringLayout.NORTH, passwordField, 344, SpringLayout.NORTH, rightPanel);
        sl_rightPanel.putConstraint(SpringLayout.WEST, passwordField, 329, SpringLayout.WEST, rightPanel);
        passwordField.setForeground(new Color(0, 64, 128));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rightPanel.add(passwordField);

        // Forgot Password button
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        sl_rightPanel.putConstraint(SpringLayout.NORTH, forgotPasswordButton, 6, SpringLayout.SOUTH, passwordField);
        sl_rightPanel.putConstraint(SpringLayout.WEST, forgotPasswordButton, 0, SpringLayout.WEST, usernameField);
        forgotPasswordButton.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        forgotPasswordButton.setForeground(new Color(0, 0, 255));
        forgotPasswordButton.setBackground(new Color(255, 255, 255));
        rightPanel.add(forgotPasswordButton);

        // Login button
        JButton loginButton = new JButton("Login");
        sl_rightPanel.putConstraint(SpringLayout.NORTH, loginButton, 17, SpringLayout.SOUTH, forgotPasswordButton);
        sl_rightPanel.putConstraint(SpringLayout.WEST, loginButton, 0, SpringLayout.WEST, lblLogin);
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        loginButton.setBackground(new Color(0, 64, 128));
        rightPanel.add(loginButton);

        // Register button
        JButton registerButton = new JButton("Sign Up");
        sl_rightPanel.putConstraint(SpringLayout.NORTH, registerButton, 17, SpringLayout.SOUTH, loginButton);
        sl_rightPanel.putConstraint(SpringLayout.WEST, registerButton, 392, SpringLayout.WEST, rightPanel);
        registerButton.setForeground(new Color(0, 64, 128));
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        registerButton.setBackground(new Color(255, 255, 255));
        rightPanel.add(registerButton);

        // Add both panels to the frame
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        loginButton.addActionListener(new LoginButtonListener());
        registerButton.addActionListener(new RegisterButtonListener());
        forgotPasswordButton.addActionListener(new ForgotPasswordButtonListener());
    }
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            dbConnect.connect();

            User user = authenticateUser(username, password);

            if (user != null) {
                String role = user.getRole();

                if ("student".equalsIgnoreCase(role)) {
                    new StudentHomePage(user.getId(), user.getUsername()).setVisible(true);
                } else if ("admin".equalsIgnoreCase(role)) {
                    new AdminHomePage(user.getId(), user.getUsername()).setVisible(true);
                }

                dispose();
            } 

            dbConnect.close();
        }

        private User authenticateUser(String username, String password) {
            User user = null;
            String sql = "SELECT Student_ID, Username, Password, Role FROM tblstudent WHERE Email = ? OR Username = ? AND Status = 'active' ";
            try (PreparedStatement pstmt = dbConnect.getConnection().prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String dbPassword = rs.getString("Password");
                    if (dbPassword.equals(password)) {
                        int id = rs.getInt("Student_ID");
                        String dbUsername = rs.getString("Username");
                        String role = rs.getString("Role");
                        user = new User(id, dbUsername, role);
                    } else {
                        // Password is incorrect
                        JOptionPane.showMessageDialog(LoginForm.this, "Incorrect password.");
                    }
                } else {
                    // Username/Email does not exist
                    JOptionPane.showMessageDialog(LoginForm.this, "Email does not exist.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return user;
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegistrationForm().setVisible(true);
        }
    }

    private class ForgotPasswordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ForgotPassForm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
