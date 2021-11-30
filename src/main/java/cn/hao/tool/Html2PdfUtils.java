package cn.hao.tool;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * html转pdf
 *
 * @author hao
 * @since 2021-11-29
 */
@Slf4j
public class Html2PdfUtils {

    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

    static {
        try {
            cfg.setDirectoryForTemplateLoading(new File(Conf.getInstance().getRootPath() + "templates"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setWrapUncheckedExceptions(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean genPdfFromFtl(Map<String, String> dataMap, String templateFile, String storePath) {
        try {
            Template temp = cfg.getTemplate(templateFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Writer out = new OutputStreamWriter(byteArrayOutputStream);
            temp.process(dataMap, out);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            OutputStream os = new FileOutputStream(storePath);
            Html2PdfUtils.writeToOutputStreamAsPDF(byteArrayInputStream, os);
            return true;
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

    private static void writeToOutputStreamAsPDF(InputStream html, OutputStream os) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
        document.open();
        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        worker.parseXHtml(pdfWriter, document, html, StandardCharsets.UTF_8, AsianFontProvider.getInstance());
        document.close();
    }

}
