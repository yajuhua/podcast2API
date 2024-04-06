package io.github.yajuhua.podcast2API.setting;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Setting {
 private String name;
 private String content;
 private String tip;
 private Long updateTime;
}
