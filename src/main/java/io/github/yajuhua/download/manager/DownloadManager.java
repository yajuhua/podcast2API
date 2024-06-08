package io.github.yajuhua.download.manager;


import io.github.yajuhua.download.commons.progress.DownloadProgress;
import io.github.yajuhua.download.commons.progress.DownloadProgressCallback;
import io.github.yajuhua.download.downloader.Downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DownloadManager implements Runnable{

    public enum Downloader {
        YtDlp,
        Nm3u8DlRe,
        Aria2
    }
    private int maxThreads;
    private int corePoolSize;
    private int queueSize;
    private List<io.github.yajuhua.download.downloader.Downloader> downloaderList = new ArrayList<>();
    private Set<DownloadProgress> downloadProgresses = new CopyOnWriteArraySet<>();
    private ThreadPoolExecutor pool;

    public DownloadManager() {
        this.maxThreads = 3;
        this.corePoolSize = 3;
        this.queueSize = 4;
    }

    public DownloadManager(int maxThreads, int corePoolSize, int queueSize) {
        this.maxThreads = maxThreads;
        this.corePoolSize = corePoolSize;
        this.queueSize = queueSize;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    /**
     * 添加请求
     * @param request
     */
    public void add(Request request){
       downloaderList.add(request.build());
    }

    /**
     * 开始下载
     */
    public void startDownload() throws Exception{

         pool = new ThreadPoolExecutor(
                corePoolSize,    //核心线程数有3个
                maxThreads,  //最大线程数有5个。   临时线程数=最大线程数-核心线程数=5-3=2
                8,    //临时线程存活的时间8秒。 意思是临时线程8秒没有任务执行，就会被销毁掉。
                TimeUnit.SECONDS,//时间单位（秒）
                new ArrayBlockingQueue<>(this.queueSize), //任务阻塞队列，没有来得及执行的任务在，任务队列中等待
                Executors.defaultThreadFactory(), //用于创建线程的工厂对象
                new ThreadPoolExecutor.CallerRunsPolicy() //拒绝策略
        );

        for (io.github.yajuhua.download.downloader.Downloader downloader : downloaderList) {
            downloader.callback(new DownloadProgressCallback() {
                @Override
                public void onProgressUpdate(String channelUuid, String uuid, Integer status, double downloadProgress, long downloadTimeLeft
                        , double totalSize, double downloadSpeed, String operation, String type, String finalFormat) {
                    DownloadProgress progress = DownloadProgress.builder()
                            .channelUuid(channelUuid)
                            .uuid(uuid)
                            .status(status)
                            .downloadProgress(downloadProgress)
                            .downloadTimeLeft(downloadTimeLeft)
                            .totalSize(totalSize)
                            .downloadSpeed(downloadSpeed)
                            .operation(operation)
                            .type(type)
                            .finalFormat(finalFormat)
                            .build();
                    if (downloadProgresses.contains(progress)){
                        downloadProgresses.remove(progress);
                    }
                    downloadProgresses.add(progress);
                }
            });
            pool.execute((Runnable) downloader);//加入线程池
            Thread.sleep(2000);//等待两秒，
        }
        pool.shutdown();;
    }

    @Override
    public void run() {
        try {
            startDownload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回所有下载进度
     *
     * @return
     * @throws Exception
     */
    public synchronized Set<DownloadProgress> allDownloadProgress(){
        return this.downloadProgresses;
    }

    /**
     * 结束所有下载
     */
    public void killAll() throws Exception{
        for (io.github.yajuhua.download.downloader.Downloader downloader : downloaderList) {
            downloader.kill();
        }
        //取消所有队列
        pool.shutdownNow();
        pool.purge();
    }

    /**
     * 根据uuid结束下载
     */
    public void killByUuid(String uuid) throws Exception{
        List<io.github.yajuhua.download.downloader.Downloader> collect =
                downloaderList.stream().filter(downloader -> downloader.getUuid()
                        .equals(uuid)).collect(Collectors.toList());
        for (io.github.yajuhua.download.downloader.Downloader downloader : collect) {
            downloader.kill();
        }
    }

    /**
     * 根据频道uuid结束下载
     */
    public void killByChannelUuid(String channelUuid) throws Exception{
        List<io.github.yajuhua.download.downloader.Downloader> collect =
                downloaderList.stream().filter(downloader -> downloader.getChannelUuid()
                        .equals(channelUuid)).collect(Collectors.toList());
        for (io.github.yajuhua.download.downloader.Downloader downloader : collect) {
            downloader.kill();
            Thread.sleep(300);
        }
    }

}
