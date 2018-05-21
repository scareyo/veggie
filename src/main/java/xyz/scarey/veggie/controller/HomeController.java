package xyz.scarey.veggie.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.IVoiceState;
import xyz.scarey.veggie.bot.VeggieBot;

import java.util.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(OAuth2Authentication auth, Model model) {
        IVoiceChannel voiceChannel = getVoiceChannel(auth);

        if (voiceChannel == null) {
            return "index";
        }

        model.addAttribute("guild", voiceChannel.getGuild().getName());
        model.addAttribute("channel", voiceChannel.getName());
        if (VeggieBot.getInstance().getGuildMusicManager(voiceChannel.getGuild()).getPlayer().getPlayingTrack() != null) {
            model.addAttribute("current", VeggieBot.getInstance().getGuildMusicManager(voiceChannel.getGuild()).getPlayer().getPlayingTrack().getInfo().title);
        }
        model.addAttribute("queue", VeggieBot.getInstance().getGuildMusicManager(voiceChannel.getGuild()).getScheduler().getQueue());

        return "channel_queue";
    }

    @PostMapping("/")
    public String addSong(@RequestParam(value="url") String url, OAuth2Authentication auth) {
        IVoiceChannel voiceChannel = getVoiceChannel(auth);
        VeggieBot.getInstance().loadAndPlay(voiceChannel, url);
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
}
