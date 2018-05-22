package xyz.scarey.veggie.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.scarey.veggie.bot.VeggieBot;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(OAuth2Authentication auth, Model model) {
        model.addAttribute("musicEnabled", isMusicEnabled());
        return "index";
    }

    private boolean isMusicEnabled() {
        if (VeggieBot.getInstance().getClient().getGuilds().isEmpty()) {
            System.out.println("false");
            return false;
        }
        System.out.println("true");
        return true;
    }
}
