import Business.CandidateBusinessLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MatchingCompanyProfilesPanel extends JPanel {
    private int candidateID;
    private JTable companyTable;
    CandidateBusinessLayer candidateBusinessLayer;
    private JButton backtoDashboardButton;

    public MatchingCompanyProfilesPanel() {
        this.candidateID = LoginPanel.loginIDs;
        candidateBusinessLayer = new CandidateBusinessLayer();

        List<Model.Company> matchingCompanies = candidateBusinessLayer.getMatchingCompanyProfiles(candidateID);
        setLayout(new BorderLayout());

        // Column names for the table
        String[] columnNames = {"Company ID", "Company Name", "Industry", "Email", "Phone Number", "Apply"};

        // Data for the table, plus one extra column for the Apply button
        Object[][] data = new Object[matchingCompanies.size()][6];
        for (int i = 0; i < matchingCompanies.size(); i++) {
            Model.Company company = matchingCompanies.get(i);
            data[i][0] = company.ID;
            data[i][1] = company.Name;
            data[i][2] = company.Industry;
            data[i][3] = company.Email;
            data[i][4] = company.PhoneNumber;
            data[i][5] = "Apply"; // Placeholder for the Apply button
        }

        // Creating the table with the data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the Apply button column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 5 ? JButton.class : super.getColumnClass(columnIndex);
            }
        };
        companyTable = new JTable(model);
        companyTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        companyTable.setFillsViewportHeight(true);

        // Adding the Apply button to each row
        companyTable.getColumn("Apply").setCellRenderer(new ButtonRenderer());
        companyTable.getColumn("Apply").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Adding the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(companyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Back to Dashboard Button
        backtoDashboardButton = new JButton("Back to Dashboard");
        backtoDashboardButton.addActionListener(e -> goBackToDashboard());
        add(backtoDashboardButton, BorderLayout.SOUTH);
    }

    private void goBackToDashboard() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        // Assuming DashboardPanel is the name of your dashboard panel class
        CandidateDashboardPanel dashboardPanel = new CandidateDashboardPanel();
        frame.getContentPane().add(dashboardPanel);
        frame.revalidate();
        frame.repaint();
    }

    // ButtonRenderer class
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // ButtonEditor class
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Logic to handle the application process
                int selectedRow = companyTable.getSelectedRow();
                int companyID = (Integer) companyTable.getValueAt(selectedRow, 0);
                applyToCompany(candidateID, companyID);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    private void applyToCompany(int candidateID, int companyID) {
         boolean isApplied = candidateBusinessLayer.applyToCompany(candidateID, companyID);

        if (isApplied) {
            JOptionPane.showMessageDialog(this, "Successfully applied to Company ID: " + companyID);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to apply to Company ID: " + companyID);
        }
    }



    // Test main method to demonstrate the panel
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Matching Company Profiles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MatchingCompanyProfilesPanel()); // Example candidateID
            frame.pack();
            frame.setVisible(true);
        });
    }
}
