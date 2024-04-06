package io.github.yajuhua.download.downloader.aria2;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.yajuhua.download.commons.Context;
import io.github.yajuhua.download.commons.Operation;
import io.github.yajuhua.download.commons.Type;
import io.github.yajuhua.download.downloader.Downloader;
import io.github.yajuhua.download.commons.progress.DownloadProgressCallback;
import io.github.yajuhua.download.commons.utils.BuildCmd;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Aria2c implements Runnable, Downloader {

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
    private List<Process> processList;
    private DownloadProgressCallback callback;
    private Aria2Client aria2Client;
    private static Gson gson = new Gson();
    private Integer status = Context.UNKNOWN;
    private String cmd;

    /**
     * 开始下载
     * @throws Exception
     */
    public void startDownload() throws Exception {
       aria2Client = new Aria2Client(Context.ARIA2C_RPC_HOSTS);
            switch (operation){
                case Single:
                    //进行转换
                    File input = new File(dir.getAbsolutePath()+File.separator+uuid+Context.ARIA2_DEFAULT_FORMAT);
                    switch (type){
                        case Audio:
                            finalFormat = Context.M4A;
                            download();
                            if (status == Context.COMPLETED_DOWNLOAD){
                                File output = new File(dir.getAbsolutePath()+File.separator+uuid+Context.FORMAT_M4A);
                                updateProgressStatus(Context.TO_M4a_ING);
                                cmd = BuildCmd.ffmpegToM4a(input, output);
                                log.info("执行ffmpegToM4a:{}",cmd);
                                int exitCode = Runtime.getRuntime().exec(cmd).waitFor();
                                if (exitCode == 0){
                                    updateProgressStatus(Context.COMPLETED);
                                }else {
                                    updateProgressStatus(Context.TO_M4A_ERR);
                                }
                            }else if (status == Context.DOWNLOAD_ERR){
                                //结束
                                aria2Client.remove();
                                return;
                            }
                            break;
                        case Video:
                            finalFormat=Context.MP4;
                            download();
                            if (status == Context.COMPLETED_DOWNLOAD){
                                //进行转换
                                File output = new File(dir.getAbsolutePath()+File.separator+uuid+Context.FORMAT_MP4);
                                updateProgressStatus(Context.TO_MP4_ING);
                                cmd = BuildCmd.ffmpegToMp4(input, output);
                                log.info("执行ffmpegToMP4:{}",cmd);
                                int exitCode = Runtime.getRuntime().exec(cmd).waitFor();
                                if (exitCode == 0){
                                    updateProgressStatus(Context.COMPLETED);
                                }else {
                                    updateProgressStatus(Context.TO_MP4_ERR);
                                }
                            }else if (status == Context.DOWNLOAD_ERR){
                                //结束
                                aria2Client.remove();
                                return;
                            }
                            break;
                    }
                    //删除下载的
                    FileUtils.forceDelete(input);
                    break;

                case Merge:
                    //不开发
                    break;
            }
    }

    private void download() throws Exception{
        //下载选择
        Map options = new HashMap<>();
        options.put("out",this.uuid + Context.ARIA2_DEFAULT_FORMAT);
        options.put("dir",this.dir.getAbsolutePath().replace("\\","/"));
        String response = aria2Client.addUri(Context.ARIA2C_RPC_SECRET, Arrays.asList(links.get(0)),options);
        log.info("aria2响应:{}",response);

        String[] keys = {"status","totalLength","completedLength","downloadSpeed","dir"};
        String tellStatusStr;
        while ((tellStatusStr=aria2Client.tellStatus(keys))!=null){

            JsonObject jsonObject = gson.fromJson(tellStatusStr, JsonObject.class);

            int totalLength = jsonObject.get("result").getAsJsonObject().get("totalLength").getAsInt();
            int completedLength = jsonObject.get("result").getAsJsonObject().get("completedLength").getAsInt();
            int downloadSpeed = jsonObject.get("result").getAsJsonObject().get("downloadSpeed").getAsInt();
            String downloadStatus = jsonObject.get("result").getAsJsonObject().get("status").getAsString();
            String dir = jsonObject.get("result").getAsJsonObject().get("dir").getAsString();
            String id = jsonObject.get("id").getAsString();

            //把情况排除掉
            if (downloadStatus!=null && !downloadStatus.contains("error") && !id.contains(" ") && totalLength!=0 && completedLength!=0){

                if (totalLength!=0 && completedLength!=0 && downloadSpeed!=0){
                    //获取下载进度 totalLength-completedLength
                    BigDecimal totalLengthBigDecimal = BigDecimal.valueOf(totalLength);
                    BigDecimal completedLengthBigDecimal = BigDecimal.valueOf(completedLength);
                    BigDecimal hundred = BigDecimal.valueOf(100);
                    BigDecimal divide = completedLengthBigDecimal.divide(totalLengthBigDecimal, 2, RoundingMode.HALF_UP);
                    BigDecimal percentage = divide.multiply(hundred);
                    percent = percentage.doubleValue();
                    //求剩余时间
                    //(totalLength-completedLength)/downloadSpeed
                    BigDecimal surplusLength = totalLengthBigDecimal.subtract(completedLengthBigDecimal);
                    BigDecimal surplusSecond = surplusLength.divide(BigDecimal.valueOf(downloadSpeed), 0, RoundingMode.HALF_UP);
                    seconds = surplusSecond.intValue();
                    totalSize = totalLength;
                    speed = downloadSpeed;
                    status=Context.DOWNLOADING;
                    updateProgressStatus(Context.DOWNLOADING);
                }
            }
            if (downloadStatus.contains("error")){
                status=Context.DOWNLOAD_ERR;
                updateProgressStatus(Context.DOWNLOAD_ERR);
                break;
            }
            if (downloadStatus.contains("complete")){
                status=Context.COMPLETED_DOWNLOAD;
                updateProgressStatus(Context.COMPLETED_DOWNLOAD);
                break;
            }
        }
    }

    /**
     * 结束下载
     * @throws Exception
     */
    public void kill() throws Exception {
        log.info("结束下载：channelUuid:{} uuid:{}",channelUuid,uuid);
        if (aria2Client != null){
            aria2Client.remove();
        }
        List<File> files = Arrays.stream(dir.listFiles()).filter(file ->
                file.getName().contains(uuid)).collect(Collectors.toList());
        for (File file : files) {
            FileUtils.forceDelete(file);
        }
    }

    /**
     * 下载进度
     * @param callback
     */
    public void callback(DownloadProgressCallback callback){
        this.callback=callback;
    }

    /**
     * 是否完成
     * @return
     */
    public boolean isCompleted(){
        return status == Context.COMPLETED;
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
     * 更新状态码
     * @param status
     */
    private void updateProgressStatus(Integer status){
        callback.onProgressUpdate(channelUuid,uuid,
                status,percent,seconds,totalSize,speed
                ,operation.toString(),type.toString(),finalFormat);
    }
}
