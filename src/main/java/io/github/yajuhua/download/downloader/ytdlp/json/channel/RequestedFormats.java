package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestedFormats {
    @SerializedName(value = "format_id")
    private String formatId;
    @SerializedName(value = "format_index")
    private Object formatIndex;
    @SerializedName(value = "url")
    private String url;
    @SerializedName(value = "manifest_url")
    private String manifestUrl;
    @SerializedName(value = "tbr")
    private Double tbr;
    @SerializedName(value = "ext")
    private String ext;
    @SerializedName(value = "fps")
    private Double fps;
    @SerializedName(value = "protocol")
    private String protocol;
    @SerializedName(value = "preference")
    private Object preference;
    @SerializedName(value = "quality")
    private Double quality;
    @SerializedName(value = "has_drm")
    private Boolean hasDrm;
    @SerializedName(value = "width")
    private Double width;
    @SerializedName(value = "height")
    private Double height;
    @SerializedName(value = "vcodec")
    private String vcodec;
    @SerializedName(value = "acodec")
    private String acodec;
    @SerializedName(value = "dynamic_range")
    private String dynamicRange;
    @SerializedName(value = "source_preference")
    private Double sourcePreference;
    @SerializedName(value = "format_note")
    private String formatNote;
    @SerializedName(value = "__needs_testing")
    private Boolean needsTesting;
    @SerializedName(value = "resolution")
    private String resolution;
    @SerializedName(value = "aspect_ratio")
    private Double aspectRatio;
    @SerializedName(value = "http_headers")
    private HttpHeaders httpHeaders;
    @SerializedName(value = "video_ext")
    private String videoExt;
    @SerializedName(value = "audio_ext")
    private String audioExt;
    @SerializedName(value = "abr")
    private Double abr;
    @SerializedName(value = "vbr")
    private Double vbr;
    @SerializedName(value = "format")
    private String format;
    @SerializedName(value = "asr")
    private Double asr;
    @SerializedName(value = "filesize")
    private Double filesize;
    @SerializedName(value = "audio_channels")
    private Double audioChannels;
    @SerializedName(value = "filesize_approx")
    private Double filesizeApprox;
    @SerializedName(value = "language")
    private Object language;
    @SerializedName(value = "language_preference")
    private Double languagePreference;
    @SerializedName(value = "container")
    private String container;
    @SerializedName(value = "downloader_options")
    private DownloaderOptions downloaderOptions;
}
