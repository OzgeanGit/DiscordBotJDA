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

public class Unmute extends ListenerAdapter {

    private static Unmute single_instance = null;

    private Unmute() {

    }

    public static Unmute getInstance() {
        if (single_instance == null)
            single_instance = new Unmute();

        return single_instance;
    }

    public static String getHelpCommand() {
        return "!help unmute";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !unmute  <@mention>.";
    }

    public static String getHelpUsage() {
        return "Acorda dreptul de a vorbi, revoca rolul <Muted>";
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("!unmute")) {
            Member member = event.getMember();
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

            List<String> args = Arrays.asList(msg.split(" "));
            if (mentionedMembers.isEmpty() || args.size() < 2) {
                event.getChannel().sendMessage("Missing Arguments").queue();
                return;
            }

            Member target = mentionedMembers.get(0);

            if (!member.hasPermission(Permission.VOICE_MUTE_OTHERS) && !member.canInteract(target)) {
                event.getChannel().sendMessage("You dont have permission to run this command").queue();
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Unmute Report");
            builder.setColor(Color.decode("#0652DD"));
            builder.addField("Unmuted User:", mentionedMembers.get(0).getEffectiveName(), false);
            builder.addField("Unmuter:", event.getAuthor().getName(), false);
            builder.addField("Date:", sdf.format(date), false);
            builder.addField("Time:", stf.format(date), false);
            event.getChannel().sendMessage(builder.build()).queue();
            Role role = event.getGuild().getRoleById("845962284035080232");
            event.getGuild().removeRoleFromMember(mentionedMembers.get(0), role).queue();
        }

    }
}
