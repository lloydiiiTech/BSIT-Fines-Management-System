package Fines;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

public class AdminHomePage extends JFrame {
    private JPanel eventsPanel;
    private int adminId;
    private int student;
    private String user;

    public AdminHomePage(int studentID, String username) {
        this.student = studentID;
        this.user = username;
        this.adminId = studentID; // Initialize admin ID

        setTitle("Admin Home - " + username);
        setSize(1200, 800); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Banner Panel
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(new Color(0, 64, 128));
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

        JLabel titleLabel = new JLabel("Admin Home Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        bannerPanel.add(logoPanel, BorderLayout.WEST);
        bannerPanel.add(titleLabel, BorderLayout.CENTER);

        // Top Container Panel
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(bannerPanel, BorderLayout.NORTH);

        // Top Panel with Buttons
        JPanel topPanel = new JPanel(new MigLayout("", "[61px][71px][67px]", "[21px][][][][][]"));
        bannerPanel.add(topPanel, BorderLayout.EAST);
        topPanel.setBackground(new Color(0, 64, 128));
        
        JButton profileAdmin = new JButton("Profile");
        profileAdmin.addActionListener(e -> {
            new AdminProfile(student, user).setVisible(true);
            dispose();
        });

        JButton payment = new JButton("Payment");
        payment.addActionListener(e -> {
            new PaymentForm(student, user).setVisible(true);
            dispose();
        });

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        topPanel.add(profileAdmin, "cell 0 3");
        topPanel.add(payment, "cell 1 3");
        topPanel.add(logoutButton, "cell 2 3");

        getContentPane().add(topContainer, BorderLayout.NORTH);

        // Events Panel with ScrollPane
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Load Events
        loadEvents();

        // Add Event Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> {
            new AddEvent(student, user).setVisible(true);
            dispose(); // This will close the AdminHomePage form
        });
        buttonPanel.add(addEventButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadEvents() {
        DbConnect dbConnect = new DbConnect();
        Connection con = dbConnect.getConnection();

        String sql = "SELECT e.Event_ID, e.Event_Name, e.Event_Type, e.Event_Description, e.Event_Date, e.Start, e.End, e.Attendance, " +
                     "CONCAT(s.First_Name, ' ', s.Last_Name) AS Admin_Name " +
                     "FROM tblevent e " +
                     "JOIN tblstudent s ON e.Admin_ID = s.Student_ID " +
                     "WHERE e.Status = 'ongoing'";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Clear the existing content in eventsPanel
            eventsPanel.removeAll();

            while (rs.next()) {
                int eventId = rs.getInt("Event_ID");
                String eventName = rs.getString("Event_Name");
                String eventType = rs.getString("Event_Type");
                String description = rs.getString("Event_Description");
                Date eventDate = rs.getDate("Event_Date");
                Time start = rs.getTime("Start");
                Time end = rs.getTime("End");
                String attendance = rs.getString("Attendance");
                String adminName = rs.getString("Admin_Name");

                // Create a container for each event
                JPanel eventPanel = new JPanel(new GridBagLayout());
                eventPanel.setBackground(new Color(173, 216, 230)); // Light blue background
                eventPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add smaller padding

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2); // Smaller insets
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.NONE;
                gbc.weightx = 1.0;

                // Add Name / Date / Start
                gbc.gridy = 0;
                gbc.gridx = 0;
                eventPanel.add(new JLabel(eventName), gbc);

                gbc.gridx = 1;
                eventPanel.add(new JLabel(eventDate.toString()), gbc);

                gbc.gridx = 2;
                eventPanel.add(new JLabel(start.toString()), gbc);

                // Add Type / Attendance / End / VIEW button
                gbc.gridy = 1;
                gbc.gridx = 0;
                eventPanel.add(new JLabel(eventType), gbc);

                gbc.gridx = 1;
                eventPanel.add(new JLabel(attendance), gbc);

                gbc.gridx = 2;
                eventPanel.add(new JLabel(end.toString()), gbc);

                gbc.gridx = 3;
                gbc.gridwidth = 2; // Button spans the last two columns
                gbc.anchor = GridBagConstraints.EAST;
                JButton viewButton = new JButton("VIEW");
                eventPanel.add(viewButton, gbc);

                viewButton.addActionListener(e -> {
                    // Open the event attendance form
                    new AttendanceForm(eventId, eventName, student, user).setVisible(true);
                });

                // Add Description / Created By
                gbc.gridy = 2;
                gbc.gridx = 0;
                gbc.gridwidth = 5; // Span all columns
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0.0; // No extra height
                JLabel descriptionLabel = new JLabel("<html>" + description + "<br/>Created By: " + adminName + "</html>");
                eventPanel.add(descriptionLabel, gbc);

                // Set a smaller fixed height for the event panel
                eventPanel.setPreferredSize(new Dimension(getWidth() - 20, 60)); // Smaller height of 60 pixels

                // Add the event panel to the main eventsPanel
                eventsPanel.add(eventPanel);
                eventsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Smaller space between events
            }

            eventsPanel.revalidate();
            eventsPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading events.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnect.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminHomePage(1, "username").setVisible(true));
    }
}
