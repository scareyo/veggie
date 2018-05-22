package xyz.scarey.veggie.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import sx.blah.discord.handle.obj.IGuild;

public class GuildMusicManager {

    private AudioPlayer player;
    private TrackScheduler scheduler;

    public GuildMusicManager(AudioPlayerManager manager, IGuild guild) {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player, guild);
        player.addListener(scheduler);
    }

    public AudioProvider getAudioProvider() {
        return new AudioProvider(player);
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }
}
