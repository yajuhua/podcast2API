package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LiveChat {
    @SerializedName(value="url")
    private String url;
    @SerializedName(value="video_id")
    private String videoId;
    @SerializedName(value="ext")
    private String ext;
    @SerializedName(value="protocol")
    private String protocol;
}
