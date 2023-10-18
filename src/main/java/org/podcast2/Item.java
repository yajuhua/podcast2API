package org.podcast2;

import com.google.gson.annotations.Expose;

public class Item {
    
    private String title;
    
    private String link;
    
    private String enclosure;
    
    private Integer count;
    
    private Long createTime;
    
    private int duration;
    
    private String description;
    
    private String image;

    public Item() {
    }

    public Item(String title, String link, String enclosure, Integer count, Long createTime, int duration, String description, String image) {
        this.title = title;
        this.link = link;
        this.enclosure = enclosure;
        this.count = count;
        this.createTime = createTime;
        this.duration = duration;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEnclosure() {
        return this.enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        return "Item{title='" + this.title + "', link='" + this.link + "', enclosure='" + this.enclosure + "', count=" + this.count + ", createTime=" + this.createTime + ", duration=" + this.duration + ", description='" + this.description + "', image='" + this.image + "'}";
    }
}
