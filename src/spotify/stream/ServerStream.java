package spotify.stream;

import spotify.rmi.common.SpotifyClient;

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
            
            try (
                java.net.Socket sock = servsock.accept();
                java.io.OutputStream os = sock.getOutputStream();
                java.io.FileInputStream fis = new java.io.FileInputStream(fileToSend);
                java.io.BufferedInputStream bis = new java.io.BufferedInputStream(fis)
            ) {

                byte[] buffer = new byte[spotify.media.Globals.TX_PACKET_SIZE_BYTES];
                int bytesRead;
                int totalBytes = 0;

                while ((bytesRead = bis.read(buffer)) > 0) {
                    os.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }
                
                os.flush();

            } catch (Exception e) {
                System.err.println("SERVER STREAM [CRASH]: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        } catch (java.io.IOException e) {
            System.err.println("SERVER STREAM ERROR OPENING SOCKET: " + e.getMessage());
        }
    }
}