import Business.CompanyBusinessLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReviewCandidatesPanel extends JPanel {

    CompanyBusinessLayer companyBusinessLayer;
    private JTable candidatesTable;

    private int companyID = LoginPanel.loginIDs; // ID of the logged-in user's company

    public ReviewCandidatesPanel() {
        companyBusinessLayer= new CompanyBusinessLayer();
        setLayout(new BorderLayout());

        candidatesTable = new JTable();
        add(new JScrollPane(candidatesTable), BorderLayout.CENTER);

        JButton reviewButton = new JButton("Review Candidates");
        reviewButton.addActionListener(e -> loadCandidates());
        add(reviewButton, BorderLayout.SOUTH);

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
}
