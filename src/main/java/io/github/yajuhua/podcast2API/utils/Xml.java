package io.github.yajuhua.podcast2API.utils;

import io.github.yajuhua.podcast2API.Channel;
import io.github.yajuhua.podcast2API.Item;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 构建xml页面
 */
public class Xml {
    public static String build(Channel channel, List<Item> items){
        Item item = null;
        if (items.size()>0){
            item = items.stream().max(Comparator.comparingLong(Item::getCreateTime)).get();//获取最新的
        }

        StringBuilder xmlStr = new StringBuilder();
        xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlStr.append("<rss version=\"2.0\" encoding=\"UTF-8\" xmlns:atom=\"http://www.w3.org/2005/Atom\" " +
                "xmlns:itunes=\"http://www.itunes.com/dtds/podcast-1.0.dtd\">\n");
        xmlStr.append("\t<channel>\n");
        xmlStr.append("\t\t<title><![CDATA[ "+ channel.getTitle() +" ]]></title>\n");
        xmlStr.append("\t\t<pubDate>"+ TimeFormat.change(item==null?System.currentTimeMillis():item.getCreateTime()) +"</pubDate>\n");
        xmlStr.append("\t\t<language>zh-CN</language>\t");
        xmlStr.append("\t\t<link><![CDATA[ "+ channel.getLink() +" ]]></link>\n");
        xmlStr.append("\t\t<itunes:image href=\""+ channel.getImage() +"\"/>\n");
        xmlStr.append("\t\t<description><![CDATA[ "+ channel.getDescription() +" ]]></description>\n");
        xmlStr.append("\t\t<itunes:author><![CDATA[ "+ channel.getAuthor() +" ]]></itunes:author>\n");
        xmlStr.append("\t\t<itunes:category text=\""+ channel.getCategory() +"\"/>\n");

        for (Item item1 : items) {
            String enclosureType = item1.getEnclosureType();
            if (enclosureType == null){
                //默认
                enclosureType = "/video/mp4";
            }
            xmlStr.append("\t<item>\n");
            xmlStr.append("\t\t<pubDate>"+ TimeFormat.change(item1.getCreateTime()) +"</pubDate>\n");
            xmlStr.append("\t\t<title><![CDATA[ "+ item1.getTitle() +"  ]]></title>\n");
            xmlStr.append("\t\t<link><![CDATA[ "+ item1.getLink() +" ]]></link>\n");
            xmlStr.append("\t\t<enclosure url=\""+ item1.getEnclosure() + "\" "+ " type=\"" + enclosureType +"\"/>\n");
            xmlStr.append("\t\t<itunes:duration>"+ TimeFormat.duration(item1.getDuration()) +"</itunes:duration>\n");
            xmlStr.append("\t\t<description><![CDATA[ "+ item1.getDescription() +" ]]></description>\n");
            xmlStr.append("\t\t<itunes:image href=\""+ item1.getImage() +"\"/>\n");
            xmlStr.append("\t</item>\n");
        }

        xmlStr.append("\t</channel>\n");
        xmlStr.append("</rss>");
        return xmlStr.toString();
    }
}
