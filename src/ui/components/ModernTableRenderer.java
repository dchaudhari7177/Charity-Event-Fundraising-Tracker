package ui.components;

import ui.ModernUIConstants;
import ui.util.GraphicsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Modern Table Renderer - Premium table styling with hover effects
 */
public class ModernTableRenderer {
    
    /**
     * Configure a JTable with modern styling
     */
    public static void applyModernStyle(JTable table) {
        // Set table properties
        table.setBackground(ModernUIConstants.BG_SECONDARY);
        table.setForeground(ModernUIConstants.TEXT_PRIMARY);
        table.setSelectionBackground(ModernUIConstants.BG_TERTIARY);
        table.setSelectionForeground(ModernUIConstants.ACCENT_CYAN);
        table.setGridColor(ModernUIConstants.TABLE_GRID_COLOR);
        table.setRowHeight(40);
        table.setShowGrid(true);
        table.setFont(ModernUIConstants.FONT_BODY);
        table.setOpaque(false);
        
        // Style header
        JTableHeader header = table.getTableHeader();
        header.setBackground(ModernUIConstants.BG_TERTIARY);
        header.setForeground(ModernUIConstants.ACCENT_CYAN);
        header.setFont(ModernUIConstants.FONT_HEADING);
        header.setPreferredSize(new Dimension(0, 45));
        
        // Apply custom cell renderer
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new ModernTableCellRenderer());
        }
    }
    
    /**
     * Modern table cell renderer with alternating row colors
     */
    public static class ModernTableCellRenderer extends DefaultTableCellRenderer {
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            
            label.setFont(ModernUIConstants.FONT_BODY);
            label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            label.setOpaque(false);
            
            if (isSelected) {
                label.setForeground(ModernUIConstants.ACCENT_CYAN);
                label.setBackground(ModernUIConstants.BG_TERTIARY);
            } else {
                // Alternating row colors
                if (row % 2 == 0) {
                    label.setBackground(ModernUIConstants.BG_SECONDARY);
                } else {
                    label.setBackground(ModernUIConstants.TABLE_ROW_ALTERNATE);
                }
                label.setForeground(ModernUIConstants.TEXT_PRIMARY);
            }
            
            return label;
        }
    }
    
    /**
     * Hover effect renderer for table rows
     */
    public static class HoverTableCellRenderer extends DefaultTableCellRenderer {
        private int hoveredRow = -1;
        
        public void setHoveredRow(int row) {
            this.hoveredRow = row;
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            
            label.setFont(ModernUIConstants.FONT_BODY);
            label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            
            if (isSelected) {
                label.setBackground(ModernUIConstants.BG_TERTIARY);
                label.setForeground(ModernUIConstants.ACCENT_CYAN);
                label.setOpaque(true);
            } else if (row == hoveredRow) {
                label.setBackground(ModernUIConstants.TABLE_ROW_HOVER);
                label.setForeground(ModernUIConstants.TEXT_PRIMARY);
                label.setOpaque(true);
            } else {
                if (row % 2 == 0) {
                    label.setBackground(ModernUIConstants.BG_SECONDARY);
                } else {
                    label.setBackground(ModernUIConstants.TABLE_ROW_ALTERNATE);
                }
                label.setForeground(ModernUIConstants.TEXT_PRIMARY);
                label.setOpaque(true);
            }
            
            return label;
        }
    }
    
    /**
     * Create a custom JTable with modern styling
     */
    public static JTable createModernTable(String[] columnNames, Object[][] data) {
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        applyModernStyle(table);
        return table;
    }
    
    /**
     * Create a scrollpane wrapper for tables
     */
    public static JScrollPane createStyledScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(ModernUIConstants.BG_SECONDARY);
        scrollPane.getViewport().setBackground(ModernUIConstants.BG_SECONDARY);
        scrollPane.setBorder(BorderFactory.createLineBorder(
            ModernUIConstants.BORDER_LIGHT, 1));
        
        // Style scrollbars
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        
        verticalBar.setUI(new ModernScrollBarUI());
        horizontalBar.setUI(new ModernScrollBarUI());
        
        return scrollPane;
    }
    
    /**
     * Modern scroll bar UI
     */
    static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = ModernUIConstants.BORDER_ACCENT;
            this.trackColor = ModernUIConstants.BG_SECONDARY;
            this.thumbDarkShadowColor = ModernUIConstants.BORDER_LIGHT;
            this.thumbHighlightColor = ModernUIConstants.ACCENT_CYAN;
        }
    }
}
