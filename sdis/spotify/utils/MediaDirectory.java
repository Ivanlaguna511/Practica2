package sdis.spotify.utils;

import sdis.spotify.media.Media;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe registry for all Media objects available on the server.
 */
public class MediaDirectory {
    private final ConcurrentHashMap<String, Media> directory;

    public MediaDirectory() {
        this.directory = new ConcurrentHashMap<>();
    }

    public void addMedia(String key, Media media) {
        directory.put(key, media);
    }

    public Media getMedia(String key) {
        return directory.get(key);
    }

    public boolean containsMediaKey(String key) {
        return directory.containsKey(key);
    }

    public boolean containsMedia(Media media) {
        return directory.containsValue(media);
    }

    public int size() {
        return directory.size();
    }

    public Set<String> getKeys() {
        return directory.keySet(); // ConcurrentHashMap keySet is safe to iterate
    }
}