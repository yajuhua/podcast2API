package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DownloaderOptions {
    @SerializedName(value="http_chunk_size")
    private Double httpChunkSize;
}
