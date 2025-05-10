package com.jimuv.common.util;

import com.jimuv.common.domain.video.VideoCreate;
import com.jimuv.common.domain.video.build.Effect;
import com.jimuv.common.domain.video.build.VideoBuild;
import com.jimuv.common.domain.video.init.VideoInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class FFmpegUtils {

    public static Boolean videoCreate(VideoCreate videoCreate, String folder) {
        handleVideoInit(videoCreate);

        VideoInit videoInit = videoCreate.getVideoInit();
        String path = folder + File.separatorChar + videoInit.getId() + File.separatorChar;

        List<VideoBuild> videoBuildList = videoCreate.getVideoBuild();
        List<VideoBuild> audioVideoBuildList;
        if (CollectionUtils.isEmpty(videoBuildList)) {
            videoBuildList = new ArrayList<>();
            audioVideoBuildList = new ArrayList<>();
        } else {
            audioVideoBuildList = videoBuildList.stream().filter(videoBuild -> FFmpegStrUtils.AUDIO.equals(videoBuild.getType())).collect(Collectors.toList());
        }

        return handleTtf(videoInit, path) && handleVideoBuildBackground(videoInit, videoBuildList, path) && handleVideoBuildDefaultAudio(videoInit, audioVideoBuildList, path) && handleVideoBuildAudio(videoInit, audioVideoBuildList, path) && handleVideoBuild(videoInit, videoBuildList, path) && handleVideoThumbnail(videoCreate, path) && handleVideoFile(videoInit, path);
    }

    private static boolean handleTtf(VideoInit videoInit, String path) {
        boolean result;
        try {
            FileUtils.createDirectory(path);
            URL resourceUrl = ResourceUtils.getURL(FFmpegStrUtils.TTF_PATH + FFmpegStrUtils.TTF_NAME);
            File resourceFile = new File(resourceUrl.toURI());
            File targetFile = new File(path, FFmpegStrUtils.TTF_NAME);
            FileCopyUtils.copy(resourceFile, targetFile);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        log.info("video create {} ------>>>>>> handleTtf, result:{}.", videoInit.getId(), result);
        return result;
    }

    private static boolean handleVideoBuild(VideoInit videoInit, List<VideoBuild> videoBuildList, String path) {
        boolean result;
        List<String> commandList = new ArrayList<>();
        commandList.add(FFmpegStrUtils.FFMPEG);
        commandList.add(FFmpegStrUtils.I);
        commandList.add(path + videoInit.getName() + FFmpegStrUtils.MP3);

        StringBuilder filterComplex = new StringBuilder();
        FFmpegIdUtils idUtils = new FFmpegIdUtils();

        String resultOutputId = FFmpegStrUtils.EMPTY;
        for (VideoBuild videoBuild : videoBuildList) {
            handleVideoBuild(videoBuild, videoInit);
            Effect effect = videoBuild.getEffect();

            if (FFmpegStrUtils.ELEMENT.equals(videoBuild.getType())) {
                if (StringUtils.isEmpty(videoBuild.getElementUrl())) {
                    continue;
                }
                commandList.add(FFmpegStrUtils.STREAM_LOOP);
                commandList.add(FFmpegStrUtils.ONE);
                commandList.add(FFmpegStrUtils.I);
                commandList.add(videoBuild.getElementUrl());

                if (!StringUtils.isEmpty(resultOutputId)) {
                    filterComplex.append(FFmpegStrUtils.SEMICOLON);
                }
                filterComplex.append(idUtils.inputV());
                filterComplex.append(effect.getTransparent().build());

                if (Objects.nonNull(effect.getCrop())) {
                    filterComplex.append(effect.getCrop().build());
                }
                if (Objects.nonNull(effect.getScale())) {
                    filterComplex.append(effect.getScale().build());
                }
                if (Objects.nonNull(effect.getFlip())) {
                    filterComplex.append(effect.getFlip().build());
                }
                if (Objects.nonNull(effect.getGblur())) {
                    filterComplex.append(effect.getGblur().build());
                }
                if (Objects.nonNull(effect.getRadius())) {
                    filterComplex.append(effect.getRadius().build());
                }
                if (Objects.nonNull(effect.getRotate())) {
                    filterComplex.append(effect.getRotate().build());
                }
                if (Objects.nonNull(effect.getFade())) {
                    effect.getTime().fadeTime(effect.getFade());
                    filterComplex.append(effect.getFade().build(effect.getTime()));
                }

                String outputId = idUtils.outputV();
                filterComplex.append(outputId);
                if (StringUtils.isEmpty(resultOutputId)) {
                    resultOutputId = outputId;
                } else {
                    filterComplex.append(FFmpegStrUtils.SEMICOLON);
                    filterComplex.append(resultOutputId);
                    filterComplex.append(outputId);

                    if (Objects.nonNull(effect.getMove())) {
                        filterComplex.append(effect.getOverlay().build(effect.getTime(), effect.getMove()));
                    } else {
                        filterComplex.append(effect.getOverlay().build());
                    }
                    filterComplex.append(effect.getTime().buildTime(videoInit.getDuration()));

                    String overlayOutputId = idUtils.outputV();
                    filterComplex.append(overlayOutputId);
                    resultOutputId = overlayOutputId;
                }

            }
            if (FFmpegStrUtils.TEXT.equals(videoBuild.getType())) {
                if (StringUtils.isEmpty(videoBuild.getTextContent())) {
                    continue;
                }
                filterComplex.append(FFmpegStrUtils.SEMICOLON);
                filterComplex.append(resultOutputId);
                if (StringUtils.isEmpty(effect.getText().getFontFile())) {
                    effect.getText().setFontFile(path + File.separatorChar + FFmpegStrUtils.TTF_NAME);
                }
                filterComplex.append(effect.getText().build(effect.getOverlay().setIsText(true).buildXY(), videoBuild.getTextContent()));
                filterComplex.append(effect.getTime().buildTime(videoInit.getDuration()));

                String overlayOutputId = idUtils.outputV();
                filterComplex.append(overlayOutputId);
                resultOutputId = overlayOutputId;
            }
        }

        idUtils.clearAllSequences();
        commandList.add(FFmpegStrUtils.FILTER_COMPLEX);
        commandList.add(filterComplex.toString());

        commandList.add(FFmpegStrUtils.MAP);
        commandList.add(resultOutputId);
        commandList.add(FFmpegStrUtils.MAP);
        commandList.add(FFmpegStrUtils.MAP_VALUE);
        commandList.add(FFmpegStrUtils.CV);
        commandList.add(FFmpegStrUtils.LIBX264);
        commandList.add(FFmpegStrUtils.R);
        commandList.add(String.valueOf(videoInit.getFrameRate()));
        commandList.add(FFmpegStrUtils.CRF);
        commandList.add(String.valueOf(videoInit.getCrf()));
        commandList.add(FFmpegStrUtils.CA);
        commandList.add(FFmpegStrUtils.AAC);
        commandList.add(FFmpegStrUtils.STRICT);
        commandList.add(FFmpegStrUtils.EXPERIMENTAL);
        commandList.add(FFmpegStrUtils.T);
        commandList.add(String.valueOf(videoInit.getDuration()));
        commandList.add(FFmpegStrUtils.MOVFLAGS);
        commandList.add(FFmpegStrUtils.FASTSTART);
        commandList.add(FFmpegStrUtils.PIX_FMT);
        commandList.add(FFmpegStrUtils.YUV420P);
        commandList.add(path + videoInit.getName() + FFmpegStrUtils.MP4);
        commandList.add(FFmpegStrUtils.Y);

        result = CommandUtils.handleExec(commandList.toArray(new String[0]), path, videoInit);
        log.info("video create {} ------>>>>>> handleVideo, result:{}.", videoInit.getId(), result);
        return result;
    }

    private static boolean handleVideoBuildAudio(VideoInit videoInit, List<VideoBuild> audioVideoBuildList, String path) {
        boolean result;
        List<String> commandList = new ArrayList<>();
        commandList.add(FFmpegStrUtils.FFMPEG);

        StringBuilder filterComplex = new StringBuilder();
        List<String> outputIdList = new ArrayList<>();
        FFmpegIdUtils idUtils = new FFmpegIdUtils();
        for (VideoBuild videoBuild : audioVideoBuildList) {
            if (StringUtils.isEmpty(videoBuild.getAudioUrl())) {
                continue;
            }
            if (CollectionUtils.isEmpty(outputIdList)) {
                commandList.add(FFmpegStrUtils.I);
            } else {
                commandList.add(FFmpegStrUtils.STREAM_LOOP);
                commandList.add(FFmpegStrUtils.ONE);
                commandList.add(FFmpegStrUtils.I);
                filterComplex.append(FFmpegStrUtils.SEMICOLON);
            }
            commandList.add(videoBuild.getAudioUrl());
            filterComplex.append(idUtils.inputA());
            if (CollectionUtils.isEmpty(outputIdList)) {
                filterComplex.append(videoBuild.getEffect().getAudio().buildVolume());
            } else {
                filterComplex.append(videoBuild.getEffect().getAudio().build(videoBuild.getEffect().getTime()));
            }
            String outputId = idUtils.outputV();
            filterComplex.append(outputId);
            outputIdList.add(outputId);
        }
        if (!CollectionUtils.isEmpty(outputIdList)) {
            filterComplex.append(FFmpegStrUtils.SEMICOLON);
            for (String outputId : outputIdList) {
                filterComplex.append(outputId);
            }
        }
        filterComplex.append(FFmpegStrUtils.AMIX_INPUT);
        filterComplex.append(outputIdList.size());
        filterComplex.append(FFmpegStrUtils.COLON);
        filterComplex.append(FFmpegStrUtils.DURATION_FIRST);

        commandList.add(FFmpegStrUtils.FILTER_COMPLEX);
        commandList.add(filterComplex.toString());
        commandList.add(path + videoInit.getName() + FFmpegStrUtils.MP3);
        commandList.add(FFmpegStrUtils.Y);
        result = CommandUtils.handleExec(commandList.toArray(new String[0]), path, videoInit);
        log.info("video create {} ------>>>>>> handleAudio, result:{}.", videoInit.getId(), result);
        return result;
    }

    private static Boolean handleVideoBuildBackground(VideoInit videoInit, List<VideoBuild> videoBuildList, String path) {
        boolean result;
        VideoBuild videoBuild = new VideoBuild();
        videoBuild.setType(FFmpegStrUtils.ELEMENT);

        if (StringUtils.isEmpty(videoInit.getBackgroundImage())) {
            String backgroundColor = videoInit.getBackgroundColor();
            String backgroundColorImage = path + DateUtils.getTimeStamp() + FFmpegStrUtils.PNG;
            videoBuild.setElementUrl(backgroundColorImage);

            List<String> commandList = new ArrayList<>();
            commandList.add(FFmpegStrUtils.FFMPEG);
            commandList.add(FFmpegStrUtils.F);
            commandList.add(FFmpegStrUtils.LAVFI);
            commandList.add(FFmpegStrUtils.I);
            commandList.add(FFmpegStrUtils.COLOR + FFmpegStrUtils.COLORC + backgroundColor + FFmpegStrUtils.COLON + FFmpegStrUtils.COLORS + videoInit.getWidth() + FFmpegStrUtils.MULTIPLY + videoInit.getHeight());
            commandList.add(FFmpegStrUtils.VFRAMES_V);
            commandList.add(String.valueOf(BigDecimal.ONE));
            commandList.add(backgroundColorImage);
            commandList.add(FFmpegStrUtils.Y);
            result = CommandUtils.handleExec(commandList.toArray(new String[0]), path, videoInit);
        } else {
            videoBuild.setElementUrl(videoInit.getBackgroundImage());
            result = true;
        }

        log.info("video create {} ------>>>>>> handleBackground, result:{}.", videoInit.getId(), result);
        handleVideoBuild(videoBuild, videoInit);
        videoBuildList.add(0, videoBuild);
        return result;
    }

    private static Boolean handleVideoBuildDefaultAudio(VideoInit videoInit, List<VideoBuild> audioVideoBuildList, String path) {
        boolean result;
        VideoBuild videoBuild = new VideoBuild();
        videoBuild.setType(FFmpegStrUtils.AUDIO);
        String audio = path + DateUtils.getTimeStamp() + FFmpegStrUtils.MP3;
        videoBuild.setAudioUrl(audio);

        List<String> commandList = new ArrayList<>();
        commandList.add(FFmpegStrUtils.FFMPEG);
        commandList.add(FFmpegStrUtils.F);
        commandList.add(FFmpegStrUtils.LAVFI);
        commandList.add(FFmpegStrUtils.I);
        commandList.add(FFmpegStrUtils.ANULLSRC);
        commandList.add(FFmpegStrUtils.T);
        commandList.add(String.valueOf(videoInit.getDuration()));
        commandList.add(FFmpegStrUtils.ACODEC);
        commandList.add(FFmpegStrUtils.LIBMP3LAME);
        commandList.add(FFmpegStrUtils.AB);
        commandList.add(FFmpegStrUtils.AB_VALUE);
        commandList.add(audio);
        commandList.add(FFmpegStrUtils.Y);
        result = CommandUtils.handleExec(commandList.toArray(new String[0]), path, videoInit);
        log.info("video create {} ------>>>>>> handleDefaultAudio, result:{}.", videoInit.getId(), result);

        handleVideoBuild(videoBuild, videoInit);
        audioVideoBuildList.add(0, videoBuild);
        return result;
    }

    public static Boolean handleVideoThumbnail(VideoCreate videoCreate, String path) {
        boolean result;
        handleVideoInit(videoCreate);

        VideoInit videoInit = videoCreate.getVideoInit();
        String videoUrl = path + videoInit.getName() + FFmpegStrUtils.MP4;
        String videoThumbnailUrl = path + videoInit.getName() + FFmpegStrUtils.JPG;
        BigDecimal thumbnailTime = videoInit.getThumbnailTime();

        List<String> commandList = new ArrayList<>();
        commandList.add(FFmpegStrUtils.FFMPEG);
        if (Objects.nonNull(thumbnailTime) && thumbnailTime.compareTo(BigDecimal.ZERO) > 0) {
            commandList.add(FFmpegStrUtils.SS);
            commandList.add(String.valueOf(thumbnailTime));
        }
        commandList.add(FFmpegStrUtils.I);
        commandList.add(videoUrl);
        commandList.add(FFmpegStrUtils.VFRAMES);
        commandList.add(String.valueOf(BigDecimal.ONE));
        commandList.add(videoThumbnailUrl);
        commandList.add(FFmpegStrUtils.Y);
        result = CommandUtils.handleExec(commandList.toArray(new String[0]), path, videoInit);
        log.info("video create {} ------>>>>>> handleThumbnail, result:{}.", videoInit.getId(), result);
        return result;
    }

    private static boolean handleVideoFile(VideoInit videoInit, String path) {
        boolean result = true;
        try {
            if (Objects.isNull(videoInit.getDebug()) || !videoInit.getDebug()) {
                Path folderPath = Paths.get(path);
                Files.walkFileTree(folderPath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (!(file.getFileName().endsWith(videoInit.getName() + FFmpegStrUtils.MP4) || file.getFileName().endsWith(videoInit.getName() + FFmpegStrUtils.MP3) || file.getFileName().endsWith(videoInit.getName() + FFmpegStrUtils.JPG))) {
                            Files.delete(file);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        if (!dir.equals(folderPath)) {
                            Files.delete(dir);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

        } catch (Exception e) {
            result = false;
        }
        log.info("video create {} ------>>>>>> handleVideoFile, result:{}.", videoInit.getId(), result);
        return result;
    }

    public static void handleVideoInit(VideoCreate videoCreate) {
        if (Objects.isNull(videoCreate)) {
            videoCreate = new VideoCreate();
        }
        VideoInit videoInit = videoCreate.getVideoInit();
        if (Objects.isNull(videoInit)) {
            videoInit = new VideoInit();
        }
        videoInit.initParam();
        videoCreate.setVideoInit(videoInit);

        List<VideoBuild> videoBuildList = videoCreate.getVideoBuild();
        if (!CollectionUtils.isEmpty(videoBuildList)) {
            for (VideoBuild videoBuild : videoBuildList) {
                handleVideoBuild(videoBuild, videoInit);
            }
        }
    }

    public static void handleVideoBuild(VideoBuild videoBuild, VideoInit videoInit) {
        if (Objects.isNull(videoBuild)) {
            videoBuild = new VideoBuild();
        }
        if (StringUtils.isEmpty(videoBuild.getType())) {
            videoBuild.setType(FFmpegStrUtils.TEXT);
        }
        Effect effect = videoBuild.getEffect();
        if (Objects.isNull(effect)) {
            effect = new Effect();
        }
        effect.initParam(videoInit);
        videoBuild.setEffect(effect);
    }
}
