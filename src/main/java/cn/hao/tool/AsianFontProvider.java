package cn.hao.tool;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 宋体
 *
 * @author hao
 * @since 2021-11-29
 */
@Slf4j
public class AsianFontProvider extends XMLWorkerFontProvider {

    private static volatile AsianFontProvider SINGLETON;

    private AsianFontProvider() {
        // prevent custom init
    }

    public static AsianFontProvider getInstance() {
        if (SINGLETON == null) {
            synchronized (AsianFontProvider.class) {
                if (SINGLETON == null) {
                    SINGLETON = new AsianFontProvider();
                }
            }
        }
        return SINGLETON;
    }

    @Override
    public Font getFont(final String fontName, String encoding, float size, final int style) {
        try {
            BaseFont bfChinese = BaseFont.createFont(Objects.requireNonNull(Utils.getResourceDir("simsun.ttf")),
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            return new Font(bfChinese, size, style);
        } catch (Exception e) {
            log.error("", e);
        }
        return super.getFont(fontName, encoding, size, style);
    }
}
