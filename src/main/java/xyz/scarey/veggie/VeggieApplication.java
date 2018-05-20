package xyz.scarey.veggie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import sx.blah.discord.api.IDiscordClient;
import xyz.scarey.veggie.bot.BotEvents;
import xyz.scarey.veggie.bot.VeggieBot;

@SpringBootApplication
@EnableOAuth2Client
public class VeggieApplication {
    public static void main(String[] args) {
        VeggieBot.initialize(System.getenv("VEGGIE_BOT_TOKEN"));
        IDiscordClient client = VeggieBot.getInstance().getClient();
        client.getDispatcher().registerListener(new BotEvents());
        client.login();

        SpringApplication.run(VeggieApplication.class, args);
    }
}
