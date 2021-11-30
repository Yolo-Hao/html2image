package cn.hao.tool;

import java.net.URL;

/**
 * @author hao
 * @since 2021-11-29
 */
public class Utils {

    public static String getResourceDir(String name) {
        URL url = Utils.class.getClassLoader().getResource(name);
        if (url == null) {
            return null;
        }
        return url.getPath();
    }

}
