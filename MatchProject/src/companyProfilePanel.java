import Business.CompanyBusinessLayer;
import Model.Company;

import javax.swing.*;
import java.awt.*;

public class companyProfilePanel extends JPanel {
    private CompanyBusinessLayer companyBusinessLayer;
    private Company companyData;

    private JTextField nameField, industryField, emailField, phoneField;
    private JButton actionButton; // Unified button for both create and update actions

    public companyProfilePanel() {
        // Setting the layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        companyBusinessLayer = new CompanyBusinessLayer();

        nameField = new JTextField(20);
        industryField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);

        // Adding components to the panel
        add(createLabeledField("Name:", nameField));
        add(createLabeledField("Industry:", industryField));
        add(createLabeledField("Email:", emailField));
        add(createLabeledField("Phone:", phoneField));

        // Creating the action button
        actionButton = new JButton();
        add(actionButton);

        // Check for existing data and update UI accordingly
        checkAndFillData();

        // Styling
        styleComponents(nameField, industryField, emailField, phoneField, actionButton);

        // Set up action button listener
        setUpActionButtonListener();
    }

    private void checkAndFillData() {
        System.out.println(LoginPanel.loginIDs);
        companyData = companyBusinessLayer.GetCompanyByLoginID(LoginPanel.loginIDs);
        if (companyData != null) {
            // Fill the fields with company data
            nameField.setText(companyData.getName());
            industryField.setText(companyData.getIndustry());
            emailField.setText(companyData.getEmail());
            phoneField.setText(companyData.getPhoneNumber());

            actionButton.setText("Update Profile");
        } else {
            actionButton.setText("Create Profile");
        }
    }

    private JPanel createLabeledField(String label, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void styleComponents(JComponent... components) {
        for (JComponent component : components) {
            if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            } else if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
            }
        }
    }

    private void setUpActionButtonListener() {
        actionButton.addActionListener(e -> {
            if (companyData == null) {
                addCompany();
            } else {
                updateCompany();
            }
        });
    }

    private void addCompany() {
        // Check if any field is empty
        if (nameField.getText().trim().isEmpty() || industryField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Incomplete Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Model.Company newCompany = new Model.Company();
        newCompany.Name = nameField.getText();
        newCompany.Industry = industryField.getText();
        newCompany.Email = emailField.getText();
        newCompany.PhoneNumber = phoneField.getText();
         companyBusinessLayer.CreateNewCompany(newCompany);

            JOptionPane.showMessageDialog(this, "Company successfully created", "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    private void updateCompany() {
        // Check if any field is empty
        if (nameField.getText().trim().isEmpty() || industryField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Incomplete Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Model.Company updatedCompany = new Model.Company();
        updatedCompany.Name = nameField.getText();
        updatedCompany.Industry = industryField.getText();
        updatedCompany.Email = emailField.getText();
        updatedCompany.PhoneNumber = phoneField.getText();
        updatedCompany.LogInUserID = LoginPanel.loginIDs; // Assuming you have this ID
           companyBusinessLayer.updateCompany(updatedCompany);

            JOptionPane.showMessageDialog(this, "Company successfully updated", "Success", JOptionPane.INFORMATION_MESSAGE);

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
