package io.github.yajuhua.download.commons.utils;

import io.github.yajuhua.download.commons.Context;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

public class BuildCmd {

    /**
     * 构建ffmpeg 两个文件合并命令
     * @param input1
     * @param input2
     * @param output
     * @return
     */
    public static String ffmpegMerge(File input1,File input2,File output){
        Map map1 = new HashMap();
        map1.put(Context.FFMPEG_INPUT,input1.getAbsolutePath());
        Map map2 = new HashMap();
        map2.put(Context.FFMPEG_INPUT,input2.getAbsolutePath());
        String args1 = buildArgs(map1);
        String args2 = buildArgs(map2);
        //返回构建的命令字符串
        return Context.FFMPEG + args1 + args2 + Context.FFMPEG_MERGE + output.getAbsolutePath();
    }

    /**
     * 构建ffmpeg 两个文件合并命令
     * 构建数组命令
     * @param input1
     * @param input2
     * @param output
     * @return
     */
    public static String[] ffmpegMergeCmdByArray(File input1,File input2,File output){
        List<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-i");
        cmd.add(input1.getAbsolutePath());
        cmd.add("-i");
        cmd.add(input2.getAbsolutePath());
        //-c:v copy -c:a copy
        cmd.add("-c:v");
        cmd.add("copy");
        cmd.add("-c:a");
        cmd.add("copy");
        cmd.add(output.getAbsolutePath());

        String[] arrCmd = new String[cmd.size()];

        for (int i = 0; i < cmd.size(); i++) {
            arrCmd[i] = cmd.get(i);
        }
       return arrCmd;
    }

    /**
     * 将文件转换成mp4
     * @param input
     * @param output
     * @return
     */
    public static String ffmpegToMp4(File input,File output){
        Map map = new HashMap();
        map.put(Context.FFMPEG_INPUT,input.getAbsolutePath());
        String args = buildArgs(map);
        return Context.FFMPEG + args + Context.FFMPEG_TO_VIDEO + output.getAbsolutePath();
    }

    /**
     * 将文件转换成mp4
     * @param input
     * @param output
     * @return
     */
    public static String[] ffmpegToMp4CmdByArray(File input,File output){
        List<String> args = new ArrayList<>();
        args.add("ffmpeg");
        args.add("-i");
        args.add(input.getAbsolutePath());
        args.add("-c");
        args.add("copy");
        args.add("-map");
        args.add("0:a");
        args.add("-bsf:a");
        args.add("aac_adtstoasc");
        args.add(output.getAbsolutePath());
        // -c copy -map 0:v -map 0:a -bsf:a aac_adtstoasc
        String[] cmd = new String[args.size()];
        for (int i = 0; i < args.size(); i++) {
            cmd[i] = args.get(i);
        }
        return cmd;
    }

    /**
     * 将文件转换成m4a
     * @param input
     * @param output
     * @return
     */
    public static String[] ffmpegToM4aCmdByArray(File input,File output){

        List<String> args = new ArrayList<>();
        args.add("ffmpeg");
        args.add("-i");
        args.add(input.getAbsolutePath());
        args.add("-vn");
        args.add("-c:a");
        args.add("copy");
        args.add(output.getAbsolutePath());
        // -vn -c:a copy
        String[] cmd = new String[args.size()];
        for (int i = 0; i < args.size(); i++) {
            cmd[i] = args.get(i);
        }
        return cmd;
    }

    /**
     * 将文件转换成m4a
     * @param input
     * @param output
     * @return
     */
    public static String ffmpegToM4a(File input,File output){
        Map map = new HashMap();
        map.put(Context.FFMPEG_INPUT,input.getAbsolutePath());
        String args = buildArgs(map);
        return Context.FFMPEG + args + Context.FFMPEG_TO_AUDIO + output.getAbsolutePath();
    }



    /**
     * 构建参数
     * @param args
     * @return
     */
    public static String buildArgs(Map args){
        String argsStr = "";
        Set keySet = args.keySet();
        for (Object key : keySet) {
            Object value = args.get(key);
            String arg = Context.SPACE + key + Context.SPACE + value + Context.SPACE;
            argsStr = argsStr + arg;
        }
        return argsStr;
    }

    /**
     * 构建参数
     * @param args
     * @return
     */
    public static String[] buildArrayArgs(Map args){
        Set keySet = args.keySet();
        List<String> argList = new ArrayList<>();

        for (Object k : keySet) {
            argList.add(k.toString());
            if (args.get(k) != null && !args.get(k).toString().isEmpty()){
                argList.add(args.get(k).toString());
            }
        }

        String[] cmd = new String[argList.size()];
        for (int i = 0; i < argList.size(); i++) {
            cmd[i] = argList.get(i);
        }

        return cmd;
    }

    /**
     * 构建yt-dlp命令
     * @param args 参数
     * @param link 下载链接
     * @return
     */
    public static String[] buildYtDlpCmd(Map args,String link){
        Set keySet = args.keySet();
        List<String> argList = new ArrayList<>();
        argList.add("yt-dlp");
        for (Object k : keySet) {
            argList.add(k.toString());
            if (args.get(k) != null && !args.get(k).toString().isEmpty()){
                argList.add(args.get(k).toString());
            }
        }
        argList.add(link);

        String[] cmd = new String[argList.size()];
        for (int i = 0; i < argList.size(); i++) {
            cmd[i] = argList.get(i);
        }

        return cmd;
    }

    @Test
    public void test(){
        Map agrs = new HashMap();
        //-N 10  --path /data/resources  --output bf11ec98-191a-485c-a471-30f49a052c46.%(ext)s
        agrs.put("-N",10);
        agrs.put("--path","/data/resources");
        agrs.put("--output","bf11ec98-191a-485c-a471-30f49a052c46.%(ext)s");
        agrs.put("-J","");
        agrs.put("-f",null);
        String[] strings = buildArrayArgs(agrs);
        System.out.println(Arrays.toString(strings));
    }
}
