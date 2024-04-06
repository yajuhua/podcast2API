package io.github.yajuhua.download.commons.utils;

import io.github.yajuhua.download.commons.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
}
