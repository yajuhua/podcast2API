package io.github.yajuhua.download.downloader.ytdlp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.yajuhua.download.commons.utils.BuildCmd;
import io.github.yajuhua.download.downloader.ytdlp.YtDlp;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.util.*;

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

    /**
     * cmd命令行的操作(数组类型)
     * @param command 命令
     */
    public static String cmd(String[] command) throws IOException {
        //拦截yt-dlp
        if (command != null && "yt-dlp".equals(command[0])){
            List<String> cmds = new ArrayList<>(Arrays.asList(command));

            //忽略warnings
            cmds.add(1,"--no-warnings");

            if (YtDlp.proxyArgs != null){
                //配置代理
                cmds.add(1,"--proxy");
                cmds.add(2,YtDlp.proxyArgs.toString());
            }

            command = new String[cmds.size()];
            for (int i = 0; i < cmds.size(); i++) {
                command[i] = cmds.get(i);
            }
        }
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
            if (YtDlp.kill){
                log.info("结束执行命令：{}",Arrays.toString(command));
            }else {
                log.error("执行命令时发生异常", e);
            }
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
