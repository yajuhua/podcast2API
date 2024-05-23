package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class RequestedDownloads {
    @SerializedName(value = "requested_formats")
    private List<RequestedFormats> requestedFormats;
    @SerializedName(value = "format")
    private String format;
    @SerializedName(value = "format_id")
    private String formatId;
    @SerializedName(value = "ext")
    private String ext;
    @SerializedName(value = "protocol")
    private String protocol;
    @SerializedName(value = "format_note")
    private String formatNote;
    @SerializedName(value = "filesize_approx")
    private Double filesizeApprox;
    @SerializedName(value = "tbr")
    private Double tbr;
    @SerializedName(value = "width")
    private Double width;
    @SerializedName(value = "height")
    private Double height;
    @SerializedName(value = "resolution")
    private String resolution;
    @SerializedName(value = "fps")
    private Double fps;
    @SerializedName(value = "dynamic_range")
    private String dynamicRange;
    @SerializedName(value = "vcodec")
    private String vcodec;
    @SerializedName(value = "vbr")
    private Double vbr;
    @SerializedName(value = "aspect_ratio")
    private Double aspectRatio;
    @SerializedName(value = "acodec")
    private String acodec;
    @SerializedName(value = "abr")
    private Double abr;
    @SerializedName(value = "asr")
    private Double asr;
    @SerializedName(value = "audio_channels")
    private Double audioChannels;
    @SerializedName(value = "filename")
    private String filename;
    @SerializedName(value = "__write_download_archive")
    private Boolean writeDownloadArchive;
}
