package io.github.yajuhua.podcast2API.utils;

import io.github.yajuhua.podcast2API.extension.reception.InputAndSelectData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {
    public static Map InputAndSelectDataToMap(List<InputAndSelectData> inputAndSelectDataList){
        Map map = new HashMap();
        for (InputAndSelectData data : inputAndSelectDataList) {
            if (data.getContent() != null && !data.getContent().isEmpty()){
                map.put(data.getName(),data.getContent());
            }
        }
        return map;
    }
}
