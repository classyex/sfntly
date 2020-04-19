package com.github.classyex.sfntlyapi.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "global")
@Data
@Slf4j
public class GlobalConfig {
    /** 上传目录 */
    private String uploadPath;
    /** 请求前缀 */
    private String requestPrefix;

    @PostConstruct
    public void mkdirs() {
        if (StrUtil.isNotBlank(uploadPath)) {
            File dir = new File(uploadPath);
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
                log.info("创建上传目录[{}]", uploadPath);
            }
        }
    }

}
