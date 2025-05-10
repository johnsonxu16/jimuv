package com.jimuv.api.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jimuv.common.domain.video.VideoCreate;
import com.jimuv.common.domain.video.VideoCreateResult;
import com.jimuv.common.domain.video.init.VideoInit;
import com.jimuv.common.util.FFmpegStrUtils;
import com.jimuv.common.util.FFmpegUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class VideoService {

    @Value("${video.create.folder}")
    private String folder;

    public VideoCreateResult create(VideoCreate videoCreate) {
        VideoCreateResult videoCreateResult = new VideoCreateResult();

        FFmpegUtils.handleVideoInit(videoCreate);

        VideoInit videoInit = videoCreate.getVideoInit();
        String id = videoInit.getId();
        log.info("video create {} ------>>>>>>> start.", id);

        long startTime = System.currentTimeMillis();
        boolean result;
        long timeout = 60L;

        ExecutorService executorService = new ThreadPoolExecutor(
                1, 1,
                timeout, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(100),
                new ThreadFactoryBuilder().setNameFormat("video-create-" + id + "-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        Future<Boolean> future = executorService.submit(() -> {
            return FFmpegUtils.videoCreate(videoCreate, folder);
        });
        try {
            result = future.get(timeout, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            result = false;
            log.error("video create {} ------>>>>>>> InterruptedException.", id);
            future.cancel(true);
        } catch (ExecutionException e) {
            result = false;
            log.error("video create {} ------>>>>>>> ExecutionException.", id);
            future.cancel(true);
        } catch (TimeoutException e) {
            result = false;
            log.error("video create {} ------>>>>>>> TimeoutException.", id);
            future.cancel(true);
        } catch (Exception e) {
            result = false;
            log.error("video create {} ------>>>>>>> Exception.", id);
        } finally {
            executorService.shutdown();
        }

        long endTime = System.currentTimeMillis();
        double time = (endTime - startTime) / 1000.0;
        log.info("video create {} ------>>>>>> end. result:{}, time:{}.", id, result, time);

        String path = folder + File.separatorChar + id + File.separatorChar;
        videoCreateResult.setId(id);
        videoCreateResult.setResult(result);
        videoCreateResult.setTime(time);
        videoCreateResult.setVideo(path + videoInit.getName() + FFmpegStrUtils.MP4);
        videoCreateResult.setVideoThumbnail(path + videoInit.getName() + FFmpegStrUtils.JPG);
        videoCreateResult.setVideoAudio(path + videoInit.getName() + FFmpegStrUtils.MP3);
        return videoCreateResult;
    }
}

