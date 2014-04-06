package Obj_Administracion_del_Sistema;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class Obj_CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {

	Obj_CheckBoxNodeRenderer renderer = new Obj_CheckBoxNodeRenderer();

	ChangeEvent changeEvent = null;
	
	JTree tree;
	
	public Obj_CheckBoxNodeEditor(JTree tree) {
		this.tree = tree;
	}
	
	public Object getCellEditorValue() {
		JCheckBox checkbox = renderer.getLeafRenderer();
		Obj_CheckBoxNode checkBoxNode = new Obj_CheckBoxNode(checkbox.getText(), checkbox.isSelected());
		return checkBoxNode;
	}

	public boolean isCellEditable(EventObject event) {
		boolean returnValue = false;
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
			if (path != null) {
				Object node = path.getLastPathComponent();
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
					Object userObject = treeNode.getUserObject();
					returnValue = ((treeNode.isLeaf()) && (userObject instanceof Obj_CheckBoxNode));
				}
			}
		}
		return returnValue;
	}
	
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row) {
		Component editor = renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, true);
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (stopCellEditing()) {
					fireEditingStopped();
				}
			}
		};
		if (editor instanceof JCheckBox) {
			((JCheckBox) editor).addItemListener(itemListener);
		}
		return editor;
	}
}
