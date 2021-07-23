package me.liamhbest.bot.application;

import me.liamhbest.bot.utility.Channel;
import me.liamhbest.bot.utility.Role;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;

public class ApplicationCommand extends ListenerAdapter {

    //                UserID
    private final ArrayList<String> applications = new ArrayList<>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event){
        if (event.getChannel().getIdLong() != Channel.COMMANDS.getId()
        && !event.getChannel().getName().contains("application-")) return;
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();
        String userID = event.getAuthor().getId();

        if (message.equalsIgnoreCase("=apply")) {
            if (!applications.contains(userID)) {
                applications.add(userID);
                Category category = event.getGuild().getCategoryById(868248029654810625L);

                event.getGuild().createTextChannel("application-" + event.getMember().getEffectiveName())
                        .setTopic("Send your application here. This will be put in for the management team to review.")
                        .addPermissionOverride(event.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                        .addPermissionOverride(event.getGuild().getRoleById(Role.GUILD_MASTER.getId()), EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(event.getGuild().getRoleById(Role.CO_OWNER.getId()), EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(event.getGuild().getRoleById(Role.OFFICER.getId()), EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addMemberPermissionOverride(event.getAuthor().getIdLong(), EnumSet.of(Permission.VIEW_CHANNEL), null)

                        .setParent(category)

                        .queue();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.GREEN);
                embed.setTitle("Application");
                embed.appendDescription("A new channel for your application has been created.\n");
                embed.appendDescription("**Channel:** " + "<#" + "ID" + ">");
                channel.sendMessage(embed.build()).queue();

            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.appendDescription("You already have an application channel open!\n");
                embed.appendDescription("**Channel:** " + "<#" + "ID" + ">");
                channel.sendMessage(embed.build()).queue();
            }
        }

        if (message.equalsIgnoreCase("=questions")) {
            if (event.getChannel().getName().contains("application-")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle("Application Questions");
                embed.appendDescription("**1 -** What is your Minecraft username?" + "\n");
                embed.appendDescription("**2 -** What is your time zone?" + "\n");
                embed.appendDescription("**3 -** What is your Hypixel Level?" + "\n");
                embed.appendDescription("**4 -** Why do you want to join this guild?" + "\n");
                embed.appendDescription("**5 -** Why should we invite you more than other users?" + "\n");
                embed.appendDescription("**6 -** What minigames do you usually play on Hypixel?" + "\n");
                embed.appendDescription("**7 -** Have you gotten a punishment(s) in the past months?" + "");
                channel.sendMessage(embed.build()).queue();
            }
        }

    }

}
















