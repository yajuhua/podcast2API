package io.github.yajuhua.podcast2API;

import com.google.gson.annotations.Expose;
import org.apache.commons.text.StringEscapeUtils;

public class Item {
    
    private String title;
    
    private String link;
    
    private String enclosure;

    private Long createTime;
    
    private int duration;
    
    private String description;
    
    private String image;
    private String equal;
    private boolean escapeEnclosure = true;//默认转义
    private boolean escapeImage = true;//默认转义

    public Item() {
    }

    public Item(String title, String link, String enclosure, Long createTime, int duration, String description, String image, String equal) {
        this.title = title;
        this.link = link;
        this.enclosure = enclosure;
        this.createTime = createTime;
        this.duration = duration;
        this.description = description;
        this.image = image;
        this.equal = equal;
    }


    public void setEscapeEnclosure(boolean escapeEnclosure) {
        this.escapeEnclosure = escapeEnclosure;
    }

    public void setEscapeImage(boolean escapeImage) {
        this.escapeImage = escapeImage;
    }

    public String getEqual() {
        return equal;
    }

    public void setEqual(String equal) {
        this.equal = equal;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return this.link ;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEnclosure() {
        //判断是默认还是自定义模式
        if (this.enclosure.contains("${rename}") || this.enclosure.contains("${path}") || this.escapeEnclosure == false){
            return this.enclosure;
        }
            return StringEscapeUtils.escapeXml10(this.enclosure);
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return this.description ;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        if (this.escapeImage == false){
            return this.image;
        }
        return StringEscapeUtils.escapeXml10(this.image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", enclosure='" + enclosure + '\'' +
                ", createTime=" + createTime +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", equal='" + equal + '\'' +
                '}';
    }
}
