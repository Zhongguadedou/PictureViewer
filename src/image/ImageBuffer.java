package image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ImageBuffer {
    private ArrayList<BufferedImage> ImageList = new ArrayList<BufferedImage>();

    //Nuskaito is URL paveiksliukus
    public ImageBuffer(ArrayList<String> urlStringArray, int imageCount) throws IOException {
        for (int i = 0; i < imageCount; i++) {
            this.ImageList.add(ImageIO.read(new URL(urlStringArray.get(i))));
        }
    }

    public ArrayList<BufferedImage> getImageList() {
        return ImageList;
    }

}
