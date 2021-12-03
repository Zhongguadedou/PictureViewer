package image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Description
 * @Author admin
 * @Date 2021/11/26 18:04
 */

public class ImageInfo {

    private Image image;

    private String name;

    private String size;

    private String path;

    public ImageInfo(BufferedImage image, String name, String path) {
        this.image = image;
        this.name = name;
        this.path = path;
        this.size = image.getWidth() + "x" + image.getHeight();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
