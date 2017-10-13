package com.ajavac.client;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * ftp客户端
 */
@Component
public final class MyFtpClient {


    private static final Logger logger = LoggerFactory.getLogger(MyFtpClient.class);
    private static final String CONTROL_ENCODING = StandardCharsets.UTF_8.name();
    private static final int CONNECT_TIMEOUT = 1000; // 毫秒

    @Value("${ftp.host:localhost}")
    private String host;
    @Value("${ftp.port:2121}")
    private int ftpPort;
    @Value("${ftp.username:admin}")
    private String ftpUsername;
    @Value("${ftp.password:admin}")
    private String ftpPassword;


    /**
     * 上传文件
     *
     * @param remoteDir      上传目录
     * @param remoteFileName 上传文件名
     * @param localFile      本地文件
     * @return 上传结果 true - 上传成功 false - 上传失败
     */
    public boolean upload(String remoteDir, String remoteFileName, File localFile) {
        FTPClient ftp = null;
        try {
            ftp = initFtpClient(remoteDir);
            if (ftp == null) {
                logger.debug("ftp初始化失败");
                return false;
            }
            try (InputStream is = new FileInputStream(localFile)) {
                boolean storeRet = ftp.storeFile(remoteFileName, is);
                if (!storeRet) {
                    logger.debug("上传文件失败");
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            logger.error("FTP操作异常", e);
            return false;
        } finally {
            close(ftp);
        }
    }

    /**
     * 删除文件
     *
     * @param remoteDir      操作目录
     * @param remoteFileName 删除文件名
     * @return 删除结果<br> true - 删除成功<br>
     * false - 删除失败
     */
    public boolean delete(String remoteDir, String remoteFileName) {
        FTPClient ftp = null;
        try {
            ftp = initFtpClient(remoteDir);
            if (ftp == null) {
                logger.debug("ftp初始化失败");
                return false;
            }
            boolean deleteRet = ftp.deleteFile(remoteFileName);
            if (!deleteRet) {
                logger.debug("删除文件失败");
                return false;
            } else {
                logger.debug("删除文件成功");
            }

            return true;
        } catch (IOException e) {
            logger.error("FTP操作异常", e);
            return false;
        } finally {
            close(ftp);
        }
    }

    /**
     * 删除文件夹下所有的文件(非文件夹)
     *
     * @param fullDir 待删除目录完整ftp路径
     * @return 删除结果<br> true - 删除成功<br>
     * false - 删除失败
     */
    public boolean deleteDir(String fullDir) {
        FTPClient ftp = null;
        // fullDir = encode(fullDir);
        try {
            ftp = initFtpClient(fullDir);
            if (ftp == null) {
                logger.debug("ftp初始化失败");
                return false;
            }

            FTPFile[] ftpFiles = ftp.listFiles();
            if (ftpFiles != null && ftpFiles.length > 0) {
                for (FTPFile ftpFile : ftpFiles) {
                    if (!ftpFile.isDirectory()) {
                        boolean deleteRet = ftp.deleteFile(ftpFile.getName());
                        if (!deleteRet) {
                            logger.debug("删除文件失败");
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            logger.error("FTP操作异常", e);
            return false;
        } finally {
            close(ftp);
        }
    }

    /**
     * 移动ftp上的文件
     *
     * @param srcPath 原文件路径
     * @param desPath 模板文件路径
     * @return 操作结果
     */
    public boolean move(String srcPath, String desPath) {
        FTPClient ftp = null;
        try {
            ftp = initFtpClient("/");

            // 创建目标文件夹
            String remoteDir = desPath.substring(0, desPath.lastIndexOf("/"));
            MKDAndCWD(ftp, remoteDir);

            if (ftp == null) {
                logger.debug("ftp初始化失败");
                return false;
            }
            if (!ftp.rename(srcPath, desPath)) {
                logger.warn("移动文件失败");
                return false;
            }
            return true;
        } catch (IOException e) {
            logger.error("FTP操作异常", e);
            return false;
        } finally {
            close(ftp);
        }
    }

    /**
     * 下载文件
     *
     * @param remoteDir      远程操作目录
     * @param remoteFileName 远程下载文件名
     * @param downloadFile   下载文件
     * @return 下载结果<br> true - 下载成功<br>
     * false - 下载失败
     */
    public boolean download(String remoteDir, String remoteFileName, File downloadFile) {
        FTPClient ftp = null;
        try {
            ftp = initFtpClient(remoteDir);
            if (ftp == null) {
                logger.debug("ftp初始化失败");
                return false;
            }
            try (OutputStream os = new FileOutputStream(downloadFile)) {
                boolean storeRet = ftp.retrieveFile(remoteFileName, os);
                if (!storeRet) {
                    logger.debug("下载文件失败");
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            logger.error("FTP操作异常", e);
            return false;
        } finally {
            close(ftp);
        }
    }

    /**
     * 检查文件/文件夹下是否存在
     *
     * @param remoteDir      远程地址
     * @param remoteFileName 远程文件名称
     * @return true文件存在，false文件不存在
     */
    public boolean isFileExist(String remoteDir, String remoteFileName) {
        FTPClient ftp = null;
        try {
            ftp = initFtpClient(remoteDir);
            if (ftp == null) {
                logger.debug("ftp初始化失败");
                return false;
            }

            FTPFile[] files = null;
            files = ftp.listFiles(remoteDir + remoteFileName);
            if (null != files && files.length > 0) {
                return true;
            }

            return false;
        } catch (IOException e) {
            logger.error("FTP操作异常", e);
            return false;
        } finally {
            close(ftp);
        }
    }

    /**
     * 获取文件大小
     *
     * @param remoteDir      远程地址
     * @param remoteFileName 远程文件名称
     * @return 异常等情况返回0，其他情况返回正常
     */
    public long getFileSize(String remoteDir, String remoteFileName) {
        long fileSize = 0;
        FTPClient ftp = null;
        try {
            ftp = initFtpClient(remoteDir);
            if (ftp == null) {
                logger.debug("ftp初始化失败");
                return fileSize;
            }

            FTPFile[] files = null;
            files = ftp.listFiles(remoteDir + remoteFileName);
            if (null != files && files.length > 0) {
                fileSize = files[0].getSize();
            }

            return fileSize;
        } catch (IOException e) {
            logger.error("FTP操作异常", e);
            return fileSize;
        } finally {
            close(ftp);
        }
    }

    private FTPClient initFtpClient(String remoteDir) throws IOException {
        FTPClient ftp = new FTPClient();
        // 设置连接超时时间
        ftp.setConnectTimeout(CONNECT_TIMEOUT);
        // 设置传输文件名编码方式
        ftp.setControlEncoding(CONTROL_ENCODING);
        ftp.connect(host, ftpPort);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            logger.debug("无法连接FTP");
            return null;
        }
        boolean loginRet = ftp.login(ftpUsername, ftpPassword);
        if (!loginRet) {
            logger.debug("FTP登录失败");
            return null;
        }
        // 进入被动模式
        ftp.enterLocalPassiveMode();
        boolean changeDirResult = MKDAndCWD(ftp, remoteDir);
        if (!changeDirResult) {
            logger.debug("创建/切换文件夹失败");
            return null;
        }
        // 传输二进制文件
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        return ftp;
    }

    private boolean MKDAndCWD(FTPClient ftp, String remoteDir) throws IOException {
        // 切分出所有子文件夹,按顺序
        String[] dirs = remoteDir.split("/");
        // 遍历文件夹
        for (String subDir : dirs) {
            // 文件夹字符串非空
            if (!subDir.isEmpty()) {
                // 切换工作目录
                if (!ftp.changeWorkingDirectory(subDir)) {
                    // 若目录不存在则先创建
                    if (!ftp.makeDirectory(subDir)) {
                        return false;
                    }
                    // 切换工作目录
                    if (!ftp.changeWorkingDirectory(subDir)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void close(FTPClient ftp) {
        if (ftp != null) {
            try {
                ftp.logout();
            } catch (IOException e) {
                logger.error("FTP连接关闭异常", e);
            } finally {
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
                    } catch (IOException e) {
                        logger.error("FTP连接关闭异常", e);
                    }
                }
            }
        }
    }

}
