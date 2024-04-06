package io.github.yajuhua.download.downloader.nm3u8dlre;

import io.github.yajuhua.download.commons.Context;
import io.github.yajuhua.download.commons.Operation;
import io.github.yajuhua.download.commons.Type;
import io.github.yajuhua.download.downloader.Downloader;
import io.github.yajuhua.download.commons.progress.DownloadProgressCallback;
import io.github.yajuhua.download.commons.utils.BuildCmd;
import io.github.yajuhua.download.downloader.nm3u8dlre.utils.Convert;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Nm3u8DlRe implements Runnable, Downloader {
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
    private Integer status;
    private boolean kill = false;
    private Matcher matcher;
    private BufferedReader br;
    private String line;
    private Process process;
    private int exitCode;
    private String cmd;

    /**
     * 匹配下载日志正则表达式
     */
    private final Pattern compile
            = Pattern.compile("\\[0m\\s+(?<percent>\\d{1,2})%.*/" +
            "(?<totalSize>.+?)\u001B.*\\[38;5;2m(?<speed>\\d+\\.\\d+K?M?G?)Bps." +
            "*\\[38;5;12m(?<s1>\\d{2}):(?<s2>\\d{2}):?(?<s3>\\d{2})?");

    /**
     * 开始下载
     * @throws Exception
     */
    public void startDownload() throws Exception {
        log.info("operation:{}",operation.toString());
        log.info("type:{}",type.toString());
        finalFormat = type.equals(Type.Video)? Context.MP4:Context.M4A;
        switch (operation){
            case Single:
                //获取第一条链接
                log.info("要下载的链接:{}",links.get(0));
                //构建命令
                args.put("--save-name",this.uuid);
                args.put("--save-dir",this.dir);
                cmd = Context.N_m3u8DL_RE + BuildCmd.buildArgs(args) + links.get(0);
                log.info("构建命令:{}",cmd);

                try {
                    process = Runtime.getRuntime().exec(cmd);
                    //解析日志
                    br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                    while ((line=br.readLine()) != null){
                        if (kill){return;}
                            if (line.contains("Done")){
                                //已经完成下载
                                percent=100;
                                updateProgressStatus(Context.COMPLETED_DOWNLOAD);
                            }else if (line.contains("ERROR")){
                                updateProgressStatus(Context.DOWNLOAD_ERR);
                                return;
                            }
                            matcher = compile.matcher(line);
                            if (matcher.find()){
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING);
                            }
                        }
                    exitCode = process.waitFor();
                    if (exitCode != 0){
                        //下载错误，直接退出
                        updateProgressStatus(Context.DOWNLOAD_ERR);
                        return;
                    }else {
                        percent=100;
                        updateProgressStatus(Context.COMPLETED_DOWNLOAD);
                    }
                    log.info("exitCode:{}",exitCode);
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    if (br != null){
                        br.close();
                    }
                }
                //根据类型转换
                switch (type){
                    case Audio:
                        log.info("进行音频转换");
                        //获取下载文件的绝对路径
                        List<File> files = Arrays.stream(dir.listFiles()).filter(file -> file.getName().
                                contains(uuid)).collect(Collectors.toList());
                        File downloadFile = null;
                        if (files.size() != 0){
                            downloadFile = files.get(0);
                        }
                        log.info("下载文件的绝对路径：{}",downloadFile.getAbsolutePath());

                        //判断是否需要转换，如果是m4a就不需要转换，反之转换
                        boolean isM4a = FilenameUtils.isExtension(downloadFile.getName(),Context.M4A);

                        if (isM4a){
                            //无须转换
                            log.info("无须转换");
                        }else {
                            updateProgressStatus(Context.TO_M4a_ING);
                            log.info("需要转换");
                            //构建转换命令
                            File output = new File(dir.getAbsolutePath() + File.separator + uuid + Context.FORMAT_M4A);
                            cmd = BuildCmd.ffmpegToM4a(downloadFile,output);
                            log.info("构建命令:{}",cmd);
                            process = Runtime.getRuntime().exec(cmd);
                            exitCode = process.waitFor();
                            log.info("exitCode:{}",exitCode);
                            if (exitCode != 0){
                                updateProgressStatus(Context.TO_M4A_ERR);
                                return;
                            }
                            log.info("清理文件:{}",downloadFile);
                            FileUtils.forceDelete(downloadFile);
                        }
                        updateProgressStatus(Context.COMPLETED);
                        break;
                    case Video:
                        log.info("进行视频转换");
                        //获取下载文件的绝对路径
                        List<File> files2 = Arrays.stream(dir.listFiles()).filter(file -> file.getName().
                                contains(uuid)).collect(Collectors.toList());
                        File downloadFile2 = null;
                        if (files2.size() != 0){
                            downloadFile2 = files2.get(0);
                        }
                        log.info("下载文件的绝对路径：{}",downloadFile2.getAbsolutePath());

                        //判断是否需要转换，如果是mp4就不需要转换，反之转换
                        boolean isMp4 = FilenameUtils.isExtension(downloadFile2.getName(),Context.MP4);
                        if (isMp4){
                            log.info("无须转换");
                        }else {
                            updateProgressStatus(Context.TO_MP4_ING);
                            log.info("需要转换");
                            //构建转换命令
                            File output = new File(dir.getAbsolutePath() + File.separator + uuid + Context.FORMAT_MP4);
                            cmd = BuildCmd.ffmpegToMp4(downloadFile2,output);
                            log.info("构建命令:{}",cmd);
                            process = Runtime.getRuntime().exec(cmd);
                            exitCode = process.waitFor();
                            log.info("exitCode:{}",exitCode);

                            if (exitCode != 0){
                                updateProgressStatus(Context.TO_MP4_ERR);
                                return;
                            }
                            log.info("清理文件:{}",downloadFile2);
                            FileUtils.forceDelete(downloadFile2);
                        }
                        //更新进度信息
                        updateProgressStatus(Context.COMPLETED);
                        break;
                }
                break;
            case Merge:
                String fileName1 = uuid + Context.TMP + "1";
                String fileName2 = uuid + Context.TMP + "2";
                args.put("--save-name",fileName1);
                args.put("--save-dir",dir);
                cmd = Context.N_m3u8DL_RE + BuildCmd.buildArgs(args) + links.get(0);
                log.info("执行命令：{}",cmd);
                try {
                    process = Runtime.getRuntime().exec(cmd);
                    br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                    while ((line=br.readLine()) != null ){
                        if (kill){return;}
                            if (line.contains("Done")){
                                percent=100;
                                updateProgressStatus(Context.COMPLETED_DOWNLOAD_PATH1);
                            }else if (line.contains("ERROR")){
                                updateProgressStatus(Context.DOWNLOAD_PATH1_ERR);
                                return;
                            }
                            matcher = compile.matcher(line);
                            if (matcher.find()){
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING_PATH1);
                            }
                        }
                    exitCode = process.waitFor();
                    log.info("exiCode:{}",exitCode);
                    if (exitCode != 0){
                        updateProgressStatus(Context.DOWNLOAD_PATH1_ERR);
                        return;
                    }else {
                        percent=100;
                        updateProgressStatus(Context.COMPLETED_DOWNLOAD_PATH1);
                    }
                } catch (Exception e) {
                   log.error(e.getMessage());
                } finally {
                    if (br != null){
                        br.close();
                    }
                }

                //第二条命令
                args.put("--save-name",fileName2);
                cmd = Context.N_m3u8DL_RE + BuildCmd.buildArgs(args) + links.get(1);
                log.info("执行命令：{}",cmd);
                try {
                    process = Runtime.getRuntime().exec(cmd);
                    br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                    while ((line=br.readLine()) != null){
                        if (kill){return;}
                            if (line.contains("Done")){
                                percent=100;
                                updateProgressStatus(Context.COMPLETED_DOWNLOAD_PATH2);
                            }else if (line.contains("ERROR")){
                                updateProgressStatus(Context.DOWNLOAD_PATH2_ERR);
                                return;
                            }
                            matcher = compile.matcher(line);
                            if (matcher.find()){
                                matcherInfo();
                                updateProgressStatus(Context.DOWNLOADING_PATH2);
                            }
                        }
                    exitCode = process.waitFor();
                    log.info("exiCode:{}",exitCode);
                    if (exitCode != 0){
                        updateProgressStatus(Context.DOWNLOAD_PATH2_ERR);
                        return;
                    }else {
                        percent=100;
                        updateProgressStatus(Context.COMPLETED_DOWNLOAD_PATH2);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    if (br != null){
                        br.close();
                    }
                }
                List<File> files = Arrays.stream(dir.listFiles()).filter(file -> file.getName()
                        .contains(fileName1) || file.getName().contains(fileName2)).collect(Collectors.toList());
                File output = new File(dir.getAbsolutePath() + File.separator + uuid + Context.FORMAT_MP4);
                if (files.size() < 2){
                    updateProgressStatus(Context.MERGE_ERR);
                    return;
                }
                cmd = BuildCmd.ffmpegMerge(files.get(0), files.get(1), output);
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
                FileUtils.forceDelete(files.get(0));
                FileUtils.forceDelete(files.get(1));
                break;
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
    public void kill() throws Exception {
        if (dir != null){
            kill = true;
            List<File> files = Arrays.stream(dir.listFiles()).filter(file -> file.getName()
                    .contains(uuid)).collect(Collectors.toList());
            for (File file : files) {
                FileUtils.forceDelete(file);
            }
        }
    }

    /**
     * 是否完成
     * @return
     * @throws Exception
     */
    public boolean isCompleted(){
        final Integer[] status1 = new Integer[1];
        callback((channelUuid, uuid, status
                , downloadProgress, downloadTimeLeft
                , totalSize, downloadSpeed, operation
                , type, finalFormat) -> status1[0] = status);
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
        String totalSizeTmp = matcher.group("totalSize");//未去单位
        totalSize = Convert.convertToByteNum(totalSizeTmp.substring(0,totalSizeTmp.length()-1));//去掉B
        speed = Convert.convertToByteNum(matcher.group("speed"));
        seconds = Convert.convertToSeconds(matcher.group("s1"),
        matcher.group("s2"), matcher.group("s3"));//剩余秒数
    }
}
