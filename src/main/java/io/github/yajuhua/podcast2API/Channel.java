package io.github.yajuhua.podcast2API;

import org.apache.commons.text.StringEscapeUtils;

public class Channel {

    private String title;
    
    private String image;
    
    private String author;
    
    private String category;
    
    private String description;
    
    private String link;
    
    private Integer status;

    public Channel() {
    }

    public Channel(String title, String image, String author, String category, String description, String link, Integer status) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.category = category;
        this.description = description;
        this.link = link;
        this.status = status;
    }

    public String getTitle() {
        return  this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return StringEscapeUtils.escapeXml10(this.image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return  this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return StringEscapeUtils.escapeXml10(this.category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
        return "Channel{title='" + this.title + "', image='" + this.image + "', author='" + this.author + "', category='" + this.category + "', description='" + this.description + "', link='" + this.link + "', status=" + this.status + "}";
    }
}