package io.github.yajuhua.podcast2API;

import io.github.yajuhua.download.manager.Request;
import io.github.yajuhua.podcast2API.extension.build.ExtendList;
import io.github.yajuhua.podcast2API.setting.Setting;

import java.util.List;
import java.util.Map;

public interface Podcast2 {
    List<Item> items() throws Exception;

    Channel channel() throws Exception;

    Item latestItem() throws Exception;

    /**
     * 获取扩展选项
     * @return
     */
    ExtendList getExtensions() throws Exception;

    /**
     * 获取插件信息
     * 使用Map集合将使用说明、更新内容封装好
     * @return
     */
    Map getInfo() throws Exception;


    /**
     * 设置方法，可以用于cookie设置与更新
     * @return
     * @throws Exception
     */
    List<Setting> settings() throws Exception;

    /**
     * 获取单个节目下载请求
     * @param link 节目连接
     * @return 下载请求
     * @throws Exception
     */
    Request getRequest(String link) throws Exception;
}