package com.bot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;

public class MessageReaction extends ListenerAdapter {

    private static MessageReaction single_instance = null;

    private MessageReaction() {

    }

    public static MessageReaction getInstance() {
        if (single_instance == null)
            single_instance = new MessageReaction();

        return single_instance;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        List<String> keywords = Arrays.asList("java", "programare", "programming", "computer", "science");
        List<String> message = List.of(event.getMessage().getContentRaw().split(" +"));
        for (String s : message)
            for (String keyword : keywords)
                if (s.equalsIgnoreCase(keyword)) {
                    event.getChannel().addReactionById(event.getMessageId(), "U+203C").queue();
                }
    }
}
