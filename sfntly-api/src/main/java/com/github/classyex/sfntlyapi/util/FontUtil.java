package com.github.classyex.sfntlyapi.util;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yex
 * @date 2019/12/28 19:43
 */
public class FontUtil {

    private static final Map<String, Font> cache;

    public static final String FONTS_MSYH = "fonts/msyh.ttc";

    static {
        cache = new HashMap<>();
        Font font = getSelfDefinedFont(FONTS_MSYH);
        cache.put(FONTS_MSYH, font);
    }

    public static Font getSelfDefinedFont(String fontName){
        try{
            File file = FontUtil.loadClassPathFile(fontName);
            Font font = Font.createFont(Font.TRUETYPE_FONT, file);
            font = font.deriveFont(Font.PLAIN, 24);
            return font;
        }catch (FontFormatException | FileNotFoundException e){
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static Font getSelfDefinedFont(String fontName, int plain, int fontSize){
        Font font = cache.get(fontName);
        if (font == null) {
            font = getSelfDefinedFont(fontName);
            cache.put(fontName, font);
        }
        font = font.deriveFont(plain, fontSize);
        return font;
    }

    /**
     * 加载classpath路径的文件
     * @param filepath
     * @return
     */
    public static File loadClassPathFile(String filepath) throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath)) {
            Path ttc = Files.createTempFile("/tem", "ttc");
            assert is != null;
            Files.copy(is, ttc);
            return ttc.toFile();
        } catch (IOException e) {
            throw new IOException("加载文件[" + filepath + "]失败", e);
        }
    }

}
