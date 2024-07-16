package io.github.yajuhua.download.manager;

import io.github.yajuhua.download.commons.Operation;
import io.github.yajuhua.download.commons.Type;
import io.github.yajuhua.download.downloader.Downloader;
import io.github.yajuhua.download.downloader.aria2.Aria2c;
import io.github.yajuhua.download.downloader.nm3u8dlre.Nm3u8DlRe;
import io.github.yajuhua.download.downloader.ytdlp.YtDlp;
import io.github.yajuhua.download.manager.DownloadManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request{
    private List<String> links;
    private Operation operation;
    private Type type;
    private String channelUuid;
    private String uuid;
    private Map args;
    private File dir;
    private DownloadManager.Downloader downloader;
    private Object customizeDownloader;

    public Downloader build(){

        //构建下载
        Downloader downloader1 = null;
        

        switch (downloader){
            case Nm3u8DlRe:
                downloader1 = Nm3u8DlRe.builder()
                        .links(links)
                        .operation(operation)
                        .type(type)
                        .channelUuid(channelUuid)
                        .uuid(uuid)
                        .args(args)
                        .dir(dir)
                        .build();
                break;
            case YtDlp:
                downloader1 = YtDlp.builder()
                        .links(links)
                        .operation(operation)
                        .type(type)
                        .channelUuid(channelUuid)
                        .uuid(uuid)
                        .args(args)
                        .dir(dir)
                        .build();
                break;
            case Aria2:
                downloader1 = Aria2c.builder()
                        .links(links)
                        .operation(operation)
                        .type(type)
                        .channelUuid(channelUuid)
                        .uuid(uuid)
                        .args(args)
                        .dir(dir)
                        .build();
                break;
            case Customize:
                try {
                    Downloader tmp = (Downloader) customizeDownloader;
                    if (customizeDownloader == null) {
                        throw new IllegalArgumentException("customizeDownloader is not provided.");
                    }
                    setField(customizeDownloader, "links", links);
                    setField(customizeDownloader, "operation", operation);
                    setField(customizeDownloader, "type", type);
                    setField(customizeDownloader, "channelUuid", channelUuid);
                    setField(customizeDownloader, "uuid", uuid);
                    setField(customizeDownloader, "args", args);
                    setField(customizeDownloader, "dir", dir);

                    downloader1 = tmp;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to set fields for custom downloader", e);
                }
                break;
        }

        return downloader1;
    }

    private void setField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
