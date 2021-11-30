package cn.hao.tool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hao
 * @since 2021-11-30
 */
public class Runner {

    public static String genImage(String templateName, String tmpFilePrefix, Map<String, String> dataMap) {
        Conf conf = Conf.getInstance();
        String pdfFile = conf.getRootPath() + String.format("pdf/%s.pdf", tmpFilePrefix);
        Html2PdfUtils.genPdfFromFtl(dataMap, templateName, pdfFile);
        String targetFilePath = Conf.getInstance().getRootPath() + String.format("images/%s.jpg", tmpFilePrefix);
        if (!Pdf2ImageUtil.pdfToImage(pdfFile, targetFilePath)) {
            return null;
        }
        if (conf.isTrimImgWhiteSpace()) {
            File targetFile = new File(targetFilePath);
            TrimWhite trimWhite = new TrimWhite(targetFile);
            trimWhite.trim();
            trimWhite.write(targetFile);
        }
        return targetFilePath;
    }

    public static void main(String[] args) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("company", "宇宙第一公司");
        dataMap.put("phone", "10086");
        dataMap.put("email", "10086@163.com");
        String filePath = Runner.genImage("demo.ftl", "mydemo", dataMap);
        System.out.println(filePath);
    }

}
