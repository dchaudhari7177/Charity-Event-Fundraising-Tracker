package ui.components;

import ui.ModernUIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Modern Panel Templates - Reusable panel layouts for common patterns
 */
public class ModernPanelTemplates {
    
    // ==================== CARD WITH TITLE ====================
    
    public static class TitleCardPanel extends ModernComponents.ModernCardPanel {
        private JLabel titleLabel;
        private JPanel contentPanel;
        
        public TitleCardPanel(String title) {
            super();
            setLayout(new BorderLayout(0, ModernUIConstants.SPACING_L));
            
            // Title
            titleLabel = new ModernComponents.ModernLabel(title, 
                ModernUIConstants.FONT_SIZE_M, true);
            add(titleLabel, BorderLayout.NORTH);
            
            // Content panel
            contentPanel = new JPanel();
            contentPanel.setOpaque(false);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            add(contentPanel, BorderLayout.CENTER);
        }
        
        public JPanel getContentPanel() {
            return contentPanel;
        }
        
        public void setTitle(String title) {
            titleLabel.setText(title);
        }
    }
    
    // ==================== STATS CARD ====================
    
    public static class StatsCard extends ModernComponents.ModernCardPanel {
        private JLabel valueLabel;
        private JLabel labelLabel;
        private JLabel changeLabel;
        
        public StatsCard(String label, String value, String change) {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            // Label
            labelLabel = new ModernComponents.ModernLabel(label, 
                ModernUIConstants.FONT_SIZE_XS, false);
            labelLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
            add(labelLabel);
            
            add(Box.createVerticalStrut(ModernUIConstants.SPACING_M));
            
            // Value
            valueLabel = new ModernComponents.ModernLabel(value, 
                ModernUIConstants.FONT_SIZE_L, true);
            valueLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
            add(valueLabel);
            
            add(Box.createVerticalStrut(ModernUIConstants.SPACING_M));
            
            // Change
            changeLabel = new ModernComponents.ModernLabel(change, 
                ModernUIConstants.FONT_SIZE_XS, false);
            changeLabel.setForeground(ModernUIConstants.SUCCESS);
            add(changeLabel);
        }
        
        public void setValue(String value) {
            valueLabel.setText(value);
        }
        
        public void setChangeText(String change) {
            changeLabel.setText(change);
        }
    }
    
    // ==================== FORM PANEL ====================
    
    public static class FormPanel extends JPanel {
        private JPanel fieldsPanel;
        private JPanel buttonsPanel;
        
        public FormPanel() {
            setOpaque(false);
            setLayout(new BorderLayout(0, ModernUIConstants.SPACING_XL));
            setBorder(BorderFactory.createEmptyBorder(
                ModernUIConstants.SPACING_XL,
                ModernUIConstants.SPACING_XL,
                ModernUIConstants.SPACING_XL,
                ModernUIConstants.SPACING_XL
            ));
            
            // Fields panel
            fieldsPanel = new JPanel();
            fieldsPanel.setOpaque(false);
            fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
            add(fieldsPanel, BorderLayout.CENTER);
            
            // Buttons panel
            buttonsPanel = new JPanel();
            buttonsPanel.setOpaque(false);
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, ModernUIConstants.SPACING_L, 0));
            add(buttonsPanel, BorderLayout.SOUTH);
        }
        
        public void addField(String label, JComponent field) {
            JPanel fieldPanel = new JPanel();
            fieldPanel.setOpaque(false);
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
            fieldPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 
                ModernUIConstants.SPACING_XXL));
            
            JLabel labelComponent = new ModernComponents.ModernLabel(label, 
                ModernUIConstants.FONT_SIZE_S, true);
            labelComponent.setForeground(ModernUIConstants.TEXT_PRIMARY);
            fieldPanel.add(labelComponent);
            
            fieldPanel.add(Box.createVerticalStrut(ModernUIConstants.SPACING_S));
            fieldPanel.add(field);
            
            fieldsPanel.add(fieldPanel);
            fieldsPanel.add(Box.createVerticalStrut(ModernUIConstants.SPACING_L));
        }
        
        public void addButton(JButton button) {
            buttonsPanel.add(button);
        }
        
        public void clearButtons() {
            buttonsPanel.removeAll();
        }
    }
    
    // ==================== CENTERED CARD PANEL ====================
    
    public static class CenteredCardPanel extends JPanel {
        private ModernComponents.ModernCardPanel card;
        
        public CenteredCardPanel() {
            setOpaque(false);
            setBackground(ModernUIConstants.BG_PRIMARY);
            setLayout(new GridBagLayout());
            
            card = new ModernComponents.ModernCardPanel();
            card.setMaximumSize(new Dimension(600, 700));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            
            add(card, gbc);
        }
        
        public ModernComponents.ModernCardPanel getCard() {
            return card;
        }
    }
    
    // ==================== HEADER PANEL ====================
    
    public static class HeaderPanel extends JPanel {
        private JLabel titleLabel;
        private JLabel subtitleLabel;
        
        public HeaderPanel(String title, String subtitle) {
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(
                ModernUIConstants.SPACING_XL,
                ModernUIConstants.SPACING_XL,
                ModernUIConstants.SPACING_XL,
                ModernUIConstants.SPACING_XL
            ));
            
            // Title
            titleLabel = new ModernComponents.ModernLabel(title, 
                ModernUIConstants.FONT_SIZE_L, true);
            titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
            add(titleLabel);
            
            // Subtitle
            subtitleLabel = new ModernComponents.ModernLabel(subtitle, 
                ModernUIConstants.FONT_SIZE_S, false);
            subtitleLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
            add(subtitleLabel);
        }
    }
    
    // ==================== ACTION BUTTON GROUP ====================
    
    public static class ActionButtonGroup extends JPanel {
        public ActionButtonGroup() {
            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.CENTER, ModernUIConstants.SPACING_L, 0));
        }
        
        public void addActionButton(String label, Color startColor, Color endColor, Runnable action) {
            ModernComponents.ModernButton button = new ModernComponents.ModernButton(label, startColor, endColor);
            button.addActionListener(e -> action.run());
            add(button);
        }
        
        public void addPrimaryButton(String label, Runnable action) {
            addActionButton(label, ModernUIConstants.ACCENT_CYAN, 
                           ModernUIConstants.ACCENT_GREEN, action);
        }
        
        public void addSecondaryButton(String label, Runnable action) {
            addActionButton(label, ModernUIConstants.ACCENT_PURPLE, 
                           ModernUIConstants.ACCENT_PINK, action);
        }
        
        public void addDangerButton(String label, Runnable action) {
            addActionButton(label, ModernUIConstants.ACCENT_PINK, 
                           ModernUIConstants.ERROR, action);
        }
    }
}
