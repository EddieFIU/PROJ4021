import Business.CandidateBusinessLayer;
import Model.Candidate;
import Model.CandidateQualification;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateProfilePanel extends JPanel {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField emailField;
    private JTextField cellPhoneField;
    private JTable qualificationTable;
    private DefaultTableModel tableModel;
    private JButton addQualificationButton;
    private JButton updateQualificationButton;
    private JButton deleteQualificationButton;
    private JButton profileActionButton;
    private List<CandidateQualification> qualifications;
    private CandidateBusinessLayer candidate;
    private JButton backButton;
     int  CandidateId;
    public CreateProfilePanel() {
        candidate= new CandidateBusinessLayer();
        qualifications = new ArrayList<>();


        setLayout(new BorderLayout());

        // Input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        firstNameField = createTextField("First Name:");
        lastNameField = createTextField("Last Name:");
        dobField = createTextField("Date of Birth (yyyy-MM-dd):");
        emailField = createTextField("Email:");
        cellPhoneField = createTextField("Cell Phone:");
        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToDashboard();
            }
        });

        // Add the back button to the button panel

        addQualificationButton = new JButton("Add Qualification");
        addQualificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQualification();
            }
        });

        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        inputPanel.add(dobField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Cell Phone:"));
        inputPanel.add(cellPhoneField);
        inputPanel.add(addQualificationButton);

        JScrollPane inputScrollPane = new JScrollPane(inputPanel);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(inputScrollPane, BorderLayout.NORTH);

        // Qualification table panel
        tableModel = new DefaultTableModel(new Object[]{"Qualification", "Level of Experience"}, 0);
        qualificationTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(qualificationTable);

        add(tableScrollPane, BorderLayout.CENTER);

        // Buttons for qualification actions
        JPanel buttonPanel = new JPanel(new FlowLayout());

        updateQualificationButton = new JButton("Update Qualification");
        updateQualificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQualification();
            }
        });

        deleteQualificationButton = new JButton("Delete Qualification");
        deleteQualificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteQualification();
            }
        });

        buttonPanel.add(updateQualificationButton);
        buttonPanel.add(deleteQualificationButton);

        boolean profileExists = candidate.doesProfileExist(LoginPanel.loginIDs);
        if (profileExists) {
            profileActionButton = new JButton("Update Profile");
            CandidateId=  candidate.getCandidateId(LoginPanel.loginIDs);
            fillFormWithExistingData(CandidateId); // Method to fill form with existing data
        } else {
            profileActionButton = new JButton("Create Profile");
        }

        profileActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (profileExists) {
                    updateProfile(); // Method to update the profile
                } else {
                    createProfile(); // Method to create the profile
                }
            }
        });
        loadQualificationsData(LoginPanel.loginIDs);
        buttonPanel.add(backButton);
        buttonPanel.add(profileActionButton); // Add the button to your panel
        add(buttonPanel, BorderLayout.SOUTH);

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


    private void updateProfile() {
        Model.Candidate existingCandidate = getCandidateFromInputs();
        if (existingCandidate != null) {
            candidate.UpdateCandidateProfile(existingCandidate);
             JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error in updating profile. Check input data.");
        }
    }

    private boolean areInputFieldsValid() {
        return !firstNameField.getText().trim().isEmpty() &&
                !lastNameField.getText().trim().isEmpty() &&
                !dobField.getText().trim().isEmpty() &&
                !emailField.getText().trim().isEmpty() &&
                !cellPhoneField.getText().trim().isEmpty();
    }
    private void createProfile() {
        if (!areInputFieldsValid()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
            return;
        }
        Model.Candidate newCandidate = getCandidateFromInputs();
        if (newCandidate != null) {
            Model.Candidate createdCandidate = candidate.CreatenewCandidate(newCandidate);
            if (createdCandidate != null) {
                for (Model.CandidateQualification qual : qualifications) {
                    qual.CandidateID = createdCandidate.ID;
                    CandidateId=  createdCandidate.ID;
                }
                JOptionPane.showMessageDialog(this, "Profile created successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error in creating profile.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error in creating profile. Check input data.");
        }
    }

    private JTextField createTextField(String label) {
        JTextField textField = new JTextField();
        return textField;
    }
    private void fillFormWithExistingData(int loginID) {
         Model.Candidate existingCandidate = candidate.GetCandidateByLoginID(loginID);
        firstNameField.setText(existingCandidate.FirstName);
        lastNameField.setText(existingCandidate.LastName);
        dobField.setText(new SimpleDateFormat("yyyy-MM-dd").format(existingCandidate.DOB));
        emailField.setText(existingCandidate.Email);
        cellPhoneField.setText(existingCandidate.CellPhone);

    }

    private void loadQualificationsData(int loginID) {
        qualifications.clear(); // Clear existing data in the list
        List<Model.CandidateQualification> existingQualifications = candidate.GetCandidateQualifications(loginID);
        qualifications.addAll(existingQualifications); // Add all to the list
        updateTableModel(existingQualifications);
    }


    private void updateQualificationsTable() {
        int loginID = LoginPanel.loginIDs;
        List<Model.CandidateQualification> existingQualifications = candidate.GetCandidateQualifications(loginID);
        updateTableModel(existingQualifications);
    }

    private void updateTableModel(List<Model.CandidateQualification> qualifications) {
        tableModel.setRowCount(0); // Clear the table
        for (Model.CandidateQualification qual : qualifications) {
            tableModel.addRow(new Object[]{qual.Qualification, qual.LevelOfExperience});
        }
    }



    private void addQualification() {
        String qualification = JOptionPane.showInputDialog(this, "Enter Qualification:");
        String levelOfExperience = JOptionPane.showInputDialog(this, "Enter Level of Experience:");

        if (qualification != null && levelOfExperience != null) {
             CandidateQualification newQual = new CandidateQualification(qualification, Integer.parseInt(levelOfExperience));
            newQual.CandidateID= CandidateId;
            qualifications.add(newQual);
            tableModel.addRow(new Object[]{qualification, levelOfExperience});

             candidate.CreatenewCandidateQualification(newQual);
        }
    }
    private void updateQualification() {
        int selectedRow = qualificationTable.getSelectedRow();

        if (selectedRow != -1 && selectedRow < qualifications.size()) {
            String qualification = JOptionPane.showInputDialog(this, "Enter Qualification:", qualifications.get(selectedRow).Qualification);
            String levelOfExperience = JOptionPane.showInputDialog(this, "Enter Level of Experience:", qualifications.get(selectedRow).LevelOfExperience);

            if (qualification != null && levelOfExperience != null) {
                qualifications.get(selectedRow).Qualification = qualification;
                qualifications.get(selectedRow).LevelOfExperience = Integer.parseInt(levelOfExperience);
                tableModel.setValueAt(qualification, selectedRow, 0);
                tableModel.setValueAt(levelOfExperience, selectedRow, 1);

                  candidate.UpdateCandidateQualification(qualifications.get(selectedRow));
            }
        }
    }

    private void deleteQualification() {
        int selectedRow = qualificationTable.getSelectedRow();
        if (selectedRow != -1) {
             CandidateQualification deletedQualification = qualifications.remove(selectedRow);
            tableModel.removeRow(selectedRow);

              candidate.DeleteCandidateQualification(deletedQualification.ID);
        }
    }



    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            loadQualificationsData(LoginPanel.loginIDs);
        }
    }

    public Candidate getCandidateFromInputs() {
        Candidate candidate = new Candidate();
        candidate.FirstName = firstNameField.getText();
        candidate.LastName = lastNameField.getText();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = new Date(format.parse(dobField.getText()).getTime());
            candidate.DOB = dob;
        } catch (ParseException e) {
            // Handle date format error
            return null;
        }
        candidate.Email = emailField.getText();
        candidate.CellPhone = cellPhoneField.getText();
        candidate.LogInUserID=LoginPanel.loginIDs;
        candidate.qualifications = qualifications.toArray(new CandidateQualification[0]);
        return candidate;
    }
}
