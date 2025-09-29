package players;

import interfaces.MediaPlayer;
import players.adapters.MediaAdapter;

public class UniversalMediaPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;

    @Override
    public void play(String fileName) {
        if (fileName.endsWith(".mp3")) {
            System.out.println("Playing MP3 file: " + fileName);
        }
        else if (fileName.endsWith(".vlc")) {
            mediaAdapter = new MediaAdapter("vlc");
            mediaAdapter.play(fileName);
        } else if (fileName.endsWith(".mp4")) {
            mediaAdapter = new MediaAdapter("mp4");
            mediaAdapter.play(fileName);
        } else {
            System.out.println("Invalid format: " + fileName);
        }
    }
}