package com.bot.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class TimedPost extends ListenerAdapter {
    private static TimedPost single_instance = null;

    private TimedPost() {

    }

    public static TimedPost getInstance() {
        if (single_instance == null)
            single_instance = new TimedPost();

        return single_instance;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                String message = "Foloseste comanda !help pentru mai multe informatii.";
                JDA jda = event.getJDA();
                for (Guild guild : jda.getGuilds()) {
                    guild.getDefaultChannel().sendMessage(message).queue();
                }
            }
        }, 0, 600000);


    }

}
