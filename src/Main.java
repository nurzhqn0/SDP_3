import players.UniversalMediaPlayer;

public class Main {

    public static void main(String[] args) {
        UniversalMediaPlayer player = new UniversalMediaPlayer();

        System.out.println("=== Media Player Demo ===\n");

        player.play("song.mp3");
        player.play("movie.mp4");
        player.play("video.vlc");
        player.play("unknown.avi");
    }
}