package com.jimuv.common.util;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.jimuv.common.domain.video.init.VideoInit;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
public class CommandUtils {

    public static boolean handleExec(String[] commands, String folder, VideoInit videoInit) {
        boolean result = false;
        String id = videoInit.getId();
        Boolean isDebug = videoInit.getDebug();
        if (CollectionUtils.isEmpty((Object) commands)) {
            log.error("video create {} ------>>>>>> commands is null.", id);
            return result;
        }
        try {
            if (Objects.nonNull(isDebug) && isDebug) {
                StringBuilder commandsStr = new StringBuilder();
                for (String c : commands) {
                    commandsStr.append(c).append(FFmpegStrUtils.SPACE);
                }
                log.info("video create {} ------>>>>>> {}", id, commandsStr);
            }

            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            processBuilder.redirectErrorStream(true);

            String logFile = DateUtils.getTimeStamp() + FFmpegStrUtils.LOG;
            if (folder != null && !folder.isEmpty()) {
                FileUtils.createDirectory(folder + File.separator);
                logFile = folder + File.separator + logFile;
            }
            processBuilder.redirectOutput(new File(logFile));

            Process handleExec = processBuilder.start();

            readStream(handleExec.getInputStream(), false, id);
            readStream(handleExec.getErrorStream(), true, id);

            int exitCode = handleExec.waitFor();

            if (exitCode == 0) {
                result = true;
            } else {
                log.error("video create {} ------>>>>>> commands handleExec error.", id);
            }
        } catch (Exception e) {
            log.error("video create {} ------>>>>>> commands handleExec exception.", id);
        }
        return result;
    }

    private static void readStream(InputStream in, boolean error, String id) {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                if (error) {
                    log.error(line);
                } else {
                    log.info(line);
                }
            }
        } catch (IOException e) {
            log.error("video create {} ------>>>>>> commands handleExec exception.", id);
        }
    }

    public static void main(String[] args) {
        handleExec(new String[]{"ffmpeg", "-i"}, "/log/", null);
    }

}
