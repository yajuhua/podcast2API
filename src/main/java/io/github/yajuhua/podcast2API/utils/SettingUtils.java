package io.github.yajuhua.podcast2API.utils;

import io.github.yajuhua.podcast2API.setting.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingUtils {
    public static Map SettingListToMap(List<Setting> settingList){
        Map map = new HashMap();
        if (settingList == null || settingList.isEmpty()){
            return map;
        }

        for (Setting setting : settingList) {
            String name = setting.getName();
            String content = setting.getContent();
            if (content != null && !content.isEmpty()){
                //排除空的
                map.put(name,content);
            }
        }
        return map;
    }
}
