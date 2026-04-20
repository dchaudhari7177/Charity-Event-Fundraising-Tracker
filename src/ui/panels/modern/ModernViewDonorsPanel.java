package ui.panels.modern;

import ui.ModernUIConstants;
import ui.components.ModernComponents;
import ui.components.ModernTableRenderer;
import dao.DonorDAO;
import model.Donor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Modern View Donors Panel - Premium table for viewing all donors
 * Features modern styling with alternating rows and hover effects
 */
public class ModernViewDonorsPanel extends JPanel {
    
    private Runnable onBackClick;
    private DonorDAO donorDAO;
    private JTable donorsTable;
    private DefaultTableModel tableModel;
    private ModernComponents.ModernButton backButton;
    private ModernComponents.ModernButton refreshButton;
    
    public ModernViewDonorsPanel() {
        this.donorDAO = new DonorDAO();
        setBackground(ModernUIConstants.BG_PRIMARY);
        setLayout(new BorderLayout());
        
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        
        loadDonors();
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
        
        JLabel titleLabel = new ModernComponents.ModernLabel("All Donors",
            ModernUIConstants.FONT_SIZE_L, true);
        titleLabel.setForeground(ModernUIConstants.ACCENT_CYAN);
        
        JLabel subtitleLabel = new ModernComponents.ModernLabel(
            "View all registered donors in the system",
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
        String[] columnNames = {"Donor ID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        donorsTable = new JTable(tableModel);
        ModernTableRenderer.applyModernStyle(donorsTable);
        
        // Add to scroll pane
        JScrollPane scrollPane = ModernTableRenderer.createStyledScrollPane(donorsTable);
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
        refreshButton.addActionListener(e -> loadDonors());
        
        backButton = new ModernComponents.ModernButton("Back",
            ModernUIConstants.TEXT_TERTIARY, ModernUIConstants.TEXT_SECONDARY);
        backButton.addActionListener(e -> {
            if (onBackClick != null) onBackClick.run();
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }
    
    private void loadDonors() {
        tableModel.setRowCount(0);
        List<Donor> donors = donorDAO.getAllDonors();
        
        for (Donor donor : donors) {
            Object[] row = {
                donor.getDonorId(),
                donor.getDonorName(),
                donor.getEmail(),
                donor.getPhoneNumber()
            };
            
            tableModel.addRow(row);
        }
    }
    
    public void setOnBackClick(Runnable callback) {
        this.onBackClick = callback;
    }
}
