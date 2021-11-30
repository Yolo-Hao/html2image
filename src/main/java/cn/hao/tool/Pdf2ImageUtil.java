package cn.hao.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf转图片
 *
 * @author hao
 * @since 2021-11-29
 */
@Slf4j
public class Pdf2ImageUtil {

    /**
     * 转换后的图片类型
     */
    private static final String IMG_TYPE = "jpg";

    /**
     * PDF转图片
     *
     * @param pdfFile   pdf文件路径
     * @param imageFile 生成图片存储路径
     * @return 是否转换成功
     */
    public static boolean pdfToImage(String pdfFile, String imageFile) {
        try {
            List<BufferedImage> images = new ArrayList<>();
            try (PDDocument document = PDDocument.load(new FileInputStream(pdfFile))) {
                PDFRenderer renderer = new PDFRenderer(document);
                for (int i = 0; i < document.getNumberOfPages(); ++i) {
                    BufferedImage bufferedImage = renderer.renderImageWithDPI(i, Conf.getInstance().getImgDpi());
                    images.add(bufferedImage);
                }
            }
            return joinImages(images, imageFile);
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

    /**
     * 将宽度相同的图片，竖向追加在一起 ##注意：宽度必须相同
     *
     * @param images  文件流数组
     * @param outPath 输出路径
     */
    public static boolean joinImages(List<BufferedImage> images, String outPath) {// 纵向处理图片
        if (images == null || images.size() <= 0) {
            return false;
        }
        try {
            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0, // 临时的高度，主要保存每个高度
                    picNum = images.size();// 图片的数量
            File fileImg = null; // 保存读取出的图片
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage buffer = null; // 保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            for (int i = 0; i < picNum; i++) {
                buffer = images.get(i);
                heightArray[i] = _height = buffer.getHeight();// 图片高度
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度
                }
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0) _height += __height; // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
            }
            File outFile = new File(outPath);
            ImageIO.write(imageResult, IMG_TYPE, outFile);// 写图片
            return true;
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

}
