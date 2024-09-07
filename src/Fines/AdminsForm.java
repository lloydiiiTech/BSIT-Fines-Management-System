package Fines;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

public class AdminsForm extends JFrame {
    private JTable adminsTable;
    private JTextField adminIdField;
    private JTextField adminNameField;
    private String selectedAdminId = "";

    public AdminsForm(int studentID, String adminUsername) {
        setTitle("Manage Admins");
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

          
            JLabel titleLabel = new JLabel("Manage Admins", JLabel.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);

       
            bannerPanel.add(logoPanel, BorderLayout.WEST);
            bannerPanel.add(titleLabel, BorderLayout.CENTER);

          
            getContentPane().add(bannerPanel, BorderLayout.NORTH);
            
                  
                    JPanel backPanel = new JPanel();
                    backPanel.setBackground(new Color(0, 64, 128));
                    bannerPanel.add(backPanel, BorderLayout.EAST);
                    backPanel.setLayout(new MigLayout("", "[53px]", "[21px][]"));
                    JButton backButton = new JButton("Back");
                    backPanel.add(backButton, "cell 0 1,alignx left,aligny top");
                    
                            // Back button action
                            backButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    new UsersForm(studentID, adminUsername).setVisible(true);
                                    dispose();
                                }
                            });
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error: Image resources not found. Ensure the images are placed in the correct location.");
        }

     
        adminsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(adminsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

     
        JPanel footerPanel = new JPanel(new BorderLayout());


        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel adminIdLabel = new JLabel("Admin ID:");
        adminIdField = new JTextField(15);
        JLabel adminNameLabel = new JLabel("Admin Name:");
        adminNameField = new JTextField(15);
        JButton updateButton = new JButton("Update Status");
        updatePanel.add(adminIdLabel);
        updatePanel.add(adminIdField);
        updatePanel.add(adminNameLabel);
        updatePanel.add(adminNameField);
        updatePanel.add(updateButton);

        footerPanel.add(updatePanel, BorderLayout.SOUTH);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);

        // Load admin data into the table
        loadAdminData();

        // Update button action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!selectedAdminId.isEmpty()) {
                    if ("1".equals(selectedAdminId)) { // Main admin check
                        JOptionPane.showMessageDialog(AdminsForm.this, "This admin cannot be removed.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        updateAdminStatus(selectedAdminId);
                        loadAdminData(); // Refresh table
                        adminIdField.setText(""); // Clear text field
                        adminNameField.setText(""); // Clear name field
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminsForm.this, "Please select an admin.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add a listener to handle row selection
        adminsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = adminsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedAdminId = (String) adminsTable.getValueAt(selectedRow, 0); // Assuming the first column is Student_ID
                    String firstName = (String) adminsTable.getValueAt(selectedRow, 1);
                    String lastName = (String) adminsTable.getValueAt(selectedRow, 2);
                    adminIdField.setText(selectedAdminId);
                    adminNameField.setText(firstName + " " + lastName);
                }
            }
        });
    }
    private void loadAdminData() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();
        String sql = "SELECT `Student_ID`, `First_Name`, `Last_Name`, `Email`, `Username`, `Contact_Number` " +
                     "FROM `tblstudent` WHERE Role = 'admin' AND Status = 'active'";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Create table model
            DefaultTableModel model = new DefaultTableModel(new String[] {"Student_ID", "First_Name", "Last_Name", "Email", "Username", "Contact_Number"}, 0);

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getString("Student_ID"),
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Email"),
                    rs.getString("Username"),
                    rs.getString("Contact_Number")
                });
            }

            adminsTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading admin data.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }

    private void updateAdminStatus(String adminId) {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();
        String sql = "UPDATE `tblstudent` SET `Status` = 'deactivated' WHERE `Student_ID` = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, adminId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Admin status updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No admin found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating admin status.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminsForm(1, "username").setVisible(true));
    }
}
