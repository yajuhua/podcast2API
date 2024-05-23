package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Thumbnails {
    @SerializedName(value="url")
    private String url;
    @SerializedName(value="height")
    private Integer height;
    @SerializedName(value="width")
    private Integer width;
    @SerializedName(value="id")
    private String id;
    @SerializedName(value="resolution")
    private String resolution;
    @SerializedName(value="preference")
    private Integer preference;
}
