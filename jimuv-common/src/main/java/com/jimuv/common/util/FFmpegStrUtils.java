package com.jimuv.common.util;

public class FFmpegStrUtils {

    public static final String TTF_PATH = "classpath:static/font/";
    public static final String TTF_NAME = "HarmonyOS_Sans_SC_Regular.ttf";

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String EQUAL = "=";
    public static final String SINGLE = "'";
    public static final String MULTIPLY = "x";
    public static final String SEMICOLON = ";";

    public static final String ADD = "+";
    public static final String REDUCE = "-";

    public static final String MP3 = ".mp3";
    public static final String MP4 = ".mp4";
    public static final String JPG = ".jpg";
    public static final String PNG = ".png";
    public static final String LOG = ".log";


    public static final String TEXT = "text";
    public static final String AUDIO = "audio";
    public static final String ELEMENT = "element";

    /**
     * 通过FFmpegPathUtils获取当前操作系统对应的FFmpeg可执行文件路径
     */
    public static final String FFMPEG = FFmpegPathUtils.getFFmpegExecutablePath();
    public static final String I = "-i";
    public static final String Y = "-y";
    public static final String T = "-t";
    public static final String F = "-f";
    public static final String LAVFI = "lavfi";

    public static final String STREAM_LOOP = "-stream_loop";
    public static final String ONE = "-1";
    public static final String FILTER_COMPLEX = "-filter_complex";
    public static final String MAP = "-map";
    public static final String MAP_VALUE = "0:a:0";
    public static final String CV = "-c:v";
    public static final String LIBX264 = "libx264";
    public static final String LIBX265 = "libx265";
    public static final String LIBOPENH264 = "libopenh264";
    public static final String R = "-r";
    public static final String CRF = "-crf";
    public static final String CA = "-c:a";
    public static final String AAC = "aac";
    public static final String STRICT = "-strict";
    public static final String EXPERIMENTAL = "experimental";
    public static final String MOVFLAGS = "-movflags";
    public static final String FASTSTART = "+faststart";
    public static final String PIX_FMT = "-pix_fmt";
    public static final String YUV420P = "yuv420p";

    public static final String COLOR = "color=";
    public static final String COLORC = "c=";
    public static final String COLORS = "s=";
    public static final String VFRAMES_V = "-vframes:v";

    public static final String ANULLSRC = "anullsrc=r=44100:cl=stereo";
    public static final String ACODEC = "-acodec";
    public static final String LIBMP3LAME = "libmp3lame";
    public static final String AB = "-ab";
    public static final String AB_VALUE = "128k";

    public static final String SS = "-ss";
    public static final String VFRAMES = "-vframes";

    public static final String AMIX_INPUT = "amix=inputs=";
    public static final String DURATION_FIRST = "duration=first";

}
