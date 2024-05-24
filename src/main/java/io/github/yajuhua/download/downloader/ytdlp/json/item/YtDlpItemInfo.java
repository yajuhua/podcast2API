package io.github.yajuhua.download.downloader.ytdlp.json.item;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YtDlpItemInfo {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("formats")
    private List<FormatsDTO> formats;
    @SerializedName("thumbnails")
    private List<ThumbnailsDTO> thumbnails;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("description")
    private String description;
    @SerializedName("channel_id")
    private String channelId;
    @SerializedName("channel_url")
    private String channelUrl;
    @SerializedName("duration")
    private Double duration;
    @SerializedName("view_count")
    private Double viewCount;
    @SerializedName("average_rating")
    private Object averageRating;
    @SerializedName("age_limit")
    private Double ageLimit;
    @SerializedName("webpage_url")
    private String webpageUrl;
    @SerializedName("categories")
    private List<String> categories;
    @SerializedName("tags")
    private List<?> tags;
    @SerializedName("playable_in_embed")
    private Boolean playableInEmbed;
    @SerializedName("live_status")
    private String liveStatus;
    @SerializedName("release_timestamp")
    private Object releaseTimestamp;
    @SerializedName("_format_sort_fields")
    private List<String> formatSortFields;
    @SerializedName("automatic_captions")
    private AutomaticCaptionsDTO automaticCaptions;
    @SerializedName("subtitles")
    private SubtitlesDTO subtitles;
    @SerializedName("comment_count")
    private Double commentCount;
    @SerializedName("chapters")
    private Object chapters;
    @SerializedName("heatmap")
    private Object heatmap;
    @SerializedName("like_count")
    private Double likeCount;
    @SerializedName("channel")
    private String channel;
    @SerializedName("channel_follower_count")
    private Double channelFollowerCount;
    @SerializedName("channel_is_verified")
    private Boolean channelIsVerified;
    @SerializedName("uploader")
    private String uploader;
    @SerializedName("uploader_id")
    private String uploaderId;
    @SerializedName("uploader_url")
    private String uploaderUrl;
    @SerializedName("upload_date")
    private String uploadDate;
    @SerializedName("availability")
    private String availability;
    @SerializedName("original_url")
    private String originalUrl;
    @SerializedName("webpage_url_basename")
    private String webpageUrlBasename;
    @SerializedName("webpage_url_domain")
    private String webpageUrlDomain;
    @SerializedName("extractor")
    private String extractor;
    @SerializedName("extractor_key")
    private String extractorKey;
    @SerializedName("playlist")
    private Object playlist;
    @SerializedName("playlist_index")
    private Object playlistIndex;
    @SerializedName("display_id")
    private String displayId;
    @SerializedName("fulltitle")
    private String fulltitle;
    @SerializedName("duration_string")
    private String durationString;
    @SerializedName("release_year")
    private Object releaseYear;
    @SerializedName("is_live")
    private Boolean isLive;
    @SerializedName("was_live")
    private Boolean wasLive;
    @SerializedName("requested_subtitles")
    private Object requestedSubtitles;
    @SerializedName("_has_drm")
    private Object hasDrm;
    @SerializedName("epoch")
    private Double epoch;
    @SerializedName("requested_downloads")
    private List<RequestedDownloadsDTO> requestedDownloads;
    @SerializedName("requested_formats")
    private List<RequestedFormatsDTO> requestedFormats;
    @SerializedName("format")
    private String format;
    @SerializedName("format_id")
    private String formatId;
    @SerializedName("ext")
    private String ext;
    @SerializedName("protocol")
    private String protocol;
    @SerializedName("language")
    private Object language;
    @SerializedName("format_note")
    private String formatNote;
    @SerializedName("filesize_approx")
    private Double filesizeApprox;
    @SerializedName("tbr")
    private Double tbr;
    @SerializedName("width")
    private Double width;
    @SerializedName("height")
    private Double height;
    @SerializedName("resolution")
    private String resolution;
    @SerializedName("fps")
    private Double fps;
    @SerializedName("dynamic_range")
    private String dynamicRange;
    @SerializedName("vcodec")
    private String vcodec;
    @SerializedName("vbr")
    private Double vbr;
    @SerializedName("stretched_ratio")
    private Object stretchedRatio;
    @SerializedName("aspect_ratio")
    private Double aspectRatio;
    @SerializedName("acodec")
    private String acodec;
    @SerializedName("abr")
    private Double abr;
    @SerializedName("asr")
    private Double asr;
    @SerializedName("audio_channels")
    private Double audioChannels;
    @SerializedName("_type")
    private String type;
    @SerializedName("_version")
    private VersionDTO version;

    @NoArgsConstructor
    @Data
    public static class AutomaticCaptionsDTO {
    }

    @NoArgsConstructor
    @Data
    public static class SubtitlesDTO {
    }

    @NoArgsConstructor
    @Data
    public static class VersionDTO {
        @SerializedName("version")
        private String version;
        @SerializedName("current_git_head")
        private Object currentGitHead;
        @SerializedName("release_git_head")
        private String releaseGitHead;
        @SerializedName("repository")
        private String repository;
    }

    @NoArgsConstructor
    @Data
    public static class FormatsDTO {
        @SerializedName("format_id")
        private String formatId;
        @SerializedName("format_note")
        private String formatNote;
        @SerializedName("ext")
        private String ext;
        @SerializedName("protocol")
        private String protocol;
        @SerializedName("acodec")
        private String acodec;
        @SerializedName("vcodec")
        private String vcodec;
        @SerializedName("url")
        private String url;
        @SerializedName("width")
        private Double width;
        @SerializedName("height")
        private Double height;
        @SerializedName("fps")
        private Double fps;
        @SerializedName("rows")
        private Double rows;
        @SerializedName("columns")
        private Double columns;
        @SerializedName("fragments")
        private List<FragmentsDTO> fragments;
        @SerializedName("resolution")
        private String resolution;
        @SerializedName("aspect_ratio")
        private Double aspectRatio;
        @SerializedName("filesize_approx")
        private Object filesizeApprox;
        @SerializedName("http_headers")
        private FormatsDTO.HttpHeadersDTO httpHeaders;
        @SerializedName("audio_ext")
        private String audioExt;
        @SerializedName("video_ext")
        private String videoExt;
        @SerializedName("vbr")
        private Double vbr;
        @SerializedName("abr")
        private Double abr;
        @SerializedName("tbr")
        private Object tbr;
        @SerializedName("format")
        private String format;
        @SerializedName("format_index")
        private Object formatIndex;
        @SerializedName("manifest_url")
        private String manifestUrl;
        @SerializedName("language")
        private Object language;
        @SerializedName("preference")
        private Object preference;
        @SerializedName("quality")
        private Double quality;
        @SerializedName("has_drm")
        private Boolean hasDrm;
        @SerializedName("source_preference")
        private Double sourcePreference;
        @SerializedName("asr")
        private Double asr;
        @SerializedName("filesize")
        private Double filesize;
        @SerializedName("audio_channels")
        private Double audioChannels;
        @SerializedName("language_preference")
        private Double languagePreference;
        @SerializedName("dynamic_range")
        private Object dynamicRange;
        @SerializedName("container")
        private String container;
        @SerializedName("downloader_options")
        private FormatsDTO.DownloaderOptionsDTO downloaderOptions;

        @NoArgsConstructor
        @Data
        public static class HttpHeadersDTO {
            @SerializedName("User-Agent")
            private String userAgent;
            @SerializedName("Accept")
            private String accept;
            @SerializedName("Accept-Language")
            private String acceptLanguage;
            @SerializedName("Sec-Fetch-Mode")
            private String secFetchMode;
        }

        @NoArgsConstructor
        @Data
        public static class DownloaderOptionsDTO {
            @SerializedName("http_chunk_size")
            private Double httpChunkSize;
        }

        @NoArgsConstructor
        @Data
        public static class FragmentsDTO {
            @SerializedName("url")
            private String url;
            @SerializedName("duration")
            private Double duration;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ThumbnailsDTO {
        @SerializedName("url")
        private String url;
        @SerializedName("preference")
        private Double preference;
        @SerializedName("id")
        private String id;
        @SerializedName("height")
        private Double height;
        @SerializedName("width")
        private Double width;
        @SerializedName("resolution")
        private String resolution;
    }

    @NoArgsConstructor
    @Data
    public static class RequestedDownloadsDTO {
        @SerializedName("requested_formats")
        private List<RequestedFormatsDTO> requestedFormats;
        @SerializedName("format")
        private String format;
        @SerializedName("format_id")
        private String formatId;
        @SerializedName("ext")
        private String ext;
        @SerializedName("protocol")
        private String protocol;
        @SerializedName("format_note")
        private String formatNote;
        @SerializedName("filesize_approx")
        private Double filesizeApprox;
        @SerializedName("tbr")
        private Double tbr;
        @SerializedName("width")
        private Double width;
        @SerializedName("height")
        private Double height;
        @SerializedName("resolution")
        private String resolution;
        @SerializedName("fps")
        private Double fps;
        @SerializedName("dynamic_range")
        private String dynamicRange;
        @SerializedName("vcodec")
        private String vcodec;
        @SerializedName("vbr")
        private Double vbr;
        @SerializedName("aspect_ratio")
        private Double aspectRatio;
        @SerializedName("acodec")
        private String acodec;
        @SerializedName("abr")
        private Double abr;
        @SerializedName("asr")
        private Double asr;
        @SerializedName("audio_channels")
        private Double audioChannels;
        @SerializedName("_filename")
        private String _filename;
        @SerializedName("filename")
        private String filename;
        @SerializedName("__write_download_archive")
        private Boolean writeDownloadArchive;

        @NoArgsConstructor
        @Data
        public static class RequestedFormatsDTO {
            @SerializedName("format_id")
            private String formatId;
            @SerializedName("format_index")
            private Object formatIndex;
            @SerializedName("url")
            private String url;
            @SerializedName("manifest_url")
            private String manifestUrl;
            @SerializedName("tbr")
            private Double tbr;
            @SerializedName("ext")
            private String ext;
            @SerializedName("fps")
            private Double fps;
            @SerializedName("protocol")
            private String protocol;
            @SerializedName("preference")
            private Object preference;
            @SerializedName("quality")
            private Double quality;
            @SerializedName("has_drm")
            private Boolean hasDrm;
            @SerializedName("width")
            private Double width;
            @SerializedName("height")
            private Double height;
            @SerializedName("vcodec")
            private String vcodec;
            @SerializedName("acodec")
            private String acodec;
            @SerializedName("dynamic_range")
            private String dynamicRange;
            @SerializedName("source_preference")
            private Double sourcePreference;
            @SerializedName("format_note")
            private String formatNote;
            @SerializedName("resolution")
            private String resolution;
            @SerializedName("aspect_ratio")
            private Double aspectRatio;
            @SerializedName("http_headers")
            private RequestedDownloadsDTO.RequestedFormatsDTO.HttpHeadersDTO httpHeaders;
            @SerializedName("video_ext")
            private String videoExt;
            @SerializedName("audio_ext")
            private String audioExt;
            @SerializedName("abr")
            private Double abr;
            @SerializedName("vbr")
            private Double vbr;
            @SerializedName("format")
            private String format;
            @SerializedName("asr")
            private Double asr;
            @SerializedName("filesize")
            private Double filesize;
            @SerializedName("audio_channels")
            private Double audioChannels;
            @SerializedName("filesize_approx")
            private Double filesizeApprox;
            @SerializedName("language")
            private Object language;
            @SerializedName("language_preference")
            private Double languagePreference;
            @SerializedName("container")
            private String container;
            @SerializedName("downloader_options")
            private RequestedDownloadsDTO.RequestedFormatsDTO.DownloaderOptionsDTO downloaderOptions;

            @NoArgsConstructor
            @Data
            public static class HttpHeadersDTO {
                @SerializedName("User-Agent")
                private String userAgent;
                @SerializedName("Accept")
                private String accept;
                @SerializedName("Accept-Language")
                private String acceptLanguage;
                @SerializedName("Sec-Fetch-Mode")
                private String secFetchMode;
            }

            @NoArgsConstructor
            @Data
            public static class DownloaderOptionsDTO {
                @SerializedName("http_chunk_size")
                private Double httpChunkSize;
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class RequestedFormatsDTO {
        @SerializedName("format_id")
        private String formatId;
        @SerializedName("format_index")
        private Object formatIndex;
        @SerializedName("url")
        private String url;
        @SerializedName("manifest_url")
        private String manifestUrl;
        @SerializedName("tbr")
        private Double tbr;
        @SerializedName("ext")
        private String ext;
        @SerializedName("fps")
        private Double fps;
        @SerializedName("protocol")
        private String protocol;
        @SerializedName("preference")
        private Object preference;
        @SerializedName("quality")
        private Double quality;
        @SerializedName("has_drm")
        private Boolean hasDrm;
        @SerializedName("width")
        private Double width;
        @SerializedName("height")
        private Double height;
        @SerializedName("vcodec")
        private String vcodec;
        @SerializedName("acodec")
        private String acodec;
        @SerializedName("dynamic_range")
        private String dynamicRange;
        @SerializedName("source_preference")
        private Double sourcePreference;
        @SerializedName("format_note")
        private String formatNote;
        @SerializedName("resolution")
        private String resolution;
        @SerializedName("aspect_ratio")
        private Double aspectRatio;
        @SerializedName("http_headers")
        private RequestedFormatsDTO.HttpHeadersDTO httpHeaders;
        @SerializedName("video_ext")
        private String videoExt;
        @SerializedName("audio_ext")
        private String audioExt;
        @SerializedName("abr")
        private Double abr;
        @SerializedName("vbr")
        private Double vbr;
        @SerializedName("format")
        private String format;
        @SerializedName("asr")
        private Double asr;
        @SerializedName("filesize")
        private Double filesize;
        @SerializedName("audio_channels")
        private Double audioChannels;
        @SerializedName("filesize_approx")
        private Double filesizeApprox;
        @SerializedName("language")
        private Object language;
        @SerializedName("language_preference")
        private Double languagePreference;
        @SerializedName("container")
        private String container;
        @SerializedName("downloader_options")
        private RequestedFormatsDTO.DownloaderOptionsDTO downloaderOptions;

        @NoArgsConstructor
        @Data
        public static class HttpHeadersDTO {
            @SerializedName("User-Agent")
            private String userAgent;
            @SerializedName("Accept")
            private String accept;
            @SerializedName("Accept-Language")
            private String acceptLanguage;
            @SerializedName("Sec-Fetch-Mode")
            private String secFetchMode;
        }

        @NoArgsConstructor
        @Data
        public static class DownloaderOptionsDTO {
            @SerializedName("http_chunk_size")
            private Double httpChunkSize;
        }
    }
}
