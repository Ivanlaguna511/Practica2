package spotify.media;

import java.awt.Desktop;
import java.io.File;

public class MediaPlayer implements Runnable {
    private final String filePath;
    private final int startingDelayMs;

    public MediaPlayer(String execCommand, String filePath, int startingDelayMs) {
        this.filePath = filePath;
        this.startingDelayMs = startingDelayMs;
    }

    @Override
    public void run() {
        try {
            System.out.println("MUSIC PLAYER: Waiting " + (startingDelayMs / 1000) + "s for the song download...");
            Thread.sleep(startingDelayMs);
            
            File mediaFile = new File(filePath);
            
            if (mediaFile.exists() && mediaFile.length() > 0) {
                System.out.println("MUSIC PLAYER: Launching music-> " + mediaFile.getName());
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(mediaFile);
                } else {
                    System.err.println("MUSIC PLAYER: Your PC don't support automatic play.");
                }
            } else {
                System.err.println("PLAYER: The file don't exists or has 0 bytes.");
            }
            
            Thread.sleep(60000); 
            
        } catch (Exception e) {
            System.err.println("MUSIC PLAYER ERROR: " + e.getMessage());
        }
    }
}