package io.github.yajuhua.podcast2API;

import io.github.yajuhua.download.manager.Request;

import lombok.*;
import org.apache.commons.text.StringEscapeUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    
    private String title;
    private String link;
    @Getter
    private String enclosure;
    private Long createTime;
    private int duration;
    private String description;
    @Getter
    private String image;
    private String equal;
    private boolean escapeEnclosure = true;//默认转义
    private boolean escapeImage = true;//默认转义
    private Request request;//下载请求
    /**
     * 原视频发布时间
     */
    private Long publicTime;
    /**
     * 附件类型 如video/mp4
     */
    private String enclosureType;

    public String getEnclosure() {
        //判断是默认还是自定义模式
        if (this.escapeEnclosure == false){
            return this.enclosure;
        }
            return StringEscapeUtils.escapeXml10(this.enclosure);
    }

    public String getImage() {
        if (this.escapeImage == false){
            return this.image;
        }
        return StringEscapeUtils.escapeXml10(this.image);
    }
}
