package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HttpHeaders {
    @SerializedName(value="User-Agent")
    private String userAgent;
    @SerializedName(value="Accept")
    private String accept;
    @SerializedName(value="Accept-Language")
    private String acceptLanguage;
    @SerializedName(value="Sec-Fetch-Mode")
    private String secFetchMode;
}
