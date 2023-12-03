import Business.LoginAccount;
import Model.LoginUser;
import Util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LoginAccount loginAccountBusinessLayer; // Instance of the business layer
    static int loginIDs;
    public LoginPanel() {
        // Business layer initialization
        loginAccountBusinessLayer = new LoginAccount();

        // UI components initialization
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerCompanyButton = new JButton("Register Company");
        JButton registerCandidateButton = new JButton("Register Candidate");

        // Apply custom styles to the buttons
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        registerCompanyButton.setBackground(new Color(0, 123, 255));
        registerCompanyButton.setForeground(Color.WHITE);
        registerCandidateButton.setBackground(new Color(0, 123, 255));
        registerCandidateButton.setForeground(Color.WHITE);

        // Layout setup with GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createLabel("Username:"), gbc);

        gbc.gridy = 1;
        add(createLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(createStyledTextField(usernameField), gbc);

        gbc.gridy = 1;
        add(createStyledPasswordField(passwordField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(createStyledButton(loginButton), gbc);

        gbc.gridy = 3;
        add(createStyledButton(registerCandidateButton), gbc);

        gbc.gridy = 4;
        add(createStyledButton(registerCompanyButton), gbc);

        // Event listeners for buttons
        loginButton.addActionListener(e -> login());
        registerCandidateButton.addActionListener(e -> register("candidate"));
        registerCompanyButton.addActionListener(e -> register("company"));
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    private JTextField createStyledTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setPreferredSize(new Dimension(200, 30)); // Adjust the size as needed
        return textField;
    }

    private JPasswordField createStyledPasswordField(JPasswordField passwordField) {
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(200, 30)); // Adjust the size as needed
        return passwordField;
    }

    private JButton createStyledButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setBackground(new Color(0, 85, 170));
                // You can add more actions here if needed
            }
        });
        return button;
    }
    private void login() {
        // Extract user input
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // Attempt to authenticate the user
        try {
            LoginUser loginUser = loginAccountBusinessLayer.GetAccountByUserName(username);
            if (loginUser != null && verifyPassword(loginUser.Password, password)) {

                // If authentication is successful, store the user in the session
                Session.setCurrentUser(loginUser);
                JOptionPane.showMessageDialog(this, "Login successful!");
                if (Objects.equals(loginUser.UserType, "candidate")) {
                    removeAll();
                    revalidate();
                    repaint();
                    System.out.println(loginUser.ID+"pop");
                    loginIDs=loginUser.ID;
                    CandidateDashboardPanel createCandidateDashboardPanel = new CandidateDashboardPanel();
                    add(createCandidateDashboardPanel);
                    revalidate();
                } else if (Objects.equals(loginUser.UserType, "company")) {
                    loginIDs=loginUser.ID;
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(new companyDashboardPanel());
                    frame.revalidate();
                    frame.repaint();
                } else {
                    // Handle unsupported UserType
                    JOptionPane.showMessageDialog(this, "Invalid UserType.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // If authentication fails, show an error message
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // Handle any exceptions during login
            JOptionPane.showMessageDialog(this, "An error occurred while attempting to login.", "Login Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Clear the password field for security
            passwordField.setText("");
        }
    }

    private boolean verifyPassword(String storedPassword, char[] enteredPassword) {

        return storedPassword.equals(new String(enteredPassword));
    }

    private void register(String userType) {
        // Display the registration dialog and handle user registration
        LoginUser newUser = displayRegistrationDialog(userType);
        if (newUser != null) {
            // Hash the password before storing it
            newUser.Password = hashPassword(newUser.Password);
            // Create the user account using the business layer
            LoginUser registeredUser = loginAccountBusinessLayer.CreateUserLogin(newUser);
            if (registeredUser != null && registeredUser.ID != 0) {
                // If registration is successful, log the user in and show a confirmation message
                Session.setCurrentUser(registeredUser);
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                // If registration fails, show an error message
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private LoginUser displayRegistrationDialog(String userType) {
        // Create the dialog and its components
        JDialog registrationDialog = new JDialog((Frame) null, "Register " + userType, true);
        JPanel panel = new JPanel(new GridLayout(0, 2));
    
        // Fields for registration
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
    
        // Add components to dialog
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
    
        // Register button and its action listener
        JButton registerButton = new JButton("Register");
        // Use an array to store the new user, so it can be final and used in the lambda
        final LoginUser[] newUser = new LoginUser[1]; 
    
        registerButton.addActionListener(e -> {
            // Create a new LoginUser object and set its properties from the fields
            newUser[0] = new LoginUser();
            newUser[0].UserName = usernameField.getText();
            newUser[0].Password = new String(passwordField.getPassword()); // Hash this password in production
            newUser[0].UserType = userType; // Set the user type based on the registration dialog
    
            registrationDialog.dispose(); // Close the dialog
        });
    
        // Add register button to dialog
        panel.add(registerButton);
    
        // Add panel to dialog and set dialog properties
        registrationDialog.setContentPane(panel);
        registrationDialog.pack();
        registrationDialog.setLocationRelativeTo(null); // Center the dialog
        registrationDialog.setVisible(true); // Show the dialog and wait for it to be disposed
    
        return newUser[0]; // Return the new user object created in the dialog
    }
    

    private String hashPassword(String password) {
        // Placeholder for password hashing logic
        // This should be replaced with actual hashing logic (e.g., bcrypt) in a production environment
        // Currently, it returns the password as-is, which is not secure
        return password;
    }
}
