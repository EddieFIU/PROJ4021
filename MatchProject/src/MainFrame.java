import javax.swing.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        // Create a LoginPanel and set it as the content pane
        setContentPane(new LoginPanel()); // TODO: Implement LoginPanel class

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
