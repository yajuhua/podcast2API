package org.podcast2;

import java.util.List;

public interface Podcast2 {
    List<String> items();

    String channel();

    String latestItem();
}