package spotify.utils;

import spotify.media.Media;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe registry for Media objects.
 */
public class MediaDirectory {
    private final ConcurrentHashMap<String, Media> directory;

    public MediaDirectory() {
        this.directory = new ConcurrentHashMap<>();
    }

    public void addMedia(String key, Media media) {
        this.directory.put(key, media);
    }

    public Media getMedia(String key) {
        return this.directory.get(key);
    }

    public boolean containsMediaKey(String key) {
        return this.directory.containsKey(key);
    }

    public int size() {
        return this.directory.size();
    }

    public Set<String> keySet() {
        return this.directory.keySet();
    }

    // Fixed: Properly retrieves a random media object
    public Media getRandomMedia(int index) {
        if (directory.isEmpty()) return null;
        List<Media> mediaList = new ArrayList<>(directory.values());
        if (index >= 0 && index < mediaList.size()) {
            return mediaList.get(index);
        }
        return null;
    }
}