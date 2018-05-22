package xyz.scarey.veggie.controller;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.IVoiceState;
import xyz.scarey.veggie.bot.TrackScheduler;
import xyz.scarey.veggie.bot.VeggieBot;

import java.util.Collection;
import java.util.LinkedHashMap;

@Controller
public class MusicController {

    @GetMapping("/music")
    public String music(OAuth2Authentication auth, Model model) {
        IVoiceChannel channel;
        IGuild guild;
        AudioPlayer player;
        TrackScheduler scheduler;

        // If there are no bots existing, do not display page.
        if (VeggieBot.getInstance().getClient().getGuilds().isEmpty()) {
            return "forward:/";
        }

        channel = getVoiceChannel(auth);

        if (channel != null) {
            guild = channel.getGuild();
            player = VeggieBot.getInstance().getGuildMusicManager(guild).getPlayer();
            scheduler = VeggieBot.getInstance().getGuildMusicManager(channel.getGuild()).getScheduler();

            model.addAttribute("guild", guild.getName());
            model.addAttribute("channel", channel.getName());
            if (player.getPlayingTrack() != null) {
                model.addAttribute("current", player.getPlayingTrack().getInfo().title);
            }
            model.addAttribute("queue", scheduler.getQueue());
        }

        return "music";
    }

    @PostMapping("/music")
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
