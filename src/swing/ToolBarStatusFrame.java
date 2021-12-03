package swing;

import javax.swing.*;
import java.awt.*;

/**
 * @Description
 * @Author admin
 * @Date 2021/11/26 16:54
 */
class ToolBarStatusFrame extends JFrame {
	private final JToolBar toolbar = new JToolBar();
	private final JLabel status = new JLabel();

	public ToolBarStatusFrame() {
		Container cp = getContentPane();
		cp.add(toolbar, BorderLayout.NORTH);
		cp.add(status, BorderLayout.SOUTH);
	}

	public void setToolBarComponentsEnabled(boolean... enabled) {
		for (int i = 0; i < enabled.length; i++) {
			toolbar.getComponent(i).setEnabled(enabled[i]);
		}
	}

	public void addToolBarComponents(JComponent... comp) {
		for (int i = 0; i < comp.length; i++) {
			toolbar.add(comp[i]);
		}
	}

	public void setStatus(String statusText) {
		status.setText(statusText);
	}
}
