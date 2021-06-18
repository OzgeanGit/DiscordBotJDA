package com.bot;

import com.bot.listener.EventListener;
import com.rometools.rome.io.FeedException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;

//TODO Pachete + Organizare cod in clase + DesignPatterns -saptamanaviitoare joi/vineri
public class Main {
    public static void main(String[] args) throws IOException, FeedException {
        JDABuilder jdaBuilder = JDABuilder.createDefault("ODQ1MDczNDU3NTE1MDY5NDgx.YKbp_A.8HTIyvmLmv-pZSYwkYcRUJcR00M")
                .setActivity(Activity.listening("Ozgean"));
        jdaBuilder.addEventListeners(new EventListener().cenzura, new EventListener().pingPong, new EventListener().rssFeed, new EventListener().ama,
                new EventListener().calculate, new EventListener().ban, new EventListener().timedPost, new EventListener().mute, new EventListener().unmute,
        new EventListener().unban, new EventListener().messageReaction, new EventListener().kick, new EventListener().joinListener, new EventListener().help);

        JDA jda = null;
        try {
            jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
