package swing;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @Description
 * @Author admin
 * @Date 2021/11/26 16:53
 */
class ScrollablePicture extends ZoomablePicture {
	private Point oldCursorPos;

	public ScrollablePicture() {
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				dragTo(e.getLocationOnScreen());
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startDragging(e.getLocationOnScreen());
			}
			public void mouseReleased(MouseEvent e) {
				stopDragging();
			}
		});
	}

	public void load(String filename) {
		super.load(filename);
		scrollRectToVisible(new Rectangle()); // 滚动到左上角位置
	}

	private void startDragging(Point cursorPos) {
		oldCursorPos = cursorPos;
		setCursor(new Cursor(Cursor.MOVE_CURSOR));
	}

	private void stopDragging() {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private void dragTo(Point newCursorPos) {
		int dx = newCursorPos.x - oldCursorPos.x;
		int dy = newCursorPos.y - oldCursorPos.y;
		Rectangle visibleRect = getVisibleRect();
		visibleRect.translate(-dx, -dy);
		scrollRectToVisible(visibleRect);
		oldCursorPos = newCursorPos;
	}
}
