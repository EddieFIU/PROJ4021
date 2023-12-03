
import javax.swing.*;
 import java.awt.*;

public class companyDashboardPanel extends JPanel  {

    private JButton CompanyProfileButton;
    private JButton JobPostingButton;
    private JButton CompanyProjectButton;
    private JButton ReviewCandidatesButton;
    private JButton MatchedCandidatesButton;

    public companyDashboardPanel() {
        initializeUI();
    }



        private void initializeUI() {
            setLayout(new GridLayout(0, 2));
            CompanyProfileButton = new JButton("Company Profile");
            JobPostingButton = new JButton("Job Posting");
            ReviewCandidatesButton = new JButton("Review Candidates");
            CompanyProjectButton = new JButton("Company Project");
            MatchedCandidatesButton = new JButton("Matched Candidates");



            CompanyProfileButton.addActionListener(e -> createCompanyProfilePanel());
            add(CompanyProfileButton);


            JobPostingButton.addActionListener(e -> JobPostingPanelPanel());
            add(JobPostingButton);

            ReviewCandidatesButton.addActionListener(e -> ReviewCandidatesPanels());
            add(ReviewCandidatesButton);

            MatchedCandidatesButton.addActionListener(e -> MatchedCandidatesPanels());
            add(MatchedCandidatesButton);

            CompanyProjectButton.addActionListener(e -> companyProjectPanels());
            add(CompanyProjectButton);
        }
    private void createCompanyProfilePanel() {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        companyProfilePanel createProfilePanel = new companyProfilePanel();

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
    private void JobPostingPanelPanel() {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        JobPostingPanel createProfilePanel = new JobPostingPanel();

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
    }   private void MatchedCandidatesPanels() {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        MatchedCandidatesPanel createProfilePanel = new MatchedCandidatesPanel();

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
    }   private void ReviewCandidatesPanels() {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        ReviewCandidatesPanel createProfilePanel = new ReviewCandidatesPanel();

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
    }   private void companyProjectPanels() {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        CompanyProjectPanel createProfilePanel = new CompanyProjectPanel();

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
