import Business.CompanyBusinessLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MatchedCandidatesPanel extends JPanel {
    private JTable matchedCandidatesTable;
    CompanyBusinessLayer companyBusinessLayer;
    public MatchedCandidatesPanel( ) {
        companyBusinessLayer = new CompanyBusinessLayer();
        setLayout(new BorderLayout());

        matchedCandidatesTable = new JTable();
        add(new JScrollPane(matchedCandidatesTable), BorderLayout.CENTER);

        JButton loadButton = new JButton("Load Matched Candidates");
        loadButton.addActionListener(e -> loadMatchedCandidates());
        add(loadButton, BorderLayout.SOUTH);

        loadMatchedCandidates(); // Load matched candidates when the panel is initialized
    }

    private void loadMatchedCandidates() {
         List<Model.Candidate> matchedCandidates = companyBusinessLayer.getMatchedCandidates( LoginPanel.loginIDs);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Qualification");
        model.addColumn("Experience");

        for (Model.Candidate candidate : matchedCandidates) {
             model.addRow(new Object[]{candidate.ID, candidate.FirstName, candidate.LastName, candidate.Qualification, candidate.Experience});
        }

        matchedCandidatesTable.setModel(model);
    }
}
