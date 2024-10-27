package io.github.yajuhua.podcast2API.extension.reception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 将前端input和select的标题和内容封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputAndSelectData {
    private String name;
    private String content;
}
