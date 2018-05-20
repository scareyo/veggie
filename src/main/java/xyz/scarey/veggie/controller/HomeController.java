package xyz.scarey.veggie.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.IVoiceState;
import xyz.scarey.veggie.bot.VeggieBot;
import xyz.scarey.veggie.playlist.PlaylistChannel;
import xyz.scarey.veggie.playlist.Song;

import java.util.*;

@Controller
public class HomeController {

    private HashMap<Long, PlaylistChannel> channels = new LinkedHashMap<>();

    @GetMapping("/")
    public String index(OAuth2Authentication auth, Model model) {
        IVoiceChannel voiceChannel = getVoiceChannel(auth);

        if (voiceChannel == null) {
            return "index";
        }

        PlaylistChannel playlistChannel = channels.get(voiceChannel.getGuild().getLongID());
        model.addAttribute("guild", voiceChannel.getGuild().getName());
        model.addAttribute("channel", voiceChannel.getName());

        if (channels.containsKey(voiceChannel.getGuild().getLongID())) {
            // Guild already has a queue in another playlistChannel.
            if (playlistChannel.getChannelId() != voiceChannel.getLongID()) {
                return "index";
            }
            System.out.println("add attribute");
            model.addAttribute("queue", playlistChannel.getQueue());
        } else {
            channels.put(voiceChannel.getGuild().getLongID(), new PlaylistChannel(voiceChannel.getGuild().getLongID(), voiceChannel.getLongID()));
        }
        model.addAttribute("song", new Song());
        return "channel_queue";
    }

    @PostMapping("/")
    public String addSong(OAuth2Authentication auth, @ModelAttribute Song song) {
        IVoiceChannel voiceChannel = getVoiceChannel(auth);
        Queue<Song> queue = channels.get(voiceChannel.getGuild().getLongID()).getQueue();
        System.out.println("add song");
        queue.add(song);
        return "redirect:/";
    }

    private IVoiceChannel getVoiceChannel(OAuth2Authentication auth) {
        IDiscordClient client = VeggieBot.getInstance().getClient();
        LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) auth.getUserAuthentication().getDetails();
        String id = details.get("id").toString();

        IUser user = client.getUserByID(Long.parseLong(id));
        Collection<IVoiceState> voiceStates = user.getVoiceStates().values();

        for (IVoiceState i : voiceStates) {
            if (i.getChannel() != null) {
                return i.getChannel();
            }
        }

        return null;
    }

    private List<Song> getList(Queue<Song> queue) {
        List<Song> list = new ArrayList<>();
        queue.forEach(list::add);
        return list;
    }
}
