package com.game.xianxue.aoh.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.game.xianxue.aoh.constant.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 10/11/16.
 */
public class FileUtils {
    private static final String TAG = "FileUtils";
    /**
     * 表示手机外置存储空间的 StatFs
     */
    private static StatFs sExtStorageStatFs;
    private static String sExternalStorageDirPath;

    /**
     * 确认目录是否存在，如果不存在，则新建目录
     *
     * @param path
     */
    public static void mkdirsIfNotExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 把content数据存进指定文件
     *
     * @param content
     * @param fileDir
     * @param fileName
     * @param isAppend 是否追加内容到最后一行
     */
    public static boolean saveContentToFile(String content, String fileDir, String fileName, boolean isAppend) {
        mkdirsIfNotExists(fileDir);
        String path = fileDir + File.separator + fileName;
        File file = new File(path);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, isAppend);
            if (isAppend) {
                //如果是追加模式，则在前面加一个换行符号
                fos.write((System.getProperty("line.separator") + content).getBytes());
            } else {
                fos.write(content.getBytes());
            }

        } catch (IOException e) {
            Log.d(TAG, "saveContentToFile failed!");
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {

                }
            }
        }
        Log.d(TAG, "save file success " + path);
        return true;
    }

    //===================================分割线 删除操作 开始==========================================

    /**
     * 删除一个文件
     *
     * @param file
     * @return
     */
    public static boolean delFile(File file) {
        if (file.isDirectory()) {
            return false;
        }
        if (file.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除一个目录(可以是非空目录)
     *
     * @param dir
     */
    public static boolean delDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                delDir(file);// 递归
            }
        }
        dir.delete();
        return true;
    }
    //===================================分割线 删除操作 结束==========================================


    //===================================分割线 复制操作 开始==========================================

    /**
     * 复制文件目录
     *
     * @param srcDir  要复制的源目录 eg:/mnt/sdcard/DB
     * @param destDir 复制到的目标目录 eg:/mnt/sdcard/db/
     * @return
     */
    public static boolean copyDirTo(String srcDir, String destDir) {
        File sourceDir = new File(srcDir);
        //判断文件目录是否存在
        if (!sourceDir.exists()) {
            Log.d(TAG, "copyDirTo(): sourceDir is not exist !");
            return false;
        }
        //判断是否是目录
        if (sourceDir.isDirectory()) {
            File[] fileList = sourceDir.listFiles();
            File targetDir = new File(destDir);
            //创建目标目录
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            //遍历要复制该目录下的全部文件
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {//如果如果是子目录进行递归
                    copyDirTo(fileList[i].getPath(), destDir + File.separator + fileList[i].getName());
                } else {//如果是文件则进行文件拷贝
                    copyFileToDir(fileList[i].getPath(), destDir);
                }
            }
            return true;
        } else {
            copyFileToDir(srcDir, destDir);
            return true;
        }
    }

    /**
     * 把文件拷贝到某一目录下
     *
     * @param srcFile
     * @param destDir
     * @return
     */
    public static boolean copyFileToDir(String srcFile, String destDir) {
        File fileDir = new File(destDir);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        File fileSrc = new File(srcFile);
        if (!fileSrc.exists()) {
            Log.d(TAG, "copyFileToDir(): srcFile is not exist !");
            return false;
        }
        String destFile = destDir + File.separator + new File(srcFile).getName();
        try {
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer)) > 0) {
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param srcFile
     * @param destFile
     * @return
     */
    public static boolean copyFileToFile(String srcFile, String destFile) {
        File fileDest = new File(destFile);
        if (fileDest.exists()) {
            fileDest.delete();
        }

        File fileSrc = new File(srcFile);
        if (!fileSrc.exists()) {
            Log.d(TAG, "copyFileToFile(): srcFile is not exist !");
            return false;
        }
        try {
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer)) > 0) {
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    //===================================分割线 复制操作 结束==========================================


    //===================================分割线 移动操作 开始==========================================

    /**
     * 移动文件目录
     *
     * @param srcDir
     * @param destDir
     * @return
     */
    public static boolean moveDirTo(String srcDir, String destDir) {
        File sourceDir = new File(srcDir);
        //判断文件目录是否存在
        if (!sourceDir.exists()) {
            Log.d(TAG, "copyDirTo(): sourceDir is not exist !");
            return false;
        }
        //判断是否是目录
        if (sourceDir.isDirectory()) {
            File[] fileList = sourceDir.listFiles();
            File targetDir = new File(destDir);
            //创建目标目录
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            //遍历要复制该目录下的全部文件
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {//如果如果是子目录进行递归
                    copyDirTo(fileList[i].getPath(), destDir + File.separator + fileList[i].getName());
                } else {//如果是文件则进行文件拷贝
                    moveFileToDir(fileList[i].getPath(), destDir);
                }
            }
            if (sourceDir.exists()) {
                delDir(sourceDir);
            }
            return true;
        } else {
            moveFileToDir(srcDir, destDir);
            return true;
        }
    }

    /**
     * 把文件移动到某一目录下
     *
     * @param srcFile
     * @param destDir
     * @return
     */
    public static boolean moveFileToDir(String srcFile, String destDir) {
        File fileDir = new File(destDir);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        File fileSrc = new File(srcFile);
        if (!fileSrc.exists()) {
            Log.d(TAG, "moveFileToDir(): srcFile is not exist !");
            return false;
        }

        String destFile = destDir + File.separator + new File(srcFile).getName();
        try {
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer)) > 0) {
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (fileSrc.exists()) {
                delFile(fileSrc);
            }
        }
    }
    //===================================分割线 移动操作 结束==========================================


    /**
     * 在指定目录下，查找关键字
     *
     * @param dirString
     * @param Keyword
     * @return
     * @throws IOException
     */
    public static String SearchKeywordInDir(String dirString, String Keyword)
            throws IOException {
        String result = null;
        if (dirString == null) {
            return result;
        }

        File srcDir = new File(dirString + File.separator);
        if (!srcDir.isDirectory()) {
            return result;
        }

        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            if (srcDirFiles[i].isFile()) {
                if (srcDirFiles[i].getName().contains(Keyword)) {
                    result = dirString + File.separator + srcDirFiles[i].getName();
                    Log.d(TAG, "db file => " + result);
                    return result;
                }
            } else if (srcDirFiles[i].isDirectory()) {
                String oneDirString = dirString + File.separator + srcDirFiles[i].getName();
                SearchKeywordInDir(oneDirString, Keyword);
            }
        }
        return result;
    }

    /**
     * 重命名
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean reNameTo(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        if (oldFile.exists()) {
            File newFile = new File(newPath);
            if (newFile.exists()) {
                delFile(newFile);
            }
            oldFile.renameTo(newFile);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得文件的大小
     *
     * @param filePath
     * @return
     */
    public static String getFileSize(String filePath) {
        if (filePath == null) {
            return "";
        }
        File file = new File(filePath);
        long temp;
        if (file != null && file.exists()) {
            long fileSize = file.length();
            if (fileSize != 0) {
                temp = fileSize / 1024;
                if (temp >= 1024) {
                    temp = temp / 1024;
                    if (temp >= 1024) {
                        temp = temp / 1024;
                        return temp + "GB";
                    } else {
                        return temp + "MB";
                    }
                } else {
                    return temp + " KB";
                }
            }
        }
        return "";
    }

    /**
     * 获取文件的大小
     *
     * @param filePath
     * @return
     */
    public static double getFileSizeDouble(String filePath) {
        if (filePath == null) {
            return 0;
        }
        double fileSize = 0;
        File file = new File(filePath);
        if (file != null && file.exists()) {
            fileSize = file.length();
        }
        return fileSize;
    }

    /**
     * 获取手机剩余内存的大小
     *
     * @return
     */
    public static double getAvaiMemosize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        double blockSize = stat.getBlockSize();
        double availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取总的外部存储空间的容量大小. 单位：Byte
     *
     * @return
     */
    public static long getTotalExtStorageSize() {
        initAndStat();
        long blockSize = sExtStorageStatFs.getBlockSizeLong();
        long totalBlocks = sExtStorageStatFs.getBlockCountLong();
        return blockSize * totalBlocks;
    }

    /**
     * 获取未使用的外部存储空间的容量大小. 单位：Byte
     *
     * @return
     */
    public static long getAvailExtStorageSize() {
        initAndStat();
        long blockSize = sExtStorageStatFs.getBlockSizeLong();
        long availableBlocks = sExtStorageStatFs.getAvailableBlocksLong();
        return blockSize * availableBlocks;
    }

    /**
     * 获取已使用的外部存储空间的容量大小. 单位：Byte
     *
     * @return
     */
    public static long getUsedExtStorageSize() {
        initAndStat();
        long blockSize = sExtStorageStatFs.getBlockSizeLong();
        long availableBlocks = sExtStorageStatFs.getAvailableBlocksLong();
        long totalBlocks = sExtStorageStatFs.getBlockCountLong();
        return blockSize * (totalBlocks - availableBlocks);
    }

    /**
     * 获取外部存储空间的使用率. 其数值介于 0.0f～1.0f之间
     *
     * @return
     */
    public static float getExtStorageUsedRate() {
        initAndStat();
        long availableBlocks = sExtStorageStatFs.getAvailableBlocksLong();
        long totalBlocks = sExtStorageStatFs.getBlockCountLong();
        return 1.0f * (totalBlocks - availableBlocks) / totalBlocks;
    }

    /**
     * 格式化文件的大小
     *
     * @param filesize
     * @return
     */
    public static String formatFileSize(long filesize) {
        if (filesize > (1024 * 1024 * 1024)) {
            return filesize / 1024 / 1024 / 1024 + " GB";
        } else if (filesize > (1024 * 1024)) {
            return filesize / 1024 / 1024 + " MB";
        } else if (filesize > 1024) {
            return filesize / 1024 + " KB";
        } else {
            return filesize + " B";
        }
    }

    private static void initAndStat() {
        init();
        statOrRestat();
    }

    private static void init() {
        if (sExternalStorageDirPath == null && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File externalStorageDir = Environment.getExternalStorageDirectory();
            sExternalStorageDirPath = externalStorageDir.getPath();
        }
    }

    private static void statOrRestat() {
        if (sExtStorageStatFs == null) {
            sExtStorageStatFs = new StatFs(sExternalStorageDirPath);
        }
        // 每次都必须要进行restat, 否则系统会重用先前已计算出来的各种blocks的数值, 而不会重新计算
        else {
            sExtStorageStatFs.restat(sExternalStorageDirPath);
        }
    }

    public static void createMyDir() {
        String path = getMyPath();
        mkdirsIfNotExists(path);
    }

    public static String getMyPath() {
        init();
        String path = sExternalStorageDirPath + File.separator + Constant.GAME_NAME;
        return path;
    }
}