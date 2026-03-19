package spotify.stream;

import spotify.rmi.common.SpotifyClient;
import spotify.utils.Utils;

public class ServerStream implements Runnable {
    private final String fileToSend;
    private int serverSocketPort;
    private final SpotifyClient client;

    public ServerStream(String fileToSend, SpotifyClient client) {
        this.fileToSend = fileToSend;
        this.client = client;
    }

    public int getServerSocketPort() {
        return serverSocketPort;
    }

    @Override
    public void run() {
        try (java.net.ServerSocket servsock = new java.net.ServerSocket(0)) {
            this.serverSocketPort = servsock.getLocalPort();
            System.out.println("SERVER STREAM: Listening port " + this.serverSocketPort);
            
            try (
                java.net.Socket sock = servsock.accept();
                java.io.OutputStream os = sock.getOutputStream();
                java.io.FileInputStream fis = new java.io.FileInputStream(fileToSend);
                java.io.BufferedInputStream bis = new java.io.BufferedInputStream(fis)
            ) {
                System.out.println("SERVER STREAM: ¡Client connected! Opening Imput Stream of MP3...");

                String logFile = spotify.media.Globals.LOG_PATH + "streams.txt";
                Utils.logMsg(logFile, Utils.nowDate() + " [STREAM] Streaming to: " + sock.getInetAddress());

                byte[] buffer = new byte[spotify.media.Globals.TX_PACKET_SIZE_BYTES];
                int bytesRead;
                int totalBytes = 0;

                while ((bytesRead = bis.read(buffer)) > 0) {
                    os.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }
                
                os.flush();
                System.out.println("SERVER STREAM: End of transmission. Total sent: " + totalBytes + " bytes.");

                Utils.logMsg(logFile, Utils.nowDate() + " [STREAM] Stream finished. Tx Bytes: " + totalBytes);

            } catch (Exception e) {
                System.err.println("SERVER STREAM [CRASH]: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                
                Utils.logMsg(spotify.media.Globals.LOG_PATH + "streams.txt", Utils.nowDate() + " [ERROR] Stream crashed: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (java.io.IOException e) {
            System.err.println("SERVER STREAM ERROR OPENING SOCKET: " + e.getMessage());
        }
    }
}