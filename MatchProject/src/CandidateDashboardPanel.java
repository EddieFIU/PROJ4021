import Model.Candidate;
import DataRepository.*;
import Util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CandidateDashboardPanel extends JPanel {

     private JButton createProfileButton;
     private JButton seeCompanyMatchesButton;
    private JButton seeCandidateMatchesButton;



    public CandidateDashboardPanel() {
         initializeUI();
        // Delay loading of candidate profile to ensure components are fully initialized
        SwingUtilities.invokeLater(this::loadCandidateProfile);
    }

    private void initializeUI() {
        setLayout(new GridLayout(0, 2));
         createProfileButton = new JButton("My professional profile");
         seeCompanyMatchesButton = new JButton("See company profiles that match my expertise");
        seeCandidateMatchesButton = new JButton("See how many candidates have matched with company profile");





        createProfileButton.addActionListener(e -> createProfessionalProfile());
        add(createProfileButton);


        seeCompanyMatchesButton.addActionListener(e -> seeCompanyMatches());
        add(seeCompanyMatchesButton);

        seeCandidateMatchesButton.addActionListener(e -> seeCandidateMatches());
        add(seeCandidateMatchesButton);
    }

    // Implement the button action methods
    private void createProfessionalProfile() {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();

            CreateProfilePanel createProfilePanel = new CreateProfilePanel();

            // Set preferred size of the createProfilePanel, adjust the width and height as needed
            createProfilePanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

            // Create GridBagConstraint for createProfilePanel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER; // End row
            gbc.fill = GridBagConstraints.BOTH; // Fill the space
            gbc.weightx = 1.0; // Take up all horizontal space
            gbc.weighty = 1.0; // Take up all vertical space

            // Add the createProfilePanel to the frame with constraints
            frame.getContentPane().add(createProfilePanel, gbc);

            // Revalidate and repaint the frame to reflect the changes
            frame.revalidate();
            frame.repaint();
        }






    private void seeCompanyMatches() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        MatchingCompanyProfilesPanel createProfilePanel = new MatchingCompanyProfilesPanel();

        // Set preferred size of the createProfilePanel, adjust the width and height as needed
        createProfilePanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

        // Create GridBagConstraint for createProfilePanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // End row
        gbc.fill = GridBagConstraints.BOTH; // Fill the space
        gbc.weightx = 1.0; // Take up all horizontal space
        gbc.weighty = 1.0; // Take up all vertical space

        // Add the createProfilePanel to the frame with constraints
        frame.getContentPane().add(createProfilePanel, gbc);

        // Revalidate and repaint the frame to reflect the changes
        frame.revalidate();
        frame.repaint();}

    private void seeCandidateMatches() {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        MatchedCandidatesCountPanel createProfilePanel = new MatchedCandidatesCountPanel();

        // Set preferred size of the createProfilePanel, adjust the width and height as needed
        createProfilePanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

        // Create GridBagConstraint for createProfilePanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // End row
        gbc.fill = GridBagConstraints.BOTH; // Fill the space
        gbc.weightx = 1.0; // Take up all horizontal space
        gbc.weighty = 1.0; // Take up all vertical space

        // Add the createProfilePanel to the frame with constraints
        frame.getContentPane().add(createProfilePanel, gbc);

        // Revalidate and repaint the frame to reflect the changes
        frame.revalidate();
        frame.repaint();
    }
    private void loadCandidateProfile() {
        // ... (existing loadCandidateProfile code)
    }

    private void applyChanges(ActionEvent e) {
        // ... (existing applyChanges code)
    }
}
