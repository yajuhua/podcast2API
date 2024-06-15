package io.github.yajuhua.download.downloader.ytdlp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        String json = cmd("yt-dlp --dump-single-json --skip-download " + optionAndUrl);
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
     * cmd命令行的操作(字符串类型)
     * @param command 命令
     */
    public static String cmd(String command) throws IOException {
        String result = "";
        try {
            BufferedReader br = null;
            try {
                Process p = Runtime.getRuntime().exec(command);
                //解决中文乱码 GBK是汉字编码
                br = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    result+=line;
                }
                int exitCode = p.waitFor();
                if (exitCode != 0){
                    result = "";
                    br = new BufferedReader(new InputStreamReader(p.getErrorStream(),"UTF-8"));
                    while ((line = br.readLine()) != null) {
                        result+=line;
                    }
                    log.error("执行异常：{}",result);
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {

        }

        return result;
    }

}
