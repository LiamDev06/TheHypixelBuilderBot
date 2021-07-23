package me.liamhbest.bot;

import me.liamhbest.bot.application.ApplicationCommand;
import me.liamhbest.bot.application.ApplicationManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class BuilderBot {

    public static JDA jda;
    private static final String TOKEN = System.getenv("THE_HYPIXEL_BUILDERS_BOT_TOKEN");

    public static void main(String[] args) throws LoginException {
        System.out.println("Starting systems...");

        jda = JDABuilder.createDefault(TOKEN)
                .setActivity(Activity.watching("The Hypixel Builders"))

                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_EMOJIS)
                .enableIntents(GatewayIntent.GUILD_BANS)

                .build();

        jda.addEventListener(new ApplicationCommand());
        jda.addEventListener(new ApplicationManager());

        System.out.println("All systems are operational. Bot completed.");
    }

}












