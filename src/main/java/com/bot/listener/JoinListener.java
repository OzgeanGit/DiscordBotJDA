package com.bot.listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class JoinListener extends ListenerAdapter { // asta nu functioneaza dar ar putea functiona teoretic

    private static JoinListener single_instance = null;

    private JoinListener() {

    }

    public static JoinListener getInstance() {
        if (single_instance == null)
            single_instance = new JoinListener();

        return single_instance;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild(); // Get the guild that the user joined.
        User user = event.getUser();    // Get the user that joined.

        List<TextChannel> channels = guild.getTextChannelsByName("general", true);
        System.out.println(channels.get(0).getName());
        event.getGuild().getDefaultChannel().sendMessage("123");
        for (TextChannel channel : channels) {
            channel.sendMessage("New member joined: " + user).queue();
        }
    }
}

