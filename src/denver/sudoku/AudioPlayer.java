package denver.sudoku;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {

    private static AudioPlayer instance;

    private MediaPlayer player;
    private int playerResourceId;

    public void start(Context context, int resourceId) {
        if (context == null) {
            throw new NullPointerException("context==null");
        }

        if (this.player != null) {
            if (this.playerResourceId == resourceId) {
                return;
            }
            this.stop();
        }

        this.player = MediaPlayer.create(context, resourceId);
        this.player.setLooping(true);
        this.player.start();
        this.playerResourceId = resourceId;
    }

    public synchronized void stop() {
        final MediaPlayer player = this.player;
        this.player = null;

        if (player != null) {
            try {
                player.stop();
            } finally {
                player.release();
            }
        }
    }

    public static synchronized AudioPlayer getAppInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }
}
