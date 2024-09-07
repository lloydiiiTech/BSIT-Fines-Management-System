package Fines;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import net.miginfocom.swing.MigLayout;

public class TransactionForm extends JFrame {
    private int studentID;
    private JTable transactionsTable;

    public TransactionForm(int studentID) {
        this.studentID = studentID;

        setTitle("Transaction History");
        setSize(1200, 800); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(0, 64, 128)); 
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

       
        JLabel titleLabel = new JLabel("Transaction History", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        
        bannerPanel.add(logoPanel, BorderLayout.WEST);
        bannerPanel.add(titleLabel, BorderLayout.CENTER);

       
        getContentPane().add(bannerPanel, BorderLayout.NORTH);
        
               
                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(new Color(0, 64, 128));
                bannerPanel.add(buttonPanel, BorderLayout.EAST);
                buttonPanel.setLayout(new MigLayout("", "[53px]", "[21px][][]"));
                JButton backButton = new JButton("Back");
                buttonPanel.add(backButton, "cell 0 2,alignx left,aligny top");
                
                        backButton.addActionListener(e -> dispose());

       
        transactionsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

     
        loadTransactionData();
    }

    private void loadTransactionData() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();
        String sqlTransactions = "SELECT p.Payment_Date, p.Payment_Time, p.Event_ID, p.Amount, " +
                                 "s.First_Name AS Admin_First_Name, s.Last_Name AS Admin_Last_Name, " +
                                 "s.Contact_Number, s.Email " +
                                 "FROM tblpayment p " +
                                 "JOIN tblstudent s ON p.Admin_ID = s.Student_ID " +
                                 "WHERE p.Student_ID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sqlTransactions)) {
            pstmt.setInt(1, studentID);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Create table model
                DefaultTableModel model = new DefaultTableModel(new String[] {
                     "Event Name", "Amount", "Payment Date", "Payment Time", "Prepared by", "Contact Number", "Email"
                }, 0);

                while (rs.next()) {
                    int eventId = rs.getInt("Event_ID");
                    String eventName = getEventName(eventId, con);
                    String adminName = rs.getString("Admin_First_Name") + " " + rs.getString("Admin_Last_Name");
                    String contactNumber = rs.getString("Contact_Number");
                    String email = rs.getString("Email");
                    double amount = rs.getDouble("Amount");

                    model.addRow(new Object[] {
                        
                        
                        eventName,
                        amount,
                        rs.getString("Payment_Date"),
                        rs.getString("Payment_Time"),
                        adminName,
                        contactNumber,
                        email
                    });
                }

                transactionsTable.setModel(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading transaction data.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }

    private String getEventName(int eventId, Connection con) throws SQLException {
        String sqlEvent = "SELECT Event_Name FROM tblEvent WHERE Event_ID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sqlEvent)) {
            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Event_Name");
                }
            }
        }
        return "Unknown";
    }
}