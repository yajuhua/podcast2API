package io.github.yajuhua.download.commons.progress;

public interface DownloadProgressCallback {
    void onProgressUpdate(String channelUuid, String uuid, Integer status, double downloadProgress,
                          long downloadTimeLeft, double totalSize, double downloadSpeed, String operation,
                          String type,String finalFormat);

}
