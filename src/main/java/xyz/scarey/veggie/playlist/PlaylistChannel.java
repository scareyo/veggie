package xyz.scarey.veggie.playlist;

import java.util.LinkedList;
import java.util.Queue;

public class PlaylistChannel {

    private long guildId;
    private long channelId;
    private Queue<Song> queue = new LinkedList<Song>();

    public PlaylistChannel(long guildId, long channelId) {
        this.guildId = guildId;
        this.channelId = channelId;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public Queue<Song> getQueue() {
        return queue;
    }
}
