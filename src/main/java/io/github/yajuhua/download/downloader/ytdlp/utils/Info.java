package io.github.yajuhua.download.downloader.ytdlp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        String ext = jsonObject.get("ext").getAsString();
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
                br = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));//解决中文乱码 GBK是汉字编码//二维码会乱码
                String line = null;
                while ((line = br.readLine()) != null) {
                    result+=line;
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