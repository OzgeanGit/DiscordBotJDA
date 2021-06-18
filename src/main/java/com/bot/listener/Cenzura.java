package com.bot.listener;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;

public class Cenzura extends ListenerAdapter {

    private static Cenzura single_instance = null;
    List<String> badWords = Arrays.asList("anal", "anus", "arse", "ass", "ballsack", "balls", "bastard", "bitch", "biatch", "bloody", "blowjob", "bollock", "bollok", "boner", "boob", "bugger", "bum", "butt", "buttplug", "clitoris", "cock", "coon", "crap", "cunt", "damn", "dick", "dildo", "dyke", "fag", "feck", "fellate", "fellatio", "felching", "fuck", "fudgepacker", "fudge packer", "flange", "Goddamn", "God damn", "hell", "homo", "jerk", "jizz", "knobend", "knob end", "labia", "lmao", "lmfao", "muff", "nigger", "nigga", "omg", "penis", "piss", "poop", "prick", "pube", "pussy", "queer", "scrotum", "sex", "shit", "s hit", "sh1t", "slut", "smegma", "spunk", "tit", "tosser", "turd", "twat", "vagina", "wank", "whore", "wtf");

    private Cenzura() {

    }

    public static Cenzura getInstance() {
        if (single_instance == null)
            single_instance = new Cenzura();

        return single_instance;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        List<String> message = List.of(event.getMessage().getContentRaw().split(" +"));
        String newMessage;
        Member member = event.getMember();
        for (String s : message)
            for (String badWord : badWords)
                if (s.equals(badWord)) {
                    newMessage = s.substring(0, s.length() / 2 - 1) + "*" + s.substring(s.length() / 2);
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage(member.getEffectiveName() + " :" + newMessage).queue();
                    event.getChannel().sendMessage("Hey @#OWNER, " + member.getEffectiveName() + " vorbeste urat.(Momentan tag-ul de rol nu este implementat in JDA)").queue();
                }
    }
}
