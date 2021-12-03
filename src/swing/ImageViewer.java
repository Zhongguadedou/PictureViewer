package swing;

import image.ImageInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

final class ImageViewer extends ToolBarStatusFrame {
    private List<String> pictureList = new ArrayList<>();
    private int pictureIndex = -1;
    private JButton btn_winTopButton;
    private ZoomablePicture view = new ScrollablePicture();
    private JTable tableList;
    private List<ImageInfo> imageInfoList = new ArrayList<>();


    public static void main(String[] args) {
        new ImageViewer();
    }

    public ImageViewer() {
        setTitle("图片查看器");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createToolBarButtons();
        setToolBarComponentsEnabled(true, false, false, false, false, true);

        // 初始化图片
        initImageTable();
        JScrollPane picJScrollPane = new JScrollPane(view);
        JScrollPane tableScrollPane = new JScrollPane(tableList);
        tableList.setFillsViewportHeight(true);
        JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, picJScrollPane, tableScrollPane);
        main.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                main.setDividerLocation(3.0 / 5.0);
            }
        });
        //分割线的位置  也就是初始位置
        main.setOneTouchExpandable(false); //是否可展开或收起，在这里没用
        main.setDividerSize(2);//设置分割线的宽度 像素为单位
        main.setEnabled(true); //设置分割线不可拖动！！

        getContentPane().add(main);
        showCurrentPicture();

        setVisible(true);


    }

    private void initImageTable() {
        List<String> dogUrlList = Arrays.asList(
                "img/n02088094_1003.jpg",
                "img/n02088094_1007.jpg",
                "img/n02088094_1023.jpg",
                "img/n02088094_10263.jpg",
                "img/n02088094_10715.jpg",
                "img/n02088094_10822.jpg",
                "img/n02088094_1234.jpg",
                "img/n02088094_12364.jpg",
                "img/n02088094_1254.jpg",
                "img/n02088094_12563.jpg",
                "img/n02088094_1259.jpg",
                "img/n02088094_12664.jpg",
                "img/n02088094_1270.jpg",
                "img/n02088094_12867.jpg",
                "img/n02088094_12879.jpg",
                "img/n02088094_12945.jpg",
                "img/n02088094_1301.jpg",
                "img/n02088094_2062.jpg",
                "img/n02088094_2131.jpg",
                "img/n02088094_2173.jpg",
                "img/n02088094_227.jpg",
                "img/n02088094_2292.jpg",
                "img/n02088094_231.jpg",
                "img/n02088094_3051.jpg",
                "img/n02088094_3057.jpg");

        for (int i = 0; i < dogUrlList.size(); i++) {
            String file = dogUrlList.get(i);
            addFile(file);
        }

        DefaultTableModel tableModel = getDefaultTableModel();
        tableList = new JTable(tableModel) {
            //表格不允许被编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rowClicked();
            }
        });
    }

    private DefaultTableModel getDefaultTableModel() {
        DefaultTableModel tableModel;
        if (!imageInfoList.isEmpty()) {
            Vector<Vector> data = new Vector<>();
            for (ImageInfo imageInfo : imageInfoList) {
                data.add(new Vector(Arrays.asList(imageInfo.getName(), imageInfo.getSize(), imageInfo.getPath())));
            }
            tableModel = new DefaultTableModel(data, new Vector(Arrays.asList("文件名", "大小", "位置")));
            tableModel.setColumnCount(3);
            tableModel.setRowCount(imageInfoList.size());
        } else {
            tableModel = new DefaultTableModel();
        }
        return tableModel;
    }

    private void addFile(String path) {
        try {
            File file = new File(path);
            BufferedImage bufferedImage = ImageIO.read(file);
            this.imageInfoList.add(new ImageInfo(bufferedImage, file.getName(), file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rowClicked() {
        int row = tableList.getSelectedRow();
        String filePath = (String) tableList.getModel().getValueAt(row, 2);
        int i = this.pictureList.indexOf(filePath);
        if (i > 0) {
            this.pictureIndex = i;
        } else {
            this.pictureList.add(filePath);
            this.pictureIndex = pictureList.size() - 1;
        }
        showCurrentPicture();
    }

    private class ToolbarButton extends JButton {
        public ToolbarButton(String text, String icon, ActionListener l) {
            super(text, new ImageIcon(ImageViewer.this.getClass().getClassLoader().getResource(icon)));
            addActionListener(l);
            setPreferredSize(new Dimension(100, 21));
        }
    }

    private void createToolBarButtons() {
        btn_winTopButton = new ToolbarButton("窗口置顶", "icons/icon_top.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAlwaysOnTop(!isAlwaysOnTop());
                if (isAlwaysOnTop()) {
                    btn_winTopButton.setText("取消置顶");

                } else {
                    btn_winTopButton.setText("窗口置顶");
                }
            }
        });

        addToolBarComponents(new ToolbarButton("打开/查找", "icons/document-open.png", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        choosePictures();
                        showCurrentPicture();
                    }
                }), new ToolbarButton("放大", "icons/list-add.png", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        view.setZoomFactor(view.getZoomFactor() * 1.1f);
                    }
                }), new ToolbarButton("缩小", "icons/list-remove.png", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        view.setZoomFactor(view.getZoomFactor() * 0.9f);
                    }
                }), new ToolbarButton("", "icons/go-previous.png", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        pictureIndex--;
                        showCurrentPicture();
                    }
                }), new ToolbarButton("", "icons/go-next.png", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        pictureIndex++;
                        showCurrentPicture();
                    }
                }), btn_winTopButton,

                new ToolbarButton("退出", "icons/system-log-out.png", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                }));
    }

    private void choosePictures() {
        MyFileChooser fc = new MyFileChooser(new MyFilterWrapper("图片文件", "jpg", "png", "gif"));
        int ret = fc.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            List<String> fileList = Arrays.asList(fc.getAbsolutePathsRecursively());
            if (!fileList.isEmpty()) {
                for (String file : fileList) {
                    int index = pictureList.indexOf(file);
                    if (index > 0) {
                        this.pictureIndex = index;
                    } else {
                        this.pictureList.add(file);
                        this.pictureIndex = this.pictureList.size() - 1;
                        // 添加扫描的文件到表格中
                        addFile(file);
                        tableList.setModel(getDefaultTableModel());
                    }
                }
            }
            this.pictureIndex = (this.pictureList.size() > 0) ? this.pictureList.size() - 1 : -1;
        }
    }

    private void showCurrentPicture() {
        int i = this.pictureIndex;
        if (i >= 0) {
            String filename = this.pictureList.get(i);
            this.view.load(filename);
            setStatus(String.format("[%d/%d] %s", i + 1, this.pictureList.size(), filename));
        } else {
            this.view.unload();
            setStatus("没有加载图片");
        }
        setToolBarComponentsEnabled(true, i >= 0, i >= 0, i > 0, i + 1 < this.pictureList.size(), true);
    }
}
