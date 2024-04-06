package io.github.yajuhua.download.downloader.ytdlp;

import io.github.yajuhua.download.commons.Context;
import io.github.yajuhua.download.commons.Operation;
import io.github.yajuhua.download.commons.Type;
import io.github.yajuhua.download.downloader.Downloader;
import io.github.yajuhua.download.commons.progress.DownloadProgressCallback;
import io.github.yajuhua.download.commons.utils.BuildCmd;
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
        log.info("operation:{}", operation.toString());
        log.info("type:{}", type.toString());
        //无须合并，没这需求
        switch (type){
            case Video:
                args.put("-f","mp4");
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
        if (dir != null){
            //删除修改文件
            List<File> files = Arrays.stream(dir.listFiles()).filter(file -> file.getName()
                    .contains(uuid)).collect(Collectors.toList());
            try {
                for (File file : files) {
                    FileUtils.forceDelete(file);
                }
            } catch (IOException e) {
                log.error("无法删除：{}"+e.getMessage());
            }
        }
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
