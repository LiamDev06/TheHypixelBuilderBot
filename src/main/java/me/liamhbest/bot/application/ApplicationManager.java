package me.liamhbest.bot.application;

import me.liamhbest.bot.BuilderBot;
import me.liamhbest.bot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.rmi.CORBA.Util;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ApplicationManager extends ListenerAdapter {

    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event){
        if (event.getChannel().getName().contains("application-")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Staff Application");
            embed.setColor(Color.YELLOW);
            embed.appendDescription("You created a new application. Please send the your answer to ");
            embed.appendDescription("the questions here and wait for the staff team to process it. ");
            embed.appendDescription("Please be detailed and feel free to ask a staff for further assistance.\n");
            embed.appendDescription("When you have answered all the questions and feel ready for a staff member to review it, " +
                    "press the 'Close' button.");
            embed.appendDescription("\n\n");
            embed.appendDescription("**Please type =questions to view all the application questions.**");
            embed.appendDescription("\n\n");
            embed.appendDescription("<:lock:414776062380343296> - Close Application\n");
            embed.appendDescription("<:no_entry:414776062380343296> - Delete Application (can only be performed by a staff member)\n");
            embed.appendDescription("\n");
            embed.setFooter("Someone from the staff team will be with you shortly.");
            embed.setAuthor("The Hypixel Builders");
            event.getChannel().sendMessage(embed.build()).queue(message -> {
                message.addReaction("U+1F512").queue();
                message.addReaction("U+26D4").queue();
            });
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event){
        if ((event.getChannel().getName().contains("application-")
                || event.getChannel().getName().contains("appclosed-")) && !event.getUser().isBot()) {

            event.getReaction().removeReaction(event.getUser()).queue();
            if (event.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1F512")) {
                //Close Reaction
                if (event.getChannel().getName().contains("appclosed-")) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor("This ticket has already been closed!");
                    embed.setColor(Color.RED);
                    event.getChannel().sendMessage(embed.build()).queue();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("The ticket has been closed.");
                embed.setColor(Color.GREEN);
                event.getChannel().sendMessage(embed.build()).queue();
                String userConnected = event.getChannel().getName().substring(12);

                event.getChannel().getManager().setName("appclosed-" + userConnected)
                        .setTopic("The staff team has started the reviewing process. You will receive an answer soon...")
                        .queueAfter(1, TimeUnit.SECONDS);

                //TODO Remove chat permission from member
                for (Member member : event.getChannel().getMembers()){
                    if (!Utils.hasRole(member, "Guild Master") &&
                            !Utils.hasRole(member, "Co-Owner") &&
                            !Utils.hasRole(member, "Officer")) {
                        event.getChannel().getManager().removePermissionOverride(member).queue();
                        break;
                    }
                }

            }

            if (event.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+26D4")) {
                //Delete Reaction
                if (!Utils.hasRole(event.getMember(), "Guild Master")
                    && !Utils.hasRole(event.getMember(), "Co-Owner")
                    && !Utils.hasRole(event.getMember(), "Officer")) return;

                if (!event.getChannel().getName().contains("closed")) {
                    //Can not delete ticket as it has not been closed yet
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor("You have to close the ticket before you can delete it!");
                    embed.setColor(Color.RED);
                    event.getChannel().sendMessage(embed.build()).queue();
                } else {
                    //Delete ticket
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor("Deleting ticket...");
                    embed.setColor(Color.YELLOW);
                    event.getChannel().sendMessage(embed.build()).queue();
                    event.getChannel().delete().queueAfter(3, TimeUnit.SECONDS);
                }
            }
        }


    }


}








