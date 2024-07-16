package io.github.yajuhua.download.commons.utils;

/**
 * 常用工具
 */
public class CommonUtils {
    /**
     * 版本比较
     * @param v1
     * @param v2
     * @return 如果v1 > v2 返回 1;v1<v2 返回 -1;相同返回0
     */
    public static int compareVersion(String v1,String v2){
        String[] v1Sp = v1.split("\\.");
        String[] v2Sp = v2.split("\\.");
        if (v1Sp.length != v2Sp.length){
            throw new RuntimeException("版本号格式不合法,格式必须为x.x.x");
        }
        String[] parts1 = v1Sp;
        String[] parts2 = v2Sp;

        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            int part1 = (i < parts1.length) ? Integer.parseInt(parts1[i]) : 0;
            int part2 = (i < parts2.length) ? Integer.parseInt(parts2[i]) : 0;

            if (part1 < part2) {
                return -1;
            } else if (part1 > part2) {
                return 1;
            }
        }

        return 0; // 版本号相同
    }
}
