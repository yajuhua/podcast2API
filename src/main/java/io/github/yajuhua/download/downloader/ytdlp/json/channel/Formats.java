package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Formats {
    @SerializedName(value = "format_id")
    private String formatId;
    @SerializedName(value = "format_note")
    private String formatNote;
    @SerializedName(value = "ext")
    private String ext;
    @SerializedName(value = "protocol")
    private String protocol;
    @SerializedName(value = "acodec")
    private String acodec;
    @SerializedName(value = "vcodec")
    private String vcodec;
    @SerializedName(value = "url")
    private String url;
    @SerializedName(value = "width")
    private Double width;
    @SerializedName(value = "height")
    private Double height;
    @SerializedName(value = "fps")
    private Double fps;
    @SerializedName(value = "rows")
    private Double rows;
    @SerializedName(value = "columns")
    private Double columns;
    @SerializedName(value = "fragments")
    private List<Fragments> fragments;
    @SerializedName(value = "resolution")
    private String resolution;
    @SerializedName(value = "aspect_ratio")
    private Double aspectRatio;
    @SerializedName(value = "filesize_approx")
    private Object filesizeApprox;
    @SerializedName(value = "http_headers")
    private HttpHeaders httpHeaders;
    @SerializedName(value = "audio_ext")
    private String audioExt;
    @SerializedName(value = "video_ext")
    private String videoExt;
    @SerializedName(value = "vbr")
    private Double vbr;
    @SerializedName(value = "abr")
    private Double abr;
    @SerializedName(value = "tbr")
    private Object tbr;
    @SerializedName(value = "format")
    private String format;
    @SerializedName(value = "format_index")
    private Object formatIndex;
    @SerializedName(value = "manifest_url")
    private String manifestUrl;
    @SerializedName(value = "language")
    private Object language;
    @SerializedName(value = "preference")
    private Object preference;
    @SerializedName(value = "quality")
    private Double quality;
    @SerializedName(value = "has_drm")
    private Boolean hasDrm;
    @SerializedName(value = "source_preference")
    private Double sourcePreference;
    @SerializedName(value = "__needs_testing")
    private Boolean needsTesting;
    @SerializedName(value = "asr")
    private Double asr;
    @SerializedName(value = "filesize")
    private Double filesize;
    @SerializedName(value = "audio_channels")
    private Double audioChannels;
    @SerializedName(value = "language_preference")
    private Double languagePreference;
    @SerializedName(value = "dynamic_range")
    private Object dynamicRange;
    @SerializedName(value = "container")
    private String container;
    @SerializedName(value = "downloader_options")
    private DownloaderOptions downloaderOptions;
}
