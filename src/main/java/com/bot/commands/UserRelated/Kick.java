package com.bot.commands.UserRelated;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Kick extends ListenerAdapter {

    private static Kick single_instance = null;

    private Kick() {

    }

    public static Kick getInstance() {
        if (single_instance == null)
            single_instance = new Kick();

        return single_instance;
    }

    public static String getHelpCommand() {
        return "!help kick";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !kick  <@mention>  <motiv> ";
    }

    public static String getHelpUsage() {
        return "Da afara un utilizator de pe server.";
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("!kick")) {

            Member member = event.getMember();
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

            List<String> args = Arrays.asList(msg.split(" "));
            if (mentionedMembers.isEmpty() || args.size() < 3) {
                event.getChannel().sendMessage("Sintaxa eronata sau utilizator inexistent").queue();
                return;
            }


            String reason = String.join(" ", args.subList(2, args.size()));


            for (Member memberTarget : mentionedMembers
            ) {
                if (!member.hasPermission(Permission.KICK_MEMBERS) && !member.canInteract(memberTarget)) {
                    event.getChannel().sendMessage("You dont have permission to run this command").queue();
                    return;
                }
                memberTarget.kick()
                        .reason(String.format("Kick by: %#s, with reason: %s", event.getAuthor(), reason)).queue();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Kick Report");
            builder.setColor(Color.decode("#0652DD"));
            builder.addField("Kicked User:", mentionedMembers.get(0).getEffectiveName(), false);
            builder.addField("Kicker:", event.getAuthor().getName(), false);
            builder.addField("Date:", sdf.format(date), false);
            builder.addField("Time:", stf.format(date), false);
            builder.addField("Reason:", reason, false);
            event.getChannel().sendMessage(builder.build()).queue();
            System.out.println(event.getChannel().sendMessage(builder.build().toString()));
        }

    }
}