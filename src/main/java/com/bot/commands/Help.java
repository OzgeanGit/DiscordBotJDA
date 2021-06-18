package com.bot.commands;

import com.bot.RSS.RssFeed;
import com.bot.commands.UserRelated.*;
import com.bot.listener.Ping;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;


public class Help extends ListenerAdapter {

    private static Help single_instance = null;

    private Help() {

    }

    public static Help getInstance() {
        if (single_instance == null)
            single_instance = new Help();

        return single_instance;
    }


    public void onMessageReceived(MessageReceivedEvent event) {
        List<String> command = List.of(event.getMessage().getContentRaw().split(" +"));
        Member member = event.getMember();
        String message = "";
        String keyword = command.get(command.size() - 1);
        String ownerId = "725625208018567219";
        if (command.get(0).equalsIgnoreCase("!help")) {
            if (command.size() == 1) {
                message = """
                        Salut! Poti folosi urmatoarele comenzi:
                        !help
                        !help calc
                        !help feed
                        !help ask""";
                for (int i = 0; i < member.getRoles().size(); i++)
                    if (member.getRoles().get(i).getId().equals(ownerId) || member.getRoles().get(i).getId().equals("725625539099885629")) {  //verifica daca este owner sau mod
                        message = """
                                Salut! Poti folosi urmatoarele comenzi:
                                !help
                                !help mute
                                !help kick
                                !help unmute
                                !help ban
                                !help unban
                                !help calc
                                !help feed
                                !help ask
                                !help ping
                                """;
                    }
            }
            if (command.size() == 2) {
                message = "Nu poti folosi aceasta comanda.";
                for (var role : member.getRoles())
                    if (role.getId().equals(ownerId) || role.getId().equals("725625539099885629")) {
                        if (keyword.equals("mute"))
                            message = "Comanda: " + Mute.getHelpCommand() + "\n" + Mute.getHelpSyntax() + "\nCe face comanda: " + Mute.getHelpUsage();
                        if (keyword.equals("kick"))
                            message = "Comanda: " + Kick.getHelpCommand() + "\n" + Kick.getHelpSyntax() + "\nCe face comanda: " + Kick.getHelpUsage();
                        if (keyword.equals("ban"))
                            message = "Comanda: " + Ban.getHelpCommand() + "\n" + Ban.getHelpSyntax() + "\nCe face comanda: " + Ban.getHelpUsage();
                        if (keyword.equals("unmute"))
                            message = "Comanda: " + Unmute.getHelpCommand() + "\n" + Unmute.getHelpSyntax() + "\nCe face comanda: " + Unmute.getHelpUsage();
                        if (keyword.equals("unban"))
                            message = "Comanda: " + Unban.getHelpCommand() + "\n" + Unban.getHelpSyntax() + "\nCe face comanda: " + Unban.getHelpUsage();
                    }
                if (keyword.equals("ping"))
                    message = "Comanda: " + Ping.getHelpCommand() + "\n" + Ping.getHelpSyntax() + "\nCe face comanda: " + Ping.getHelpUsage();
                if (keyword.equals("ask"))
                    message = "Comanda: " + AMA.getHelpCommand() + "\n" + AMA.getHelpSyntax() + "\nCe face comanda: " + AMA.getHelpUsage() + "\nAlta comanda utila: !help questions";
                if (keyword.equals("feed"))
                    message = "Comanda: " + RssFeed.getHelpCommand() + "\n" + RssFeed.getHelpSyntax() + "\nCe face comanda: " + RssFeed.getHelpUsage() + "\nAlta comanda utila: !help feeds";
                if (keyword.equals("calc"))
                    message = "Comanda: " + Calculate.getHelpCommand() + "\n" + Calculate.getHelpSyntax() + "\nCe face comanda: " + Calculate.getHelpUsage();
                if (keyword.equals("questions"))
                    message = """
                            Raspund la urmatoarele intrebari:
                            Ma poti ajuta?
                            Stii java?
                            Unde pot invata Java?
                            Unde pot invata Python?
                            Unde pot invata C?
                            Unde pot invata C++?
                            Imi poti recomanda niste carti?
                            """;
                if (keyword.equals("feeds"))
                    message = """
                            Lista de feeduri implicite:
                            digi
                            java
                            reddit-humour
                            reddit-programming
                            programiz
                            real-python
                            java-revisited
                            css-tricks
                            youtube-freecodecamp
                            """;

            }
            event.getChannel().sendMessage(message).queue();
        }
    }
}
