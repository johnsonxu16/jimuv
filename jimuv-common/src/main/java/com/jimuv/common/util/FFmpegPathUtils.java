package com.jimuv.common.util;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.ffmpeg.ffmpeg;

import java.io.File;

/**
 * FFmpeg路径工具类，用于获取FFmpeg可执行文件的路径
 */
public class FFmpegPathUtils {
    
    /**
     * 获取当前操作系统对应的FFmpeg可执行文件路径
     * @return FFmpeg可执行文件路径
     */
    public static String getFFmpegExecutablePath() {
        try {
            // 设置优先从系统路径加载FFmpeg
            System.setProperty("org.bytedeco.javacpp.pathsFirst", "true");
            
            // 加载FFmpeg类，确保相关库被加载
            String javaCvPath = Loader.load(ffmpeg.class);
            
            // 获取操作系统类型
            String osName = System.getProperty("os.name").toLowerCase();
            String ffmpegBinary = osName.contains("win") ? "ffmpeg.exe" : "ffmpeg";
            
            // 尝试从环境变量中获取FFmpeg路径
            String ffmpegPath = System.getenv("FFMPEG_PATH");
            if (ffmpegPath != null && new File(ffmpegPath).exists()) {
                return ffmpegPath;
            }
            
            // 尝试从JavaCV加载的路径中查找FFmpeg可执行文件
            if (javaCvPath != null && !javaCvPath.isEmpty()) {
                File dir = new File(javaCvPath).getParentFile();
                if (dir != null && dir.exists()) {
                    File ffmpegFile = new File(dir, ffmpegBinary);
                    if (ffmpegFile.exists()) {
                        return ffmpegFile.getAbsolutePath();
                    }
                }
            }
            
            // 如果以上方法都无法获取FFmpeg路径，则返回默认的命令名，依赖系统PATH
            return ffmpegBinary;
        } catch (Exception e) {
            // 如果出现异常，返回默认的ffmpeg命令
            System.err.println("获取FFmpeg路径时出现异常: " + e.getMessage());
            return "ffmpeg";
        }
    }
}