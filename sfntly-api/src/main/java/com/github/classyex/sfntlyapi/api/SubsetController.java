package com.github.classyex.sfntlyapi.api;

import cn.hutool.core.util.IdUtil;
import com.github.classyex.sfntlyapi.common.FontSet;
import com.github.classyex.sfntlyapi.config.GlobalConfig;
import com.github.classyex.sfntlyapi.util.SfntToolWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

import static com.github.classyex.sfntlyapi.api.CommonDownload.DownloadType.OCTET_STREAM;


/**
 * 提取字体子集
 * @author yex <br>
 * @version 1.0 <br>
 * @date 2020/3/24 16:40 <br>
 */
@Controller
@RequestMapping("/font/subsets")
@AllArgsConstructor
@Slf4j
public class SubsetController {

    private GlobalConfig globalConfig;
    private FontSet fontSet;

    /**
     * subset <br>
     * @version 1.0 <br>
     * @date 2020/3/24 16:48 <br>
     * @author yex <br>
     *
     * @param fontId 字体id
     * @param content 子集字体文字
     * @param type 生成的目标类型，比如woff
     * @param response
     * @return void
     */
    @GetMapping("/{fontId}")
    public void subset(@PathVariable String fontId, String content, FontExtension type, HttpServletResponse response) throws Exception {
        SfntToolWrapper tool = new SfntToolWrapper(content, type);
        String outFile = getOutFile(type);
        tool.subsetFontFile(fontSet.get(fontId), outFile);
        new CommonDownload(fontId + "." + type, new FileInputStream(outFile), OCTET_STREAM, response).invok();
    }

    private String getOutFile(FontExtension type) {
        return globalConfig.getUploadPath() + File.separator + IdUtil.randomUUID() + "." + type;
    }

}
