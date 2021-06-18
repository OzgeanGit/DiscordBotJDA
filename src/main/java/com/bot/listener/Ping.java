package com.bot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter {

    private static Ping single_instance = null;

    private Ping() {

    }

    public static Ping getInstance() {
        if (single_instance == null)
            single_instance = new Ping();

        return single_instance;
    }

    public static String getHelpCommand() {
        return "-";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !ping";
    }

    public static String getHelpUsage() {
        return "Intoarce textul !pong";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!Ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        }
    }
}
