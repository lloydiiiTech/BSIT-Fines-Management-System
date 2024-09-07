package Fines;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class PaymentHistoryForm extends JFrame {
    private JTable historyTable;
    private JButton backButton;

    public PaymentHistoryForm(String adminUsername) {
        setTitle("Payment History");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

            
            JLabel titleLabel = new JLabel("Payment History", JLabel.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);

            
            bannerPanel.add(logoPanel, BorderLayout.WEST);
            bannerPanel.add(titleLabel, BorderLayout.CENTER);

       
            getContentPane().add(bannerPanel, BorderLayout.NORTH);
            
                  
                    JPanel bottomPanel = new JPanel();
                    bottomPanel.setBackground(new Color(0, 64, 128));
                    bannerPanel.add(bottomPanel, BorderLayout.EAST);
                    bottomPanel.setLayout(new MigLayout("", "[53px]", "[21px][]"));
                    backButton = new JButton("Back");
                    bottomPanel.add(backButton, "cell 0 1,alignx left,aligny top");
                    
                            backButton.addActionListener(e -> {
                                dispose(); 
                            });

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error: Image resources not found. Ensure the images are placed in the correct location.");
        }

       
        historyTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(historyTable);
        getContentPane().add(tableScrollPane, BorderLayout.CENTER);

       
        SwingUtilities.invokeLater(this::loadPaymentHistory);
    }


    private void loadPaymentHistory() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();

        try {
        	String query = "SELECT p.Student_ID, "
        	        + "       s.School_ID, "
        	        + "       CONCAT(s.First_Name, ' ', s.Last_Name) AS Student_Name, "
        	        + "       s.Year_Level, "
        	        + "       s.Section, "
        	        + "       p.Amount, "
        	        + "       p.Payment_Date, "
        	        + "       p.Payment_Time, "
        	        + "       CONCAT(a.First_Name, ' ', a.Last_Name) AS Admin_Name, "
        	        + "       e.Event_Name "
        	        + "FROM tblpayment p "  // Added alias 'p' for tblpayment
        	        + "JOIN tblstudent s ON p.Student_ID = s.Student_ID "
        	        + "JOIN tblstudent a ON p.Admin_ID = a.Student_ID "
        	        + "JOIN tblevent e ON p.Event_ID = e.Event_ID "
        	        + "WHERE a.Role = 'admin';";


            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("School ID");
            tableModel.addColumn("Student Name");
            tableModel.addColumn("Year Level");
            tableModel.addColumn("Section");
            tableModel.addColumn("Amount");
            tableModel.addColumn("Event Name");
            tableModel.addColumn("Payment Date");
            tableModel.addColumn("Payment Time");
            tableModel.addColumn("Admin Name");

            while (rs.next()) {
                Object[] row = new Object[9];
                row[0] = rs.getString("School_ID");
                row[1] = rs.getString("Student_Name");
                row[2] = rs.getString("Year_Level");
                row[3] = rs.getString("Section");
                row[4] = rs.getDouble("Amount");
                row[5] = rs.getString("Event_Name");
                row[6] = rs.getString("Payment_Date");
                row[7] = rs.getString("Payment_Time");
                row[8] = rs.getString("Admin_Name");
                tableModel.addRow(row);
            }

            historyTable.setModel(tableModel);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading payment history.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
