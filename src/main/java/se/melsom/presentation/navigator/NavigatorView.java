package se.melsom.presentation.navigator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;

@SuppressWarnings("serial")
public class NavigatorView extends JInternalFrame {
	private JSplitPane splitPane = new JSplitPane();
	private JTree tree;
	private final JPanel panel = new JPanel();

	public NavigatorView(TreeModel treeModel, TreeSelectionListener listener) {
		setResizable(true);
		splitPane.setResizeWeight(0.25);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		tree = new JTree(treeModel);
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(listener);
		splitPane.setLeftComponent(new JScrollPane(tree));		
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		panel.setBorder(new LineBorder(Color.BLUE));
		JScrollPane scrollPane = new JScrollPane(panel);
		splitPane.setRightComponent(scrollPane);
	}
	
	public Object getSelectedNode() {
		return tree.getLastSelectedPathComponent();
	}
	
	public void setViewComponent(JComponent viewComponent) {
		panel.removeAll();
		
		if (viewComponent != null) {
			panel.add(viewComponent);
		}
	}
}
