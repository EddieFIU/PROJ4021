import Business.CompanyBusinessLayer;
import java.util.List;

import Model.CompanyProject;

import javax.swing.*;
import java.awt.*;

public class JobPostingPanel extends JPanel {
    private JTextField requirementField;
    private JSpinner levelOfExpertiseSpinner;
    private JComboBox<Integer> companyProjectIdComboBox;
    private JButton postButton;
    CompanyBusinessLayer companyBusinessLayer;

    public JobPostingPanel() {
        this.companyBusinessLayer = new CompanyBusinessLayer();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        requirementField = new JTextField(20);
        levelOfExpertiseSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
        companyProjectIdComboBox = new JComboBox<>(); // Populate this with company project IDs
        postButton = new JButton("Post Requirement");
        companyProjectIdComboBox = new JComboBox<>();
        List<CompanyProject> companyProjects = companyBusinessLayer.getCompanyProjectsByCompanyID(LoginPanel.loginIDs);
        for (CompanyProject project : companyProjects) {
            companyProjectIdComboBox.addItem(project.ID);
        }
        add(createLabeledField("Requirement:", requirementField));
        add(createLabeledField("Level of Expertise (Years):", levelOfExpertiseSpinner));
        add(createLabeledField("Company Project ID:", companyProjectIdComboBox));
        add(postButton);

        postButton.addActionListener(e -> postRequirement());
    }

    private JPanel createLabeledField(String label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void postRequirement() {
        Model.ProjectRequirement requirement = new Model.ProjectRequirement();
        requirement.setRequirement(requirementField.getText());
        requirement.setLevelofExperty((Integer) levelOfExpertiseSpinner.getValue());
        requirement.setCompanyProjectID((Integer) companyProjectIdComboBox.getSelectedItem());

        boolean isSuccess = companyBusinessLayer.addProjectRequirement(requirement);
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Requirement Posted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to post requirement", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
