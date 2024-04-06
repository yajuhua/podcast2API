package io.github.yajuhua.podcast2API;

import lombok.*;
import org.apache.commons.text.StringEscapeUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Channel {
    private String title;
    @Getter
    private String image;
    private String author;
    @Getter
    private String category;
    private String description;
    private String link;
    /**
     * 1表示继续更新
     * 0表示停止更新
     */
    private Integer status;
    private boolean escapeImage;//默认转义
    private boolean escapeCategory;//默认转义


    public String getImage() {
        if (!this.escapeImage){
            return StringEscapeUtils.escapeXml10(this.image);
        }
        return this.image;
    }

    public String getCategory() {
        if (!this.escapeCategory){
            return StringEscapeUtils.escapeXml10(this.category);
        }
        return this.category;
    }
}