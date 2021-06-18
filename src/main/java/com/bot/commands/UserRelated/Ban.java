package com.bot.commands.UserRelated;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Ban extends ListenerAdapter {
    private static Ban single_instance = null;

    private Ban() {

    }

    public static Ban getInstance() {
        if (single_instance == null)
            single_instance = new Ban();

        return single_instance;
    }

    public static String getHelpCommand() {
        return "!help ban";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !ban  <@mention> <perioada(zile)> <motiv> ";
    }

    public static String getHelpUsage() {
        return "Da afara un utilizator de pe server si ii interzice accesul.";
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("!ban")) {
            Member member = event.getMember();
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            List<String> command = List.of(event.getMessage().getContentRaw().split(" +"));
            if (mentionedMembers.isEmpty() || command.size() < 4) {
                event.getChannel().sendMessage("Sintaxa eronata sau utilizator inexistent.").queue();
                return;
            }
            int days = Integer.parseInt(command.get(2));
            Member target = mentionedMembers.get(0);
            String reason = String.join(" ", command.subList(3, command.size()));

            if (!member.hasPermission(Permission.BAN_MEMBERS) && !member.canInteract(target)) {
                event.getChannel().sendMessage("You dont have permission to run this command").queue();
                return;
            }

            target.ban(days)
                    .reason(String.format("Ban by: %#s, with reason: %s", event.getAuthor(), reason)).queue();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Ban Report");
            builder.setColor(Color.decode("#0652DD"));
            builder.addField("Banned User:", target.getEffectiveName(), false);
            builder.addField("Banner:", event.getAuthor().getName(), false);
            builder.addField("Date:", sdf.format(date), false);
            builder.addField("Time:", stf.format(date), false);
            builder.addField("Reason:", reason, false);
            event.getChannel().sendMessage(builder.build()).queue();
        }
    }
}
