package io.github.yajuhua.download.downloader.ytdlp.utils;

/**
 * 转换工具
 */
public class Convert {
    /**
     * 00:00:00转换成秒
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public static int convertToSeconds(String s1,String s2,String s3){
        if (s3 == null){
            //分秒
            int sec = Integer.parseInt(s2) + Integer.parseInt(s1) * 60;
            return sec;
        }else if (s1 != null && s2 != null && s3 != null){
            return Integer.parseInt(s1)*60*60 + Integer.parseInt(s2) * 60 + Integer.parseInt(s3);
        }else {
            return -1;
        }

    }

    /**
     * 转换成字节数
     * @param s
     * @return
     */
    public static double convertToByteNum(String s){
        char unit = s.charAt(s.length() - 1);//单位 K M G
        String num = s.substring(0, s.length() - 1);//去掉单位的
        double byteNum = -1;
        switch (unit){
            case 'K':
                byteNum = Double.parseDouble(num)*1024;
                break;
            case 'M':
                byteNum = Double.parseDouble(num)*1024*1024;
                break;
            case 'G':
                byteNum = Double.parseDouble(num)*1024*1024*1024;
                break;
        }
        return byteNum;
    }
}
