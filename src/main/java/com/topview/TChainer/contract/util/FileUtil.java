package com.topview.TChainer.contract.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * @author 刘家辉
 * @date 2023/09/25
 */
@Slf4j
public class FileUtil {
    public static void write(File file, String content) throws IOException {
        FileUtils.writeStringToFile(file, content, "UTF-8");
    }

    public static void create(File file) throws IOException {
        FileUtils.touch(file);
    }

    public static String read(String path) throws IOException {
        File file = new File(path);
        return  FileUtils.readFileToString(file, "UTF-8");
    }
}
