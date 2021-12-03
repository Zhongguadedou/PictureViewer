package swing;

import javax.swing.*;
import java.awt.*;

/**
 * @Description
 * @Author admin
 * @Date 2021/11/26 16:54
 */
class ZoomablePicture extends JComponent {
	private Image image;
	private int width, height;
	private float zoomFactor;

	public void load(String filename) {
		unload();
		image = Toolkit.getDefaultToolkit().getImage(filename);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(image, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		width = image.getWidth(null);
		height = image.getHeight(null);
		zoomFactor = 1.0f;
		setPreferredSize(new Dimension(width, height));
		revalidate();
		repaint();
	}

	public void unload() {
		if (image != null) {
			image = null;
			setPreferredSize(new Dimension(1, 1));
			revalidate();
			repaint();
		}
	}

	public void setZoomFactor(float factor) {
		zoomFactor = factor;
		setPreferredSize(new Dimension((int) (width * zoomFactor), (int) (height * zoomFactor)));
		revalidate();
		repaint();
	}

	public float getZoomFactor() {
		return zoomFactor;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			int ws = getWidth();
			int hs = getHeight();
			int wp = getPreferredSize().width;
			int hp = getPreferredSize().height;
			g.drawImage(image, (ws - wp) / 2, (hs - hp) / 2, wp, hp, null);
		}
	}
}
