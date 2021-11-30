package cn.hao.tool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片空白区域裁剪
 *
 * @author hao
 * @since 2021-11-30
 */
public class TrimWhite {
    private BufferedImage img;

    public TrimWhite(File input) {
        try {
            img = ImageIO.read(input);
        } catch (IOException e) {
            throw new RuntimeException("Problem reading image", e);
        }
    }

    public void trim() {
        int width = this.img.getWidth();
        int height = getTrimmedHeight();

        BufferedImage newImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = newImg.createGraphics();
        g.drawImage(img, 0, 0, null);
        img = newImg;
    }

    public void write(File f) {
        try {
            ImageIO.write(img, "bmp", f);
        } catch (IOException e) {
            throw new RuntimeException("Problem writing image", e);
        }
    }

    private int getTrimmedHeight() {
        int width = this.img.getWidth();
        int height = this.img.getHeight();
        int trimmedHeight = 0;

        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                if (img.getRGB(i, j) != Color.WHITE.getRGB() &&
                        j > trimmedHeight) {
                    trimmedHeight = j;
                    break;
                }
            }
        }

        return Math.min(trimmedHeight + 30, height);
    }

}
