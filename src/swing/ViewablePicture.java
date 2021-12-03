package swing;

import image.ImageInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * @Description
 * @Author admin
 * @Date 2021/11/26 16:53
 */
class ViewablePicture extends JTable {
    private Point oldCursorPos;
    private List<ImageInfo> imageList;

    public ViewablePicture(List<ImageInfo> imageList) {
        this.imageList = imageList;
//        addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseDragged(MouseEvent e) {
//                dragTo(e.getLocationOnScreen());
//            }
//        });
//        addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent e) {
//                startDragging(e.getLocationOnScreen());
//            }
//
//            public void mouseReleased(MouseEvent e) {
//                stopDragging();
//            }
//        });
        initTable();
    }

    private void initTable() {


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
