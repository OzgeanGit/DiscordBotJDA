package com.bot.commands.UserRelated;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Unban extends ListenerAdapter {

    private static Unban single_instance = null;

    private Unban() {

    }

    public static Unban getInstance() {
        if (single_instance == null)
            single_instance = new Unban();

        return single_instance;
    }

    private static boolean isRightUser(Guild.Ban ban, String args) {
        User bannedUser = ban.getUser();
        return bannedUser.getName().equalsIgnoreCase(args) || bannedUser.getId().equals(args)
                || bannedUser.getAsTag().equalsIgnoreCase(args);
    }

    public static String getHelpCommand() {
        return "!help unban";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !unban  <id_utilizator>";
    }

    public static String getHelpUsage() {
        return "Ofera accesul unui utilizator banat pe server.";
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        List<String> command = List.of(event.getMessage().getContentRaw().split(" +"));
        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("!unban")) {
            String userId = command.get(1);
            Member member = event.getMember();
            if (userId.isEmpty() || command.size() < 2) {
                event.getChannel().sendMessage("Sintaxa eronata sau utilizator inexistent").queue();
                return;
            }
            event.getGuild().retrieveBanList().queue((bans) -> {
                //Finds all possible targets
                List<User> possibleTargets = bans.stream().filter((ban) -> isRightUser(ban, userId)).map(Guild.Ban::getUser).collect(Collectors.toList());
                if (possibleTargets.isEmpty()) {
                    event.getChannel().sendMessage("Acest utilizator nu este banat.").queue();
                    return;
                }
                //Unbans the first possible target found!
                User target = possibleTargets.get(0);
                String targetName = String.format("%#s", target);
                event.getGuild().unban(target).queue();
                event.getChannel().sendMessage("User " + targetName + " unbanned.").queue();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Unban Report");
                builder.setColor(Color.decode("#0652DD"));
                builder.addField("Unbanned User ID:", userId, false);
                builder.addField("Unbanner:", event.getAuthor().getName(), false);
                builder.addField("Date:", sdf.format(date), false);
                builder.addField("Time:", stf.format(date), false);
                event.getChannel().sendMessage(builder.build()).queue();
            });

            if (!member.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("You dont have permission to run this command").queue();
                return;
            }


            //    event.getGuild().unban(userId).queue();
            //   event.getChannel().sendMessage(userId).queue();

        }

    }
}
