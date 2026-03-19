package spotify.media;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GraphicsEnvironment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Serializable DTO representing a Media object (Song, Podcast, etc.).
 */
public class Media implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String internalName;
    private int likes;
    private boolean isAdultContent;
    private final List<String> comments;
    private double score;
    private int numVotes;
    
    private ImageIcon cover;

    public Media(String name) {
        this.name = name;
        this.internalName = name.trim().replaceAll("[^a-zA-Z0-9]+", "_").toLowerCase();
        this.likes = 0;
        this.isAdultContent = false;
        this.comments = new ArrayList<>();
        this.score = 0.0;
        this.numVotes = 0;
        this.cover = null;
    }

    // Getters
    public String getName() { return name; }
    public String getInternalName() { return internalName; }
    public int getLikes() { return likes; }
    public boolean isAdultContent() { return isAdultContent; }
    public List<String> getComments() { return comments; }
    public double getScore() { return score; }
    public ImageIcon getCover() { return cover; }

    // Setters & Modifiers
    public void addLike() { this.likes++; }
    public void tagAdultContent(boolean flag) { this.isAdultContent = flag; }
    public void addComment(String comment) { this.comments.add(comment); }
    
    public synchronized void addScore(double newScore) {
        this.score = ((this.score * this.numVotes) + newScore) / (++this.numVotes);
    }

    public void loadCover(String path) {
        try {
            this.cover = new ImageIcon(ImageIO.read(new File(path)));
        } catch (Exception e) {
            System.err.println("SERVER: No se pudo cargar la carátula físicamente -> " + e.getMessage());
        }
    }
    
    public void setCover(ImageIcon img) {
        this.cover = img;
    }

    public void showCover() {
        if (cover == null || GraphicsEnvironment.isHeadless()) return;
        
        JFrame frame = new JFrame(this.name);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.add(new JPanel().add(new JLabel(cover)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public String toString() {
        showCover(); 
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n© ").append(name).append(isAdultContent ? " [+18]" : " [AP]");
        sb.append(String.format("\n %.2f (%d votes)", score, numVotes));
        sb.append("\n ").append(likes);
        sb.append("\n ").append(comments.size()).append(" comments:");
        
        for (int i = 0; i < comments.size(); i++) {
            sb.append("\n    #").append(i + 1).append(": ").append(comments.get(i));
        }
        return sb.toString();
    }
}