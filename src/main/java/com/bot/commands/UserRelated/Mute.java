package com.bot.commands.UserRelated;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Mute extends ListenerAdapter {


    private static Mute single_instance = null;

    private Mute() {

    }

    public static Mute getInstance() {
        if (single_instance == null)
            single_instance = new Mute();

        return single_instance;
    }

    public static String getHelpCommand() {
        return "!help mute";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !mute  <@mention>  <motiv> ";
    }

    public static String getHelpUsage() {
        return "Revoca dreptul de a vorbi, acorda rolul <Muted>";
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("!mute")) {
            Member member = event.getMember();
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

            List<String> args = Arrays.asList(msg.split(" "));
            if (mentionedMembers.isEmpty() || args.size() < 2) {
                event.getChannel().sendMessage("Sintaxa eronata sau utilizator inexistent.").queue();
                return;
            }
            Member target = mentionedMembers.get(0);
            String reason = args.subList(2, args.size()).toString().replaceAll("[\\[\\]]", "").replaceAll(",", "");

            if (!member.hasPermission(Permission.VOICE_MUTE_OTHERS) && !member.canInteract(target)) {
                event.getChannel().sendMessage("You dont have permission to run this command").queue();
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Mute Report");
            builder.setColor(Color.decode("#0652DD"));
            builder.addField("Muted User:", target.getEffectiveName(), false);
            builder.addField("Muter:", event.getAuthor().getName(), false);
            builder.addField("Date:", sdf.format(date), false);
            builder.addField("Time:", stf.format(date), false);
            builder.addField("Reason:", reason, false);
            event.getChannel().sendMessage(builder.build()).queue();
            Role role = event.getGuild().getRoleById("845962284035080232");
            for (var currentRole : target.getRoles())
                event.getGuild().removeRoleFromMember(target, currentRole);
            event.getGuild().addRoleToMember(target, role).queue();
        }
    }
}
