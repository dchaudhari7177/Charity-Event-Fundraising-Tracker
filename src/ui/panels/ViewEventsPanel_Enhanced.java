package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import model.Event;
import model.Pledge;
import dao.EventDAO;
import observer.DonationObserver;
import observer.DonationEventManager;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Enhanced View Events Panel - Professional styled table with observer pattern integration
 * Supports admin mode with delete functionality
 */
public class ViewEventsPanel_Enhanced extends JPanel implements DonationObserver {
    
    private JTable eventsTable;
    private JButton backButton;
    private JButton logoutButton;
    private Runnable onBack;
    private Runnable onLogout;
    private EventDAO eventDAO;
    private boolean isAdminMode = false;
    
    public ViewEventsPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        eventDAO = new EventDAO();
        loadEvents();
        
        // Subscribe to donation events - Observer Pattern
        DonationEventManager.getInstance().subscribe(this);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE
        ));
        
        JLabel titleLabel = EnhancedComponents.createHeadingLabel("📊 All Registered Events");
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_YELLOW);
        header.add(titleLabel, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, UIConstants_Enhanced.PADDING_MEDIUM, 0));
        
        logoutButton = EnhancedComponents.createAnimatedSecondaryButton("🚪 Logout");
        logoutButton.addActionListener(e -> {
            if (onLogout != null) onLogout.run();
        });
        logoutButton.setVisible(false); // Hidden by default
        buttonPanel.add(logoutButton);
        
        backButton = EnhancedComponents.createAnimatedSecondaryButton("← Back");
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
        buttonPanel.add(backButton);
        
        header.add(buttonPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE,
            UIConstants_Enhanced.PADDING_LARGE
        ));
        
        // Create table with Action column if admin
        String[] columns = isAdminMode 
            ? new String[]{"ID", "Event Name", "Target Amount", "Amount Collected", "Progress", "Status", "Action"}
            : new String[]{"ID", "Event Name", "Target Amount", "Amount Collected", "Progress", "Status"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isAdminMode && column == 6; // Only Action column is editable in admin mode
            }
        };
        
        eventsTable = new JTable(model);
        eventsTable.setFont(UIConstants_Enhanced.FONT_BODY);
        eventsTable.setRowHeight(40);
        eventsTable.setBackground(UIConstants_Enhanced.BG_TERTIARY);
        eventsTable.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        eventsTable.setGridColor(UIConstants_Enhanced.BORDER_COLOR);
        eventsTable.setShowGrid(true);
        eventsTable.setShowHorizontalLines(true);
        eventsTable.setShowVerticalLines(false);
        
        // Header styling
        JTableHeader header = eventsTable.getTableHeader();
        header.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        header.setForeground(UIConstants_Enhanced.ACCENT_YELLOW);
        header.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        header.setOpaque(true);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UIConstants_Enhanced.ACCENT_YELLOW));
        
        // Cell rendering
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? UIConstants_Enhanced.BG_SECONDARY : UIConstants_Enhanced.BG_TERTIARY);
                c.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        };
        
        for (int i = 0; i < eventsTable.getColumnCount(); i++) {
            eventsTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            eventsTable.getColumnModel().getColumn(i).setPreferredWidth(120);
        }
        
        // Set button renderer for action column if admin
        if (isAdminMode) {
            eventsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
            eventsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), this));
            eventsTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        }
        
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        scrollPane.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIConstants_Enhanced.BG_PRIMARY);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadEvents() {
        List<Event> events = eventDAO.getAllEvents();
        DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();
        model.setRowCount(0);
        
        for (Event event : events) {
            double progressPercent = event.getTargetAmount() > 0
                ? (event.getCollectedAmount() / event.getTargetAmount()) * 100
                : 0;
            String status = progressPercent >= 100 ? "✓ Complete" : "● Active";
            
            if (isAdminMode) {
                model.addRow(new Object[]{
                    event.getEventId(),
                    event.getEventName(),
                    "₹" + event.getTargetAmount(),
                    "₹" + event.getCollectedAmount(),
                    String.format("%.1f%%", progressPercent),
                    status,
                    "Delete"
                });
            } else {
                model.addRow(new Object[]{
                    event.getEventId(),
                    event.getEventName(),
                    "₹" + event.getTargetAmount(),
                    "₹" + event.getCollectedAmount(),
                    String.format("%.1f%%", progressPercent),
                    status
                });
            }
        }
    }
    
    /**
     * Delete event by ID
     */
    public void deleteEvent(int eventId) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this event?\nThis action cannot be undone.",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = eventDAO.deleteEvent(eventId);
            if (success) {
                JOptionPane.showMessageDialog(
                    this,
                    "✓ Event deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadEvents(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "❌ Failed to delete event!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    /**
     * Observer Pattern: Called when a donation is received
     */
    @Override
    public void onDonationReceived(Pledge pledge, Event event) {
        loadEvents();
    }
    
    /**
     * Observer Pattern: Called when a goal is reached
     */
    @Override
    public void onGoalReached(Event event) {
        loadEvents();
    }
    
    /**
     * Observer Pattern: Called when event progress is updated
     */
    @Override
    public void onProgressUpdated(Event event, double percentageComplete) {
        loadEvents();
    }
    
    public void setOnBack(Runnable callback) { 
        this.onBack = callback; 
    }
    
    public void setOnLogout(Runnable callback) {
        this.onLogout = callback;
    }
    
    public void setAdminMode(boolean adminMode) {
        this.isAdminMode = adminMode;
        logoutButton.setVisible(adminMode);
        // Recreate table with new columns if needed
        SwingUtilities.invokeLater(this::loadEvents);
    }
    
    // Custom button renderer for table
    private static class ButtonRenderer extends javax.swing.table.DefaultTableCellRenderer {
        private JButton button;
        
        public ButtonRenderer() {
            button = new JButton("🗑️ Delete");
            button.setBackground(UIConstants_Enhanced.ACCENT_RED);
            button.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
            button.setFont(UIConstants_Enhanced.FONT_SMALL);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            button.setBackground(isSelected ? UIConstants_Enhanced.ACCENT_RED.darker() : UIConstants_Enhanced.ACCENT_RED);
            return button;
        }
    }
    
    // Custom button editor for table
    private static class ButtonEditor extends javax.swing.DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private ViewEventsPanel_Enhanced parentPanel;
        
        public ButtonEditor(JCheckBox checkBox, ViewEventsPanel_Enhanced parentPanel) {
            super(checkBox);
            this.parentPanel = parentPanel;
            button = new JButton("🗑️ Delete");
            button.setBackground(UIConstants_Enhanced.ACCENT_RED);
            button.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
            button.setFont(UIConstants_Enhanced.FONT_SMALL);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = "Delete";
            isPushed = true;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                JTable table = (JTable) getComponent();
                if (table != null) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        int eventId = (int) table.getValueAt(row, 0);
                        parentPanel.deleteEvent(eventId);
                    }
                }
            }
            isPushed = false;
            return label;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
