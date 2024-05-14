package spotify.media;
import java.io.IOException;
/**
 * Runnable class MediaPlayer 2024
 * @author hector
 */
public class MediaPlayer implements Runnable{
    private String exec_command;
    private String file_path;
    private int starting_delay;
    public MediaPlayer(String exec_command, String file_path, int starting_delay){
        this.exec_command = exec_command;
        this.file_path = file_path;
        this.starting_delay = starting_delay;
    }
    public void run(){
        try {
            System.err.println("** Launching "+exec_command+" w/ delay = " +
                    starting_delay / 1000 + " seg");
            Thread.sleep(starting_delay);
            ProcessBuilder pb = new ProcessBuilder(exec_command, file_path);
            Process start = pb.start();
            start.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}