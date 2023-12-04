import javax.swing.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        // Initialize the tabbed pane and set up the layout
        tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Login", new LoginPanel()); // TODO: Implement LoginPanel class
        //tabbedPane.addTab("Candidates", new CandidatePanel()); // TODO: Implement CandidatePanel class
        tabbedPane.addTab("Companies", new CompanyPanel()); // TODO: Implement CompanyPanel class
        //tabbedPane.addTab("Search", new SearchPanel()); // TODO: Implement SearchPanel class
        tabbedPane.addTab("Candidate Profile", new CandidateDashboardPanel()); // TODO: Implement CandidateProfilePanel class

        // Add the tabbed pane to the frame
        add(tabbedPane);

        // Set up the main frame
        setTitle("Project Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ensure the GUI is created on the Swing event dispatch thread
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
