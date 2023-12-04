import Business.CompanyBusinessLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReviewCandidatesPanel extends JPanel {

    CompanyBusinessLayer companyBusinessLayer;
    private JTable candidatesTable;
    private JButton backtoDashboardButton;

    private int companyID = LoginPanel.loginIDs; // ID of the logged-in user's company

    public ReviewCandidatesPanel() {
        companyBusinessLayer = new CompanyBusinessLayer();
        setLayout(new BorderLayout());

        candidatesTable = new JTable();
        add(new JScrollPane(candidatesTable), BorderLayout.CENTER);

        JButton reviewButton = new JButton("Review Candidates");
        reviewButton.addActionListener(e -> loadCandidates());

        // Back to Dashboard Button
        backtoDashboardButton = new JButton("Back to Dashboard");
        backtoDashboardButton.addActionListener(e -> goBackToDashboard());

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backtoDashboardButton);
        buttonPanel.add(reviewButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadCandidates(); // Load candidates when the panel is initialized
    }

    private void loadCandidates() {
        List<Model.Candidate> candidates = companyBusinessLayer.getCandidatesForCompanyProjects(companyID);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Email");
        model.addColumn("Cell Phone");

        for (Model.Candidate candidate : candidates) {
            model.addRow(new Object[]{candidate.ID, candidate.FirstName, candidate.LastName, candidate.Email, candidate.CellPhone});
        }

        candidatesTable.setModel(model);
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
