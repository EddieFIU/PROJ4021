import Model.Candidate;
import DataRepository.*;
import Util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CandidateDashboardPanel extends JPanel {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField emailField;
    private JTextField cellPhoneField;
    private JButton applyButton;

    private DatabaseRepo repo;
    private Candidate currentCandidate;

    public CandidateDashboardPanel() {
        repo = new DatabaseRepo();
        initializeUI();
        // Delay loading of candidate profile to ensure components are fully initialized
        SwingUtilities.invokeLater(this::loadCandidateProfile);
    }

    private void initializeUI() {
        setLayout(new GridLayout(0, 2));
        // Initialize fields
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        dobField = new JTextField();
        emailField = new JTextField();
        cellPhoneField = new JTextField();
        applyButton = new JButton("Apply Changes");

        // Add components to the panel
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Date of Birth (yyyy-mm-dd):"));
        add(dobField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Cell Phone:"));
        add(cellPhoneField);

        applyButton.addActionListener(this::applyChanges);
        add(applyButton);
    }

    private void loadCandidateProfile() {
        if (Session.getCurrentUser() != null) {
            int loginUserID = Session.getCurrentUser().ID;
            currentCandidate = repo.GetCandidateByLoginID(loginUserID);

            // Check if currentCandidate is not null before attempting to access its fields
            if (currentCandidate != null) {
                // Populate the fields with the current candidate's information
                firstNameField.setText(currentCandidate.FirstName);
                lastNameField.setText(currentCandidate.LastName);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dob = format.format(currentCandidate.DOB);
                dobField.setText(dob); // Ensure DOB is stored as a String
                emailField.setText(currentCandidate.Email);
                cellPhoneField.setText(currentCandidate.CellPhone);
            } else {
                // Handle the case where the candidate profile is not found
                JOptionPane.showMessageDialog(this, "No candidate profile found.", "Profile Error", JOptionPane.ERROR_MESSAGE);
                // Consider disabling editing or hiding the panel
            }
        } else {
            // Handle the case where no user is logged in
            JOptionPane.showMessageDialog(this, "No user is logged in.", "Session Error", JOptionPane.ERROR_MESSAGE);
            // Consider redirecting to the login panel
        }
    }

    private void applyChanges(ActionEvent e) {
        if (currentCandidate != null) {
            // Update the currentCandidate object with the new values from the fields
            currentCandidate.FirstName = firstNameField.getText();
            currentCandidate.LastName = lastNameField.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dob = (Date) format.parse(dobField.getText());
                currentCandidate.DOB = dob;
            } catch (ParseException ex) {
                // Handle the case where the date format is incorrect
                JOptionPane.showMessageDialog(this, "Invalid date format. Please enter the date in yyyy-MM-dd format.", "Date Error", JOptionPane.ERROR_MESSAGE);
            } // Ensure this is the correct format before saving
            currentCandidate.Email = emailField.getText();
            currentCandidate.CellPhone = cellPhoneField.getText();

            boolean success = repo.UpdateCandidate(currentCandidate);
            if(success) {
                JOptionPane.showMessageDialog(this, "Changes were successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile. Please try again.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Handle the case where there is no candidate to update
            JOptionPane.showMessageDialog(this, "No candidate data to update.", "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
