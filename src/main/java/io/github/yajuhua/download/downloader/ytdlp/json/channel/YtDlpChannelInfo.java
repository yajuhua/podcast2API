package io.github.yajuhua.download.downloader.ytdlp.json.channel;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YtDlpChannelInfo {
    @SerializedName(value="id")
    private String id;
    @SerializedName(value="channel")
    private String channel;
    @SerializedName(value="channel_id")
    private String channelId;
    @SerializedName(value="title")
    private String title;
    @SerializedName(value="availability")
    private Object availability;
    @SerializedName(value="channel_follower_count")
    private Object channelFollowerCount;
    @SerializedName(value="description")
    private String description;
    @SerializedName(value="tags")
    private List<String> tags;
    @SerializedName(value="thumbnails")
    private List<Thumbnails> thumbnails;
    @SerializedName(value="uploader_id")
    private String uploaderId;
    @SerializedName(value="uploader_url")
    private String uploaderUrl;
    @SerializedName(value="modified_date")
    private Object modifiedDate;
    @SerializedName(value="view_count")
    private Object viewCount;
    @SerializedName(value="playlist_count")
    private Object playlistCount;
    @SerializedName(value="uploader")
    private String uploader;
    @SerializedName(value="channel_url")
    private String channelUrl;
    @SerializedName(value="_type")
    private String type;
    @SerializedName(value="entries")
    private List<Entries> entries;
    @SerializedName(value="extractor_key")
    private String extractorKey;
    @SerializedName(value="extractor")
    private String extractor;
    @SerializedName(value="webpage_url")
    private String webpageUrl;
    @SerializedName(value="original_url")
    private String originalUrl;
    @SerializedName(value="webpage_url_basename")
    private String webpageUrlBasename;
    @SerializedName(value="webpage_url_domain")
    private String webpageUrlDomain;
    @SerializedName(value="release_year")
    private Object releaseYear;
    @SerializedName(value="requested_entries")
    private List<Double> requestedEntries;
    @SerializedName(value="epoch")
    private Double epoch;
    @SerializedName(value="__files_to_move")
    private FilesToMove filesToMove;
    @SerializedName(value="_version")
    private Version version;
}
