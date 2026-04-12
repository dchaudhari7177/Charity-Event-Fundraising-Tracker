package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import model.Event;
import dao.EventDAO;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Enhanced View Events Panel - Professional styled table
 */
public class ViewEventsPanel_Enhanced extends JPanel {
    
    private JTable eventsTable;
    private JButton backButton;
    private Runnable onBack;
    private EventDAO eventDAO;
    
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
        
        backButton = EnhancedComponents.createAnimatedSecondaryButton("← Back");
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
        header.add(backButton, BorderLayout.EAST);
        
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
        
        // Create table
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Event Name", "Target Amount", "Amount Collected", "Progress", "Status"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
