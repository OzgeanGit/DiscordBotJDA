package com.bot.listener;

import com.bot.RSS.RssFeed;
import com.bot.commands.AMA;
import com.bot.commands.Calculate;
import com.bot.commands.Help;
import com.bot.commands.TimedPost;
import com.bot.commands.UserRelated.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class EventListener extends ListenerAdapter {

    public Ping pingPong = Ping.getInstance();

    public RssFeed rssFeed = RssFeed.getInstance();

    public AMA ama = AMA.getInstance();

    public Calculate calculate = Calculate.getInstance();

    public Ban ban = Ban.getInstance();

    public TimedPost timedPost = TimedPost.getInstance();

    public Mute mute = Mute.getInstance();

    public Unmute unmute = Unmute.getInstance();

    public Unban unban = Unban.getInstance();

    public Cenzura cenzura = Cenzura.getInstance();

    public MessageReaction messageReaction = MessageReaction.getInstance();

    public JoinListener joinListener = JoinListener.getInstance();

    public Kick kick = Kick.getInstance();

    public Help help = Help.getInstance();
}
