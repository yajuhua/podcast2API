package io.github.yajuhua.download.downloader;

import io.github.yajuhua.download.commons.progress.DownloadProgressCallback;

public interface Downloader {
    void startDownload() throws Exception;
    void callback(DownloadProgressCallback callback);
    void kill() throws Exception;
    boolean isCompleted() throws Exception;
    String getUuid();
    String getChannelUuid();
}
