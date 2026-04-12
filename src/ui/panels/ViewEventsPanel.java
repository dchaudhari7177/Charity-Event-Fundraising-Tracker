package ui.panels;

import ui.UIConstants;
import ui.CustomComponents;
import model.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * View Events Panel - Display events in a styled table
 */
public class ViewEventsPanel extends JPanel {
    
    private JTable eventsTable;
    private JButton backButton;
    private Runnable onBack;
    
    public ViewEventsPanel() {
        setBackground(UIConstants.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIConstants.BG_SECONDARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE,
            UIConstants.PADDING_MEDIUM, UIConstants.PADDING_LARGE
        ));
        
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        
        backButton = CustomComponents.createSecondaryButton("← Back");
        backButton.setPreferredSize(new Dimension(100, UIConstants.BUTTON_HEIGHT));
        backButton.setMaximumSize(new Dimension(100, UIConstants.BUTTON_HEIGHT));
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
        
        JLabel titleLabel = CustomComponents.createTitleLabel("All Events");
        
        headerPanel.add(backButton);
        headerPanel.add(Box.createHorizontalStrut(UIConstants.PADDING_LARGE));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalGlue());
        
        return headerPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(UIConstants.BG_PRIMARY);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE,
            UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE
        ));
        tablePanel.setLayout(new BorderLayout());
        
        // Create table
        String[] columns = {"ID", "Event Name", "Target (₹)", "Collected (₹)", "Progress (%)", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventsTable = new JTable(model);
        eventsTable.setFont(UIConstants.FONT_BODY);
        eventsTable.setRowHeight(30);
        eventsTable.setBackground(UIConstants.BG_SECONDARY);
        eventsTable.setForeground(UIConstants.TEXT_PRIMARY);
        eventsTable.setGridColor(UIConstants.BG_TERTIARY);
        eventsTable.setSelectionBackground(UIConstants.ACCENT_GREEN);
        eventsTable.setSelectionForeground(UIConstants.BG_PRIMARY);
        
        // Header styling
        JTableHeader header = eventsTable.getTableHeader();
        header.setBackground(UIConstants.BG_TERTIARY);
        header.setForeground(UIConstants.TEXT_PRIMARY);
        header.setFont(UIConstants.FONT_SUBHEADING);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        
        // Cell rendering
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? UIConstants.BG_SECONDARY : UIConstants.BG_TERTIARY);
                    setForeground(UIConstants.TEXT_PRIMARY);
                }
                
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                setHorizontalAlignment(SwingConstants.CENTER);
                
                return this;
            }
        };
        
        for (int i = 0; i < columns.length; i++) {
            eventsTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        scrollPane.setBackground(UIConstants.BG_PRIMARY);
        scrollPane.getViewport().setBackground(UIConstants.BG_PRIMARY);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BG_TERTIARY, 2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    /**
     * Load events into the table
     */
    public void loadEvents(List<Event> events) {
        DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();
        model.setRowCount(0);
        
        for (Event event : events) {
            double progress = (event.getCollectedAmount() / event.getTargetAmount()) * 100;
            String status = progress >= 100 ? "✓ Complete" : "Ongoing";
            
            Object[] row = {
                event.getEventId(),
                event.getEventName(),
                String.format("%.2f", event.getTargetAmount()),
                String.format("%.2f", event.getCollectedAmount()),
                String.format("%.1f", progress),
                status
            };
            
            model.addRow(row);
        }
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
