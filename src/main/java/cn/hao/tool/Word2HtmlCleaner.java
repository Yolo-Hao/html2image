package cn.hao.tool;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * 使用word转html功能后会带上一些影响生成效果的标签<br>
 * 使用该类可以帮助清理这些标签。
 * @author hao
 * @since 2021-11-29
 */
public class Word2HtmlCleaner {

    private final File source;

    private final File target;

    public Word2HtmlCleaner(File source, File target) {
        this.source = source;
        this.target = target;
    }

    public void clean() throws Exception {
        String content = FileUtils.readFileToString(source);
        content = content.replaceAll("lang=EN-US", "");
        content = content.replaceAll("<span >(\\d+)</span>", "$1");
        content = content.replaceAll("<span\\n>(\\d+)</span>", "$1");
        content = content.replaceAll("<meta.+?>", "");
        FileUtils.write(target, content, false);
    }

    public static void main(String[] args) throws Exception {
        Conf conf = Conf.getInstance();
        File htmlFile = new File(conf.getRootPath() + "html/demo.html");
        File ftlFile = new File(conf.getRootPath() + "templates/demo.ftl");
        Word2HtmlCleaner cleaner = new Word2HtmlCleaner(htmlFile, ftlFile);
        cleaner.clean();
    }
}
