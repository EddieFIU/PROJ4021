import Business.CompanyBusinessLayer;

import javax.swing.*;
import java.awt.*;

public class CompanyProjectPanel extends JPanel {
    private JTextField projectNameField, projectManagerNameField, projectManagerEmailField;
    private JComboBox<Integer> companyIDComboBox;
    private JButton addButton;
    private JButton backtoDashboardButton;

    CompanyBusinessLayer companyBusinessLayer;

    public CompanyProjectPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        companyBusinessLayer = new CompanyBusinessLayer();
        projectNameField = new JTextField(20);
        projectManagerNameField = new JTextField(20);
        projectManagerEmailField = new JTextField(20);
        companyIDComboBox = new JComboBox<>(); // Populate this with company IDs
        addButton = new JButton("Add Project");

        add(createLabeledField("Project Name:", projectNameField));
        add(createLabeledField("Project Manager Name:", projectManagerNameField));
        add(createLabeledField("Project Manager Email:", projectManagerEmailField));
        add(addButton);

        addButton.addActionListener(e -> addProject());

        // Back to Dashboard Button
        backtoDashboardButton = new JButton("Back to Dashboard");
        backtoDashboardButton.addActionListener(e -> goBackToDashboard());
        add(backtoDashboardButton);
    }

    private JPanel createLabeledField(String label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void addProject() {
        Model.CompanyProject companyProject = new Model.CompanyProject();
        companyProject.setProjectName(projectNameField.getText());
        companyProject.setProjectManagerName(projectManagerNameField.getText());
        companyProject.setProjectManagerEmail(projectManagerEmailField.getText());
        companyProject.CompanyID = LoginPanel.loginIDs;
        boolean isSuccess = companyBusinessLayer.addCompanyProject(companyProject);
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Project Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add project", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToDashboard() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        // Assuming DashboardPanel is the name of your dashboard panel class
        companyDashboardPanel dashboardPanel = new companyDashboardPanel();
        frame.getContentPane().add(dashboardPanel);
        frame.revalidate();
        frame.repaint();
    }
}
