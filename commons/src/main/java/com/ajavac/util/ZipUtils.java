package com.ajavac.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip压缩工具
 * Created by wyp0596 on 21/06/2017.
 */
public final class ZipUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 压缩文件或者文件夹
     *
     * @param input  待压缩文件或者文件夹
     * @param output 输出压缩文件
     * @throws UncheckedIOException 当input不存在,或者其它IO错误时抛出IO异常
     */
    public static void zip(File input, File output) {
        zip(input, output, true);
    }

    /**
     * 压缩文件或者文件夹
     *
     * @param input         待压缩文件或者文件夹
     * @param output        输出压缩文件
     * @param withParentDir 若input是文件时是否包含该文件夹
     * @throws UncheckedIOException 当input不存在,或者其它IO错误时抛出IO异常
     */
    public static void zip(File input, File output, boolean withParentDir) {
        Map<String, File> entryMap = getEntryMap(input, withParentDir);
        zip(entryMap, output);
    }

    /**
     * 压缩文件列表
     *
     * @param inputFiles 待压缩文件列表
     * @param output     输出压缩文件
     * @throws UncheckedIOException 当input不存在,或者其它IO错误时抛出IO异常
     */
    public static void zip(List<File> inputFiles, File output) {

        Map<String, File> entryMap = getEntryMap(inputFiles);
        zip(entryMap, output);
    }

    private static void zip(Map<String, File> entryMap, File output) {
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(output)),
                DEFAULT_CHARSET)) {
            for (Map.Entry<String, File> mapEntry : entryMap.entrySet()) {
                String entryName = mapEntry.getKey();
                File file = mapEntry.getValue();
                zos.putNextEntry(new ZipEntry(entryName));
                if (file.isDirectory()) {
                    continue;
                }
                byte[] buffer = new byte[1024];
                try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, len);
                    }
                }
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Map<String, File> getEntryMap(File file, boolean withParentDir) {
        String parentPath = withParentDir ? file.getParent() : file.getPath();
        return getEntryMap(file, parentPath);
    }

    private static Map<String, File> getEntryMap(List<File> fileList) {
        Map<String, File> entryMap = new LinkedHashMap<>();
        for (File file : fileList) {
            entryMap.putAll(getEntryMap(file, file.getParent()));
        }
        return entryMap;
    }

    private static Map<String, File> getEntryMap(File file, String parentPath) {
        Map<String, File> entryMap = new LinkedHashMap<>();
        // is a File
        if (file.isFile()) {
            putEntry(file, parentPath, entryMap);
            return entryMap;
        }
        // is a Dir
        File[] files = file.listFiles();
        if (files == null) {
            throw new NullPointerException();
        }
        // is an empty Dir and not parentDir
        if (files.length == 0 && !parentPath.equals(file.getPath())) {
            putEntry(file, parentPath, entryMap);
            return entryMap;
        }
        for (File aFile : files) {
            if (aFile.isFile()) {
                putEntry(aFile, parentPath, entryMap);
            } else {
                entryMap.putAll(getEntryMap(aFile, parentPath));
            }
        }
        return entryMap;
    }

    private static void putEntry(File file, String parentPath, Map<String, File> entryMap) {
        String path = file.getPath();
        String entryName = path.substring(path.indexOf(parentPath) + parentPath.length())
                + (file.isDirectory() ? "/" : "");
        entryMap.put(entryName, file);
    }

}
