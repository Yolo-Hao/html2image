package cn.hao.tool;

import lombok.Data;

/**
 * @author hao
 * @since 2021-11-29
 */
@Data
public class Conf {

    /**
     * 文件目录
     */
    private String rootPath;

    /**
     * dpi越大转换后的图片越清晰，相对转换速度越慢，体积越大
     */
    private Integer imgDpi;

    /**
     * 是否裁减掉图片底部的空白区域
     */
    private boolean trimImgWhiteSpace;

    /**
     * 裁剪图片底部空白区域之后保留多少空白区域
     */
    private int imgWhiteSpaceHeight;

    private Conf() {
    }

    private static volatile Conf conf;

    public static Conf getInstance() {
        if (conf == null) {
            synchronized (Conf.class) {
                if (conf == null) {
                    conf = new Conf();
                    conf.rootPath = System.getProperty("rootPath", System.getProperty("user.dir") + "/DEMO_SOURCE/");
                    conf.imgDpi = Integer.parseInt(System.getProperty("imgDpi", "100"));
                    conf.trimImgWhiteSpace = Boolean.parseBoolean(System.getProperty("trimImgWhiteSpace", "true"));
                    conf.imgWhiteSpaceHeight = Integer.parseInt(System.getProperty("imgWhiteSpaceHeight", "30"));
                }
            }
        }
        return conf;
    }

}
