package io.github.yajuhua.download.downloader.ytdlp.json.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Version {
    @SerializedName(value="version")
    private String version;
    @SerializedName(value="current_git_head")
    private Object currentGitHead;
    @SerializedName(value="release_git_head")
    private String releaseGitHead;
    @SerializedName(value="repository")
    private String repository;
}
