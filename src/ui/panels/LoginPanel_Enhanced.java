package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import model.Admin;
import service.AdminService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Enhanced Login Panel - Admin authentication interface
 * Provides professional login UI with admin credentials validation
 */
public class LoginPanel_Enhanced extends JPanel {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel messageLabel;
    private Runnable onLoginSuccess;
    private AdminService adminService;
    private Admin currentAdmin;
    
    public LoginPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new GridBagLayout());
        
        adminService = new AdminService();
        
        // Create center panel with gradient background
        JPanel centerPanel = createCenterPanel();
        add(centerPanel);
        
        // Add key listeners for Enter key
        setFocusable(true);
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE,
            UIConstants_Enhanced.PADDING_XLARGE
        ));
        
        // Set max size for center panel
        panel.setMaximumSize(new Dimension(400, 550));
        panel.setPreferredSize(new Dimension(400, 550));
        
        // Title
        JLabel titleLabel = new JLabel("🔐 Admin Login");
        titleLabel.setFont(UIConstants_Enhanced.FONT_HEADING);
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_CYAN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_LARGE));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Secure Access Only");
        subtitleLabel.setFont(UIConstants_Enhanced.FONT_BODY);
        subtitleLabel.setForeground(UIConstants_Enhanced.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        usernameLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        
        // Username Field
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIConstants_Enhanced.FIELD_HEIGHT));
        usernameField.setBackground(UIConstants_Enhanced.BG_TERTIARY);
        usernameField.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        usernameField.setCaretColor(UIConstants_Enhanced.ACCENT_CYAN);
        usernameField.setFont(UIConstants_Enhanced.FONT_BODY);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants_Enhanced.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UIConstants_Enhanced.PADDING_MEDIUM, 
                UIConstants_Enhanced.PADDING_MEDIUM, 
                UIConstants_Enhanced.PADDING_MEDIUM, 
                UIConstants_Enhanced.PADDING_MEDIUM)
        ));
        usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_LARGE));
        
        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        passwordLabel.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_SMALL));
        
        // Password Field
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIConstants_Enhanced.FIELD_HEIGHT));
        passwordField.setBackground(UIConstants_Enhanced.BG_TERTIARY);
        passwordField.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        passwordField.setCaretColor(UIConstants_Enhanced.ACCENT_CYAN);
        passwordField.setFont(UIConstants_Enhanced.FONT_BODY);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants_Enhanced.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UIConstants_Enhanced.PADDING_MEDIUM, 
                UIConstants_Enhanced.PADDING_MEDIUM, 
                UIConstants_Enhanced.PADDING_MEDIUM, 
                UIConstants_Enhanced.PADDING_MEDIUM)
        ));
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        
        // Message Label for errors/info
        messageLabel = new JLabel("");
        messageLabel.setForeground(UIConstants_Enhanced.ACCENT_RED);
        messageLabel.setFont(UIConstants_Enhanced.FONT_SMALL);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_MEDIUM));
        
        // Login Button
        loginButton = EnhancedComponents.createAnimatedPrimaryButton("🔓 Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> performLogin());
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_MEDIUM));
        
        // Exit Button
        exitButton = EnhancedComponents.createAnimatedSecondaryButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);
        
        // Info section
        panel.add(Box.createVerticalStrut(UIConstants_Enhanced.PADDING_XLARGE));
        JLabel infoLabel = new JLabel("<html><center><b>Demo Credentials:</b><br/>Username: admin<br/>Password: admin123</center></html>");
        infoLabel.setForeground(UIConstants_Enhanced.TEXT_SECONDARY);
        infoLabel.setFont(UIConstants_Enhanced.FONT_SMALL);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(infoLabel);
        
        return panel;
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty()) {
            showError("❌ Username cannot be empty");
            return;
        }
        
        if (password.isEmpty()) {
            showError("❌ Password cannot be empty");
            return;
        }
        
        loginButton.setEnabled(false);
        loginButton.setText("🔄 Logging in...");
        
        // Authenticate admin
        Admin admin = adminService.login(username, password);
        
        if (admin != null) {
            currentAdmin = admin;
            showSuccess("✓ Login successful! Welcome " + admin.getFullName());
            
            // Call success callback after a short delay
            SwingUtilities.invokeLater(() -> {
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            });
        } else {
            showError("❌ Invalid username or password");
            passwordField.setText("");
            usernameField.requestFocus();
            loginButton.setEnabled(true);
            loginButton.setText("🔓 Login");
        }
    }
    
    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(UIConstants_Enhanced.ACCENT_RED);
        loginButton.setEnabled(true);
        loginButton.setText("🔓 Login");
    }
    
    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(UIConstants_Enhanced.ACCENT_GREEN);
    }
    
    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }
    
    public Admin getCurrentAdmin() {
        return currentAdmin;
    }
    
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText("");
        usernameField.requestFocus();
    }
}
