package spotify.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String nowDate() {
        return LocalDateTime.now().format(formatter);
    }

    public static void logMsg(String logFilePath, String message) {
        try {
            Files.createDirectories(Paths.get(logFilePath).getParent());
            String formattedMessage = message + System.lineSeparator();
            Files.write(Paths.get(logFilePath), formattedMessage.getBytes(), 
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Logger Failed: Could not write to " + logFilePath);
        }
    }
}