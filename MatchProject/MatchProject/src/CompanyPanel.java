import Business.Company; // Adjust this import to your package structure
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class CompanyPanel extends JPanel {
    private JTable companiesTable;
    private JButton addCompanyButton;
    private Company companyBusinessLayer; // Adjust this to match your actual business layer class name

    public CompanyPanel() {
        companyBusinessLayer = new Company(); // Initialize your business layer here
        setLayout(new BorderLayout());

        // Initialize UI components for company list
        companiesTable = new JTable(new DefaultTableModel(null, new String[]{"ID", "Name", "Industry", "Email", "Phone"}));
        refreshCompanyTable(); // Load company data

        addCompanyButton = new JButton("Add Company");
        // Layout components
        add(new JScrollPane(companiesTable), BorderLayout.CENTER);
        add(addCompanyButton, BorderLayout.SOUTH);

        // Add action listener for adding a new company
        addCompanyButton.addActionListener(e -> addCompany());
    }

    private void refreshCompanyTable() {
        DefaultTableModel model = (DefaultTableModel) companiesTable.getModel();
        model.setRowCount(0); // Clear the existing data
        List<Model.Company> companies = companyBusinessLayer.GetAllCompanies();
        for (Model.Company company : companies) {
            model.addRow(new Object[]{
                    company.ID,
                    company.Name,
                    company.Industry,
                    company.Email,
                    company.PhoneNumber
            });
        }
    }

    private void addCompany() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField nameField = new JTextField();
        JTextField industryField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
    
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Industry:"));
        panel.add(industryField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
    
        // Show the dialog and get the result
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Company Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Model.Company newCompany = new Model.Company();
            newCompany.Name = nameField.getText();
            newCompany.Industry = industryField.getText();
            newCompany.Email = emailField.getText();
            newCompany.PhoneNumber = phoneField.getText();
            // Assuming LogInUserID is managed elsewhere in your application logic
    
            // Call business layer to add company
            companyBusinessLayer.CreateNewCompany(newCompany); // Method as defined in your business layer
    
            // Refresh the company table model
            refreshCompanyTable();
        }
    }
    
}
