package com.ajavac.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

/**
 * 命令行工具
 * Created by wyp0596 on 14/12/2016.
 */
public final class CommandUtils {

    public static String exec(String command) {
        // 执行程序
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        // 程序输出
        String output = read(process);

        // 等待程序执行结束并输出状态
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    // 读取输入流
    private static String read(Process process) {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
