package spotify.stream;

import spotify.media.Globals;
import spotify.media.Media;

import java.io.*;
import java.net.Socket;

public class ClientStream implements Runnable {
    private final String serverHost;
    private final int serverPort;
    private final String fileToReceive;

    public ClientStream(Media media, String serverHost, int serverPort, Thread player) {
        this.fileToReceive = Globals.PATH_DESTINATION + media.getName() + Globals.FILE_EXTENSION;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        File file = new File(fileToReceive);
        file.getParentFile().mkdirs();

        try (
            Socket sock = new Socket(serverHost, serverPort);
            InputStream is = sock.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            byte[] buffer = new byte[Globals.TX_PACKET_SIZE_BYTES];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) > 0) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();

        } catch (IOException e) {
            System.err.println("CLIENT ERROR STREAMING: " + e.getMessage());
        }
    }
}