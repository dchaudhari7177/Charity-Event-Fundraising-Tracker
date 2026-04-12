package ui.panels;

import ui.UIConstants;
import ui.CustomComponents;
import model.Donor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * View Donors Panel - Display donors in a styled table
 */
public class ViewDonorsPanel extends JPanel {
    
    private JTable donorsTable;
    private JButton backButton;
    private Runnable onBack;
    
    public ViewDonorsPanel() {
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
        
        JLabel titleLabel = CustomComponents.createTitleLabel("All Donors");
        
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
        String[] columns = {"ID", "Donor Name", "Email", "Phone Number"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        donorsTable = new JTable(model);
        donorsTable.setFont(UIConstants.FONT_BODY);
        donorsTable.setRowHeight(30);
        donorsTable.setBackground(UIConstants.BG_SECONDARY);
        donorsTable.setForeground(UIConstants.TEXT_PRIMARY);
        donorsTable.setGridColor(UIConstants.BG_TERTIARY);
        donorsTable.setSelectionBackground(UIConstants.ACCENT_CYAN);
        donorsTable.setSelectionForeground(UIConstants.BG_PRIMARY);
        
        // Header styling
        JTableHeader header = donorsTable.getTableHeader();
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
                setHorizontalAlignment(SwingConstants.LEFT);
                
                return this;
            }
        };
        
        for (int i = 0; i < columns.length; i++) {
            donorsTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(donorsTable);
        scrollPane.setBackground(UIConstants.BG_PRIMARY);
        scrollPane.getViewport().setBackground(UIConstants.BG_PRIMARY);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BG_TERTIARY, 2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    /**
     * Load donors into the table
     */
    public void loadDonors(List<Donor> donors) {
        DefaultTableModel model = (DefaultTableModel) donorsTable.getModel();
        model.setRowCount(0);
        
        for (Donor donor : donors) {
            Object[] row = {
                donor.getDonorId(),
                donor.getDonorName(),
                donor.getEmail(),
                donor.getPhoneNumber()
            };
            
            model.addRow(row);
        }
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
