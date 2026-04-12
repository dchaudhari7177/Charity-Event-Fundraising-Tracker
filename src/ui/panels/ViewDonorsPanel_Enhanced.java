package ui.panels;

import ui.UIConstants_Enhanced;
import ui.EnhancedComponents;
import model.Donor;
import dao.DonorDAO;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Enhanced View Donors Panel - Professional styled table
 */
public class ViewDonorsPanel_Enhanced extends JPanel {
    
    private JTable donorsTable;
    private JButton backButton;
    private Runnable onBack;
    private DonorDAO donorDAO;
    
    public ViewDonorsPanel_Enhanced() {
        setBackground(UIConstants_Enhanced.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        donorDAO = new DonorDAO();
        loadDonors();
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
        
        JLabel titleLabel = EnhancedComponents.createHeadingLabel("👥 All Registered Donors");
        titleLabel.setForeground(UIConstants_Enhanced.ACCENT_PURPLE);
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
            new String[]{"ID", "Donor Name", "Email", "Phone Number"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        donorsTable = new JTable(model);
        donorsTable.setFont(UIConstants_Enhanced.FONT_BODY);
        donorsTable.setRowHeight(40);
        donorsTable.setBackground(UIConstants_Enhanced.BG_TERTIARY);
        donorsTable.setForeground(UIConstants_Enhanced.TEXT_PRIMARY);
        donorsTable.setGridColor(UIConstants_Enhanced.BORDER_COLOR);
        donorsTable.setShowGrid(true);
        donorsTable.setShowHorizontalLines(true);
        donorsTable.setShowVerticalLines(false);
        
        // Header styling
        JTableHeader header = donorsTable.getTableHeader();
        header.setBackground(UIConstants_Enhanced.BG_SECONDARY);
        header.setForeground(UIConstants_Enhanced.ACCENT_PURPLE);
        header.setFont(UIConstants_Enhanced.FONT_SUBHEADING);
        header.setOpaque(true);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UIConstants_Enhanced.ACCENT_PURPLE));
        
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
        
        for (int i = 0; i < donorsTable.getColumnCount(); i++) {
            donorsTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            donorsTable.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        
        JScrollPane scrollPane = new JScrollPane(donorsTable);
        scrollPane.setBackground(UIConstants_Enhanced.BG_PRIMARY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIConstants_Enhanced.BG_PRIMARY);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadDonors() {
        List<Donor> donors = donorDAO.getAllDonors();
        DefaultTableModel model = (DefaultTableModel) donorsTable.getModel();
        model.setRowCount(0);
        
        for (Donor donor : donors) {
            model.addRow(new Object[]{
                donor.getDonorId(),
                donor.getDonorName(),
                donor.getEmail(),
                donor.getPhoneNumber()
            });
        }
    }
    
    public void setOnBack(Runnable callback) { this.onBack = callback; }
}
