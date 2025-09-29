package players.adapters;

import interfaces.AdvancedMediaPlayer;
import interfaces.MediaPlayer;
import players.Mp4Player;
import players.VlcPlayer;

public class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedPlayer;

    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedPlayer = new VlcPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String fileName) {
        if (fileName.endsWith(".vlc")) {
            advancedPlayer.playVlc(fileName);
        } else if (fileName.endsWith(".mp4")) {
            advancedPlayer.playMp4(fileName);
        }
    }
}