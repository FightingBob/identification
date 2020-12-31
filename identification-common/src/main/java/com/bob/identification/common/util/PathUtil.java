package com.bob.identification.common.util;

import cn.hutool.core.util.StrUtil;
import com.bob.identification.common.exception.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 项目路径工具
 * Created by LittleBob on 2020/12/31/031.
 */
public class PathUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathUtil.class);

    public static String getResourceBasePath() {
        // 获取根目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }
        String pathStr = path.getAbsolutePath();
        pathStr = pathStr.replace("\\target\\classes", "");
        return pathStr;
    }

    public static void ensureDirectory(String filePath) {
        if (StrUtil.isBlank(filePath)) {
            Asserts.fail("该路径不存在");
            return;
        }
        filePath = replaceSeparator(filePath);
        if (filePath.contains("/")) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    private static String replaceSeparator(String str) {
        return str.replace("\\", "/").replace("\\\\","/");
    }
}
