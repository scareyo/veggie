package xyz.scarey.veggie.bot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class VeggieBot {

    public static VeggieBot bot = null;

    private IDiscordClient client;
    private String prefix = "!";

    private VeggieBot(String token) {
        client = new ClientBuilder()
                .withToken(token)
                .build();
    }

    public IDiscordClient getClient() {
        return client;
    }

    public static void initialize(String token) {
        bot = new VeggieBot(token);
    }

    public static VeggieBot getInstance() {
        return bot;
    }
}
