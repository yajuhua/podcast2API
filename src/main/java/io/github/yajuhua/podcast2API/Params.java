package io.github.yajuhua.podcast2API;

import io.github.yajuhua.podcast2API.extension.reception.InputAndSelectData;
import io.github.yajuhua.podcast2API.setting.Setting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Params {
    private String url;
    private Type type;
    private List<Integer> episodes;
    private List<InputAndSelectData> inputAndSelectDataList;
    private List<Setting> settings;
    private String podcast2Version;
    private String podcast2Uuid;
    private Item lastItem;//上一次的
}
