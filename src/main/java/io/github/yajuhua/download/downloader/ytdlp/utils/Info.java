package io.github.yajuhua.download.downloader.ytdlp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.yajuhua.download.commons.utils.BuildCmd;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Info {

    /**
     * 获取最终的扩展名
     * @param optionAndUrl
     * @return
     * @throws Exception
     */
    public static String getExt(String optionAndUrl) throws Exception {
        Gson gson = new Gson();
        String json = cmd(new String[]{"yt-dlp","-J",optionAndUrl});
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String ext = "";
        if (jsonObject.has("ext")){
            ext = jsonObject.get("ext").getAsString();
        }else if (jsonObject.has("entries")){
             JsonArray entries = jsonObject.get("entries").getAsJsonArray();
             JsonObject entrie = entries.get(entries.size() - 1).getAsJsonObject();
             JsonArray formats = entrie.get("formats").getAsJsonArray();
             ext = formats.get(formats.size() - 1).getAsJsonObject().get("ext").getAsString();
        }
        return ext;
    }

    /**
     * 获取最终的扩展名
     * @param args
     * @param url
     * @return
     * @throws Exception
     */
    public static String getExt(Map args, String url) throws Exception {
        Gson gson = new Gson();
        args.put("-J","");
        String json = cmd(BuildCmd.buildYtDlpCmd(args,url));
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String ext = "";
        if (jsonObject.has("ext")){
            ext = jsonObject.get("ext").getAsString();
        }else if (jsonObject.has("entries")){
            JsonArray entries = jsonObject.get("entries").getAsJsonArray();
            JsonObject entrie = entries.get(entries.size() - 1).getAsJsonObject();
            JsonArray formats = entrie.get("formats").getAsJsonArray();
            ext = formats.get(formats.size() - 1).getAsJsonObject().get("ext").getAsString();
        }
        return ext;
    }

    @Test
    public void test() throws Exception {
        Map args = new HashMap();
        String url = "https://rr2---sn-a0jpm-a0md.googlevideo.com/videoplayback?expire=1718968167&ei=Bwt1ZqLCCNSjmLAPtqWamAo&ip=2001%3A910%3A800%3A0%3A1933%3A4d57%3A6f7e%3A55ef&id=o-AFQfkRbDvrU2BJiUh2nm4mRxpN48TbH_uMRpUg6m-p4x&itag=139&source=youtube&requiressl=yes&xpc=EgVo2aDSNQ%3D%3D&mh=No&mm=31%2C29&mn=sn-a0jpm-a0md%2Csn-25ge7nsk&ms=au%2Crdu&mv=m&mvi=2&pl=32&initcwndbps=1150000&vprv=1&svpuc=1&mime=audio%2Fmp4&rqh=1&gir=yes&clen=199776172&ratebypass=yes&dur=32762.146&lmt=1701611630074351&mt=1718946294&fvip=3&keepalive=yes&c=ANDROID_TESTSUITE&txp=5432434&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cxpc%2Cvprv%2Csvpuc%2Cmime%2Crqh%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AJfQdSswRQIhAMdpbR_odCnylLY-LeRYH37lfhGMqbpIzdukrNLKQLpkAiANOEPnBApBlHB2tMuZ_DaYCHkkc__hYQOCs893OfzHXw%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AHlkHjAwRQIhAPikNjXg9d0Vq5FEr1s3v7HhyDX2hcRIxKfhF3HwU8QBAiAjErgX2pfU6PDYxZ3K9CFpwsAMjvoV7NQvdCXLxUAnFw%3D%3D&host=rr2---sn-a0jpm-a0md.googlevideo.com";
        String ext = getExt(args, url);
        System.out.println(ext);
    }

    /**
     * cmd命令行的操作(数组类型)
     * @param command 命令
     */
    public static String cmd(String[] command) throws IOException {
        log.info("执行命令：{}", Arrays.toString(command));
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;

        try {
            Process p = Runtime.getRuntime().exec(command);
            // 解决中文乱码
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                result.setLength(0);  // Clear the result buffer
                br = new BufferedReader(new InputStreamReader(p.getErrorStream(), "UTF-8"));
                while ((line = br.readLine()) != null) {
                    result.append(line).append("\n");
                }
                log.error("执行异常：{}", result.toString());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            log.error("执行命令时发生异常", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("关闭 BufferedReader 时发生异常", e);
                }
            }
        }

        return result.toString().trim();
    }

}
