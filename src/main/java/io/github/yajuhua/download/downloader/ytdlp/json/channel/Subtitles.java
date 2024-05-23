package io.github.yajuhua.download.downloader.ytdlp.json.channel;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Subtitles {
    @SerializedName(value="live_chat")
    private List<LiveChat> liveChat;
}
