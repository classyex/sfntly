package com.github.classyex.sfntlyapi.api;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yex <br>
 * @version 1.0 <br>
 * @date 2020/3/25 14:06 <br>
 */
public class CommonDownload {

    private String fileName;
    private InputStream inputStream;
    private DownloadType contentType;
    private HttpServletResponse response;

    public CommonDownload(String fileName, InputStream inputStream, DownloadType contentType, HttpServletResponse response) {
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.contentType = contentType;
        this.response = response;
    }

    public void invok()
            throws Exception {
        //声明本次下载状态的记录对象
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType(contentType.getContentType());
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //激活下载操作
        OutputStream os = response.getOutputStream();
        //循环写入输出流
        byte[] b = new byte[2048];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        // 这里主要关闭。
        os.close();
    }

    /**
     * CommonDownload.java <br>
     * @version 1.0 <br>
     * @date 2020/3/25 14:32 <br>
     * @author yex <br>
     */
    public enum DownloadType {

        /** 下载流 */
        OCTET_STREAM("application/octet-stream");

        private String contentType;

        DownloadType(String contentType) {
            this.contentType = contentType;
        }

        public String getContentType() {
            return contentType;
        }
    }

}
