package io.github.yajuhua.download.commons.progress;

import io.github.yajuhua.download.commons.Operation;
import io.github.yajuhua.download.commons.Type;
import lombok.*;

import java.util.Objects;

/**
 * 对下载进度信息进行封装
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadProgress {
    private String channelUuid;
    private String uuid;
    private Integer status;
    private double downloadProgress;
    private long downloadTimeLeft;
    private double totalSize;
    private double downloadSpeed;
    private String operation;
    private String type;
    private String finalFormat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadProgress that = (DownloadProgress) o;
        return that.channelUuid == channelUuid && that.uuid == uuid;
    }
}
