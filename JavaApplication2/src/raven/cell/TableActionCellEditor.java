
package raven.cell;

import javax.swing.DefaultCellEditor;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author asma8
 */
public class TableActionCellEditor extends DefaultCellEditor {
    private TableActionEvent event;
    public TableActionCellEditor(TableActionEvent even) {
        super(new JCheckBox());
        this.event = event;
    }
    @Override
     public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row,int column){
        PanelAction action = new PanelAction();
        action.setBackground(jtable.getSelectionBackground());
        action.initEvent(event, row);
        return action;
    }
    
}

