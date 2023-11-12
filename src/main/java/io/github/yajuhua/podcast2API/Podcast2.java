package io.github.yajuhua.podcast2API;

import java.util.List;

public interface Podcast2 {
    List<String> items();

    String channel();

    String latestItem();
}