package io.github.yajuhua.download.commons;


public class Context {
    public static final String YT_DLP = " yt-dlp ";
    public static final String YT_DLP_VERSION = "yt-dlp --version";
    public static final String YT_DLP_UPDATE = "yt-dlp -U";
    public static final String N_m3u8DL_RE = " N_m3u8DL-RE ";
    public static final String N_m3u8DL_RE_VERSION = "N_m3u8DL-RE --version";

    public static final String ARIA2_VERSION = "aria2c --version";
    public static final String ARIA2C_RPC_HOSTS = "http://127.0.0.1:6800/jsonrpc";
    public static final String ARIA2C_RPC_SECRET = "token:aria2";
    public static final String FFMPEG = " ffmpeg ";
    public static final String FFMPEG_VERSION = "ffmpeg -version";
    public static final String FFMPEG_INPUT = " -i ";
    public static final String FFMPEG_MERGE = " -c:v copy -c:a copy ";
    public static final String FFMPEG_TO_AUDIO = " -vn -c:a copy ";
    public static final String FFMPEG_TO_VIDEO = " -c copy -map 0:v -map 0:a -bsf:a aac_adtstoasc ";

    public static final String FORMAT_MP4 = ".mp4";
    public static final String MP4 = "mp4";
    public static final String FORMAT_M4A = ".m4a";
    public static final String M4A = "m4a";
    public static final String ARIA2_DEFAULT_FORMAT = ".format";

    public static final String SPACE = " ";
    public static final String TMP = "-tmp";


    public static final Integer COMPLETED_DOWNLOAD = 1;
    public static final Integer COMPLETED_MERGE = 2;
    public static final Integer COMPLETED_TO_MP4 = 3;
    public static final Integer COMPLETED_TO_M4a = 4;
    public static final Integer COMPLETED = 5;

    public static final Integer DOWNLOAD_ERR = 6;
    public static final Integer MERGE_ERR = 7;
    public static final Integer TO_MP4_ERR = 8;
    public static final Integer TO_M4A_ERR = 9;

    public static final Integer DOWNLOADING = 10;
    public static final Integer MERGE_ING = 11;
    public static final Integer TO_MP4_ING = 12;
    public static final Integer TO_M4a_ING = 13;

    public static final Integer DOWNLOADING_PATH1 = 14;
    public static final Integer DOWNLOADING_PATH2 = 15;
    public static final Integer COMPLETED_DOWNLOAD_PATH1 = 16;
    public static final Integer COMPLETED_DOWNLOAD_PATH2 = 17;
    public static final Integer DOWNLOAD_PATH1_ERR = 18;
    public static final Integer DOWNLOAD_PATH2_ERR = 19;
    public static final Integer UNKNOWN = -1;




}
