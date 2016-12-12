package cn.canlnac.onlinecourse.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 缓存文件管理.
 */
@Singleton
public class FileManager {

    @Inject
    public FileManager() {
    }

    /**
     * 将缓存文件写入磁盘.
     *
     * @param file 将要被写入的缓存文件.
     */
    public void writeToFile(File file, String fileContent) {
        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(fileContent);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取缓存文件.
     *
     * @param file 将要读取的缓存文件.
     * @return 缓存文件内容.
     */
    public String readFileContent(File file) {
        StringBuilder fileContentBuilder = new StringBuilder();
        if (file.exists()) {
            String stringLine;
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((stringLine = bufferedReader.readLine()) != null) {
                    fileContentBuilder.append(stringLine + "\n");
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileContentBuilder.toString();
    }

    /**
     * 缓存文件是否存在.
     *
     * @param file 缓存文件.
     * @return true 缓存文件存在, false 缓存文件不存在.
     */
    public boolean exists(File file) {
        return file.exists();
    }

    /**
     * 清空目录下的文件
     *
     * @param directory 缓存文件目录.
     */
    public boolean clearDirectory(File directory) {
        boolean result = false;
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                result = file.delete();
            }
        }
        return result;
    }

    /**
     * 将值写进偏好文件中.
     *
     * @param context               android的上下文.
     * @param preferenceFileName    偏好文件名.
     * @param key                   键.
     * @param value                 值.
     */
    public void writeToPreferences(Context context, String preferenceFileName, String key,
                                   long value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 从偏好文件中读取值.
     *
     * @param context               android的上下文.
     * @param preferenceFileName    偏好文件名.
     * @param key                   键.
     * @return 值.
     */
    public long getFromPreferences(Context context, String preferenceFileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }
}
