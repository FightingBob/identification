package com.bob.identification.common.util;

import cn.hutool.core.util.StrUtil;
import com.bob.identification.common.exception.Asserts;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * txt文件导出工具
 * Created by LittleBob on 2020/12/31/031.
 */
public class ExportTxtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportTxtUtil.class);

    /**
     * 导出txt文件
     * @param response 响应
     * @param fileName 文件名称
     * @param relativePath 相对地址
     */
    public static void exportTxt(HttpServletResponse response, String fileName, String relativePath) {
        OutputStream os = null;
        InputStream is = null;
        try {
            // 取得输出刘
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            // 读取流
            String basePath = System.getProperty("user.dir");
            PathUtil.ensureDirectory(basePath + File.separator + relativePath);

            File file = new File(basePath, relativePath);
            is = new FileInputStream(file);
            if (is == null) {
                LOGGER.error("下载附加失败，请检查文件" + fileName + "是否存在");
                Asserts.fail("下载附加失败，请检查文件" + fileName + "是否存在");
            }
            // 复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            Asserts.fail("下载附加失败");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static void deleteTxt(String relativePath) {
        // 读取流
        String basePath = System.getProperty("user.dir");
        File file = new File(basePath, relativePath);
        String folderPath = StrUtil.reverse(StrUtil.removePreAndLowerFirst(StrUtil.reverse(relativePath), 24));
        System.out.println(folderPath);
        FileSystemUtils.deleteRecursively(file);
        FileSystemUtils.deleteRecursively(new File(basePath, folderPath));
    }

    public static String listToTxt(List<String> list) {
        return listToTxt(list, MyIdUtil.getSnowflake(), MyIdUtil.getSnowflake());
    }

    private static String listToTxt(List<String> list, String directory, String fileName) {
        String absolutePath = getRelativePath(directory, fileName);
        return listToTxt(list, absolutePath) ? absolutePath : null;
    }

    private static String getRelativePath(String directory, String fileName) {
        //String basePath = PathUtil.getResourceBasePath();
        String basePath = System.getProperty("user.dir");
        String relativePath = "serial_number" + File.separator + directory + File.separator + fileName + ".txt";
        String absolutePath = new File(basePath, relativePath).getAbsolutePath();
        PathUtil.ensureDirectory(absolutePath);
        return relativePath;
    }

    /**
     * list生成txt
     *
     * @param list         list
     * @param absolutePath 绝对路径
     * @return 生成状态
     */
    private static boolean listToTxt(List<String> list, String absolutePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absolutePath)));
            writer.write("防伪编号" + "\r\n");
            for (int i = 0; i < (list.size() - 1); i++) {
                writer.write(list.get(i) + "\r\n");
            }
            // 加入最后一行
            writer.write(list.get(list.size() - 1));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }
}
