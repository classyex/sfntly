package com.github.classyex.sfntlyapi.common;

import cn.hutool.core.io.resource.ClassPathResource;
import com.github.classyex.sfntlyapi.config.GlobalConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author yex <br>
 * @version 1.0 <br>
 * @date 2020/3/26 12:09 <br>
 */
@Component
@AllArgsConstructor
@Slf4j
public class FontSet {

    private static final String FONTS_DIR = "fonts";
    private static HashMap<String, String> fontSet = new HashMap<>();
    private GlobalConfig globalConfig;

    @PostConstruct
    public void initFonts() {
        initTargetDir();
        FontResource font1 = new FontResource("10000", "微软雅黑", FONTS_DIR + "/msyh.ttc");
        copyFontFile(font1);
        FontResource font2 = new FontResource("10001", "默陌老屋手迹", FONTS_DIR + "/mmlwsj.ttf");
        copyFontFile(font2);
    }

    private void copyFontFile(FontResource fontResource) {
        ClassPathResource font = new ClassPathResource(fontResource.path);
        try {
            String target = globalConfig.getUploadPath() + File.separator + fontResource.path;
            Files.copy(font.getStream(), Paths.get(target), REPLACE_EXISTING);
            fontSet.put(fontResource.fontId, target);
        } catch (IOException e) {
            log.info("复制字体文件[{}]出错", fontResource.path, e);
        }
    }

    private void initTargetDir() {
        String fontDir = globalConfig.getUploadPath() + File.separator + FONTS_DIR;
        File dir = new File(fontDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public String get(String fontId) {
        return fontSet.get(fontId);
    }

    @Data
    @AllArgsConstructor
    public static class FontResource {
        private String fontId;
        private String fontName;
        private String path;
    }



}
