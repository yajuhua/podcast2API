package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Fragments {
    @SerializedName(value="url")
    private String url;
    @SerializedName(value="duration")
    private Double duration;
}
