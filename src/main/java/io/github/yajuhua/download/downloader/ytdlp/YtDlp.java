package io.github.yajuhua.download.downloader.ytdlp;

import com.google.gson.Gson;
import io.github.yajuhua.download.commons.Context;
import io.github.yajuhua.download.commons.Operation;
import io.github.yajuhua.download.commons.Type;
import io.github.yajuhua.download.downloader.Downloader;
import io.github.yajuhua.download.commons.progress.DownloadProgressCallback;
import io.github.yajuhua.download.commons.utils.BuildCmd;
import io.github.yajuhua.download.downloader.ytdlp.json.item.YtDlpItemInfo;
import io.github.yajuhua.download.downloader.ytdlp.utils.Convert;
import io.github.yajuhua.download.downloader.ytdlp.utils.Info;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YtDlp implements Runnable, Downloader {

    private int seconds = 0;
    private double speed = 0;
    private double percent = 0;
    private double totalSize = 0;
    private String finalFormat = "unknown";


    private List<String> links;
    private Operation operation;
    private Type type;
    private String channelUuid;
    private String uuid;
    private Map args;
    private File dir;
    private DownloadProgressCallback callback;
    private Matcher matcher;
    private String line;
    private BufferedReader br;
    private Process process;
    private int exitCode;
    private String cmd;
    private boolean kill = false;
    private final Pattern compile = Pattern.compile(
            "\\[download\\]\\s+(?<percent>\\d+\\.\\d)%.*(?<totalSize>\\d+\\.\\d+K?M?G?)iB\\s+" +
                    "at\\s+(?<speed>\\d+\\.\\d+K?M?G?)iB/s\\s+ETA\\s+(?<s1>\\d{2}):(?<s2>\\d{2}):?(?<s3>\\d{2})?");

    public void startDownload()throws Exception {
        //TODO 存储空间
        log.info("operation:{}", operation.toString());
        log.info("type:{}", type.toString());
        Gson gson = new Gson();
        //无须合并，没这需求
        switch (operation) {
            case Single:
                String json = Info.cmd("yt-dlp -J " + links.get(0));
                YtDlpItemInfo ytDlpItemInfo = gson.fromJson(json, YtDlpItemInfo.class);
                if (json == null || ytDlpItemInfo == null){
                    log.error("解析失败：{}",links.get(0),json);
                    updateProgressStatus(Context.DOWNLOAD_ERR);
                    return;
                }
                switch (type){
                    case Video:
                        //1.获取ext
                        boolean supportMp4 = !ytDlpItemInfo.getFormats().stream().filter(formatsDTO -> "mp4".equals(formatsDTO.getExt()))
                                .collect(Collectors.toList()).isEmpty();
                        //2.下载,如果不是mp4那么就转换成mp4;
                        if (supportMp4){
                            args.put("-f","mp4");
                        }else {
                            args.put("--postprocessor-args",Context.FFMPEG_TO_VIDEO);
                        }
                        finalFormat = Context.MP4;
                        args.put("--path",dir.getAbsolutePath());
                        args.put("--output",uuid + ".%(ext)s");
                        cmd = Context.YT_DLP + BuildCmd.buildArgs(args) + links.get(0);
                        log.info("执行命令：{}",cmd);
                        process = Runtime.getRuntime().exec(cmd);
                        br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                        while ((line=br.readLine()) != null){
                            if (kill){return;}
                            matcher = compile.matcher(line);
                            if (matcher.find()) {
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING);
                            }
                        }
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.DOWNLOAD_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED);
                        }
                        break;
                    case Audio:
                        //m4a
                        boolean supportM4a = !ytDlpItemInfo.getFormats().stream().filter(formatsDTO -> "m4a".equals(formatsDTO.getExt()))
                                .collect(Collectors.toList()).isEmpty();
                        if (supportM4a){
                            args.put("-f","m4a");
                        }else {
                            args.put("-x","");
                            args.put("--audio-format","m4a");
                        }
                        finalFormat = Context.M4A;
                        args.put("--path",dir.getAbsolutePath());
                        args.put("--output",uuid + ".%(ext)s");
                        cmd = Context.YT_DLP + BuildCmd.buildArgs(args) + links.get(0);
                        log.info("执行命令：{}",cmd);
                        process = Runtime.getRuntime().exec(cmd);
                        br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                        while ((line=br.readLine()) != null){
                            if (kill){return;}
                            matcher = compile.matcher(line);
                            if (matcher.find()) {
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING);
                            }
                        }
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.DOWNLOAD_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED);
                        }
                        break;

                    case Customization:
                        args.put("--path",dir.getAbsolutePath());
                        args.put("--output",uuid + ".%(ext)s");
                        cmd = Context.YT_DLP + BuildCmd.buildArgs(args) + links.get(0);
                        finalFormat = Info.getExt(BuildCmd.buildArgs(args) + links.get(0));
                        log.info("获取扩展名：{}",finalFormat);
                        log.info("执行命令：{}",cmd);
                        process = Runtime.getRuntime().exec(cmd);
                        br= new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                        while ((line=br.readLine()) != null){
                            if (kill){return;}
                            matcher = compile.matcher(line);
                            if (matcher.find()) {
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING);
                            }
                        }
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.DOWNLOAD_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED);
                        }
                        break;
                }
                break;
            case Merge:
                //p1
                String json1 = Info.cmd("yt-dlp -J " + links.get(0));
                YtDlpItemInfo ytDlpItemInfo1 = gson.fromJson(json1, YtDlpItemInfo.class);
                if (json1 == null || ytDlpItemInfo1 == null){
                    log.error("解析失败：{}",links.get(0));
                    updateProgressStatus(Context.DOWNLOAD_ERR);
                    return;
                }
                //p2
                String json2 = Info.cmd("yt-dlp -J " + links.get(1));
                YtDlpItemInfo ytDlpItemInfo2 = gson.fromJson(json2, YtDlpItemInfo.class);
                if (json2 == null || ytDlpItemInfo2 == null){
                    log.error("解析失败：{}",links.get(1));
                    updateProgressStatus(Context.DOWNLOAD_ERR);
                    return;
                }
                switch (type){
                    case Video:
                        String fileName1 = uuid + Context.TMP + "1." + ytDlpItemInfo1.getExt();
                        String fileName2 = uuid + Context.TMP + "2." + ytDlpItemInfo2.getExt();
                        //下载第一条
                        finalFormat = Context.MP4;
                        args.put("--path",dir.getAbsolutePath());
                        args.put("--output",fileName1);
                        cmd = Context.YT_DLP + BuildCmd.buildArgs(args) + links.get(0);
                        log.info("执行命令：{}",cmd);
                        process = Runtime.getRuntime().exec(cmd);
                        br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                        while ((line=br.readLine()) != null){
                            if (kill){return;}
                            matcher = compile.matcher(line);
                            if (matcher.find()) {
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING_PATH1);
                            }
                        }
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.DOWNLOAD_PATH1_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED_DOWNLOAD_PATH1);
                        }

                        //下载第二条
                        finalFormat = Context.MP4;
                        args.put("--path",dir.getAbsolutePath());
                        args.put("--output",fileName2);
                        cmd = Context.YT_DLP + BuildCmd.buildArgs(args) + links.get(1);
                        log.info("执行命令：{}",cmd);
                        process = Runtime.getRuntime().exec(cmd);
                        br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                        while ((line=br.readLine()) != null){
                            if (kill){return;}
                            matcher = compile.matcher(line);
                            if (matcher.find()) {
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING_PATH2);
                            }
                        }
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.DOWNLOAD_PATH2_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED_DOWNLOAD_PATH2);
                        }

                        //合并
                        File file1 = new File(dir.getAbsolutePath() + File.separator + fileName1);
                        File file2 = new File(dir.getAbsolutePath() + File.separator + fileName2);
                        File output = new File(dir.getAbsolutePath() + File.separator + uuid + Context.FORMAT_MP4);
                        if (!file1.exists() || !file1.exists()){
                            updateProgressStatus(Context.MERGE_ERR);
                            return;
                        }
                        cmd = BuildCmd.ffmpegMerge(file1,file2, output);
                        log.info("执行命令:{}",cmd);
                        updateProgressStatus(Context.MERGE_ING);
                        process = Runtime.getRuntime().exec(cmd);
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.MERGE_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED);
                        }
                        log.info("exitCode:{}",exitCode);
                        //删除文件
                        FileUtils.forceDelete(file1);
                        FileUtils.forceDelete(file2);
                        break;
                    case Audio:
                        //m4a
                        args.put("-f","m4a");
                        finalFormat = Context.M4A;
                        args.put("--path",dir.getAbsolutePath());
                        args.put("--output",uuid + ".%(ext)s");
                        cmd = Context.YT_DLP + BuildCmd.buildArgs(args) + links.get(0);
                        log.info("执行命令：{}",cmd);
                        process = Runtime.getRuntime().exec(cmd);
                        br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                        while ((line=br.readLine()) != null){
                            if (kill){return;}
                            matcher = compile.matcher(line);
                            if (matcher.find()) {
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING);
                            }
                        }
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.DOWNLOAD_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED);
                        }
                        break;

                    case Customization:
                        args.put("--path",dir.getAbsolutePath());
                        args.put("--output",uuid + ".%(ext)s");
                        cmd = Context.YT_DLP + BuildCmd.buildArgs(args) + links.get(0);
                        finalFormat = Info.getExt(BuildCmd.buildArgs(args) + links.get(0));
                        log.info("获取扩展名：{}",finalFormat);
                        log.info("执行命令：{}",cmd);
                        process = Runtime.getRuntime().exec(cmd);
                        br= new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                        while ((line=br.readLine()) != null){
                            if (kill){return;}
                            matcher = compile.matcher(line);
                            if (matcher.find()) {
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING);
                            }
                        }
                        exitCode = process.waitFor();
                        if (exitCode != 0){
                            updateProgressStatus(Context.DOWNLOAD_ERR);
                            return;
                        }else {
                            updateProgressStatus(Context.COMPLETED);
                        }
                        break;
                }
                break;
        }

        if (br != null){
            br.close();
        }
    }

    @Override
    public void run() {
        try {
            startDownload();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    /**
     * 结束下载
     * @throws Exception
     */
    public void kill(){
        kill=true;
        this.updateProgressStatus(Context.REMOVE);
    }

    /**
     * 是否完成
     * @return
     */
    public boolean isCompleted(){
        final Integer[] status1 = new Integer[1];
        callback((channelUuid, uuid, status, downloadProgress, downloadTimeLeft
                , totalSize, downloadSpeed, operation, type, finalFormat) -> status1[0] = status);
        return status1[0] == Context.COMPLETED;
    }

    /**
     * 获取下载回调信息
     * @param callback
     */
    public void callback(DownloadProgressCallback callback){
        this.callback = callback;
    }

    /**
     * 更新状态码
     * @param status
     */
    private void updateProgressStatus(Integer status){
        callback.onProgressUpdate(channelUuid,uuid,
                status,percent,seconds,totalSize,speed
                ,operation.toString(),type.toString(),finalFormat);
    }

    /**
     * 匹配下载日志信息
     */
    private void matcherInfo(){
        percent = Double.parseDouble(matcher.group("percent"));
        totalSize = Convert.convertToByteNum(matcher.group("totalSize"));
        speed = Convert.convertToByteNum(matcher.group("speed"));
        seconds = Convert.convertToSeconds(matcher.group("s1"),
                matcher.group("s2"), matcher.group("s3"));//剩余秒数
    }
}
