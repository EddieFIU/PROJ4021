import Business.CandidateBusinessLayer;

import javax.swing.*;
import java.awt.*;

public class MatchedCandidatesCountPanel extends JPanel {
    private int companyID;
    private JLabel matchedCountLabel;
    private JButton backtoDashboardButton;
    CandidateBusinessLayer candidateBusinessLayer;

    public MatchedCandidatesCountPanel() {
        this.companyID = LoginPanel.loginIDs;
        candidateBusinessLayer = new CandidateBusinessLayer();
        int matchedCandidatesCount = candidateBusinessLayer.countMatchedCandidates(companyID);

        // Setting up the layout
        setLayout(new BorderLayout());
        matchedCountLabel = new JLabel("Number of Matched Candidates: " + matchedCandidatesCount);
        matchedCountLabel.setHorizontalAlignment(JLabel.CENTER);
        matchedCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Adding the label to the panel
        add(matchedCountLabel, BorderLayout.CENTER);

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

    // Optionally, if you want to refresh the count
    public void refreshMatchedCount() {
        int matchedCandidatesCount = candidateBusinessLayer.countMatchedCandidates(companyID);
        matchedCountLabel.setText("Number of Matched Candidates: " + matchedCandidatesCount);
    }

    // Test main method to demonstrate the panel
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Matched Candidates Count");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MatchedCandidatesCountPanel());
            frame.pack();
            frame.setVisible(true);
        });
    }
}
