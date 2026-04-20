package ui.panels.modern;

import ui.ModernUIConstants;
import ui.components.ModernComponents;
import ui.components.ModernTableRenderer;
import dao.EventDAO;
import model.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Modern View Events Panel - Premium table with modern styling
 * Features alternating row colors, hover effects, and modern header
 */
public class ModernViewEventsPanel extends JPanel {
    
    private Runnable onBackClick;
    private EventDAO eventDAO;
    private JTable eventsTable;
    private DefaultTableModel tableModel;
    private ModernComponents.ModernButton backButton;
    private ModernComponents.ModernButton refreshButton;
    
    public ModernViewEventsPanel() {
        this.eventDAO = new EventDAO();
        setBackground(ModernUIConstants.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        
        loadEvents();
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(ModernUIConstants.BG_SECONDARY);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL
        ));
        
        JLabel titleLabel = new ModernComponents.ModernLabel("All Events",
            ModernUIConstants.FONT_SIZE_L, true);
        titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        
        JLabel subtitleLabel = new ModernComponents.ModernLabel(
            "View all registered charity events",
            ModernUIConstants.FONT_SIZE_S, false);
        subtitleLabel.setForeground(ModernUIConstants.TEXT_SECONDARY);
        
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(ModernUIConstants.SPACING_S));
        header.add(subtitleLabel);
        
        return header;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL
        ));
        
        // Create table
        String[] columnNames = {"Event ID", "Event Name", "Target Amount", "Collected Amount", "Progress", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventsTable = new JTable(tableModel);
        ModernTableRenderer.applyModernStyle(eventsTable);
        
        // Add to scroll pane
        JScrollPane scrollPane = ModernTableRenderer.createStyledScrollPane(eventsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, ModernUIConstants.SPACING_L, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
            0,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL,
            ModernUIConstants.SPACING_XL
        ));
        
        refreshButton = new ModernComponents.ModernButton("Refresh",
            ModernUIConstants.ACCENT_GREEN, ModernUIConstants.lighten(ModernUIConstants.ACCENT_GREEN, 0.15));
        refreshButton.addActionListener(e -> loadEvents());
        
        backButton = new ModernComponents.ModernButton("Back",
            ModernUIConstants.TEXT_TERTIARY, ModernUIConstants.TEXT_SECONDARY);
        backButton.addActionListener(e -> {
            if (onBackClick != null) onBackClick.run();
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }
    
    private void loadEvents() {
        tableModel.setRowCount(0);
        List<Event> events = eventDAO.getAllEvents();
        
        for (Event event : events) {
            double progress = event.getProgressPercentage();
            String progressStr = String.format("%.1f%%", progress);
            
            Object[] row = {
                event.getEventId(),
                event.getEventName(),
                "₹" + String.format("%.2f", event.getTargetAmount()),
                "₹" + String.format("%.2f", event.getCollectedAmount()),
                progressStr,
                event.getDescription()
            };
            
            tableModel.addRow(row);
        }
    }
    
    public void setOnBackClick(Runnable callback) {
        this.onBackClick = callback;
    }
}
