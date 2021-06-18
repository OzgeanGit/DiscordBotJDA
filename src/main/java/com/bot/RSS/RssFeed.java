package com.bot.RSS;

import com.rometools.rome.feed.synd.SyndEntry;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RssFeed extends ListenerAdapter {
    private static RssFeed single_instance = null;
    final FeedReader feedReader;
    final RssMessage rssMessage;
    Map<String, String> urls;


    private RssFeed() {
        feedReader = new FeedReader();
        rssMessage = new RssMessage();
        urls = new HashMap<>(
                Map.ofEntries(
                        Map.entry("digi", "https://www.digisport.ro/rss"),
                        Map.entry("java", "https://www.infoworld.com/category/java/index.rss"),
                        Map.entry("reddit-humour", "https://www.reddit.com/r/ProgrammerHumor.rss"),
                        Map.entry("reddit-programming", " https://www.reddit.com/r/programming.rss"),
                        Map.entry("java-revisited", "http://feeds.feedburner.com/Javarevisited"),
                        Map.entry("programiz", "https://www.programiz.com/python-programming/rss.xml"),
                        Map.entry("real-python", "https://realpython.com/atom.xml?format=xml"),
                        Map.entry("css-tricks", "https://css-tricks.com/feed/"),
                        Map.entry("youtube-freecodecamp", "https://www.youtube.com/feeds/videos.xml?channel_id=UC8butISFwT-Wl7EV0hUK0BQ")
                ));
    }
// javacodegeeks.com/feed
    public static RssFeed getInstance() {
        if (single_instance == null)
            single_instance = new RssFeed();

        return single_instance;
    }

    public static String getHelpCommand() {
        return "!help feed";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !feed <subiect> sau !feedmemore <subiect> sau pentru a adauga un feed foloseste !addFeed <subiect> <linkRSS> ";
    }

    public static String getHelpUsage() {
        return "Ofera mesaje preluate prin RSS pe anumite teme";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        List<String> command = List.of(event.getMessage().getContentRaw().split(" +"));
        String url;
        if (command.size() > 1 && urls.containsKey(command.get(1).toLowerCase(Locale.ROOT)))
            url = urls.get(command.get(1).toLowerCase(Locale.ROOT));
        else url = urls.get("digi");
        if (command.get(0).equalsIgnoreCase("!Feed")) {
            try {
                SyndEntry news = rssMessage.getLatestNews(url);
                event.getChannel().sendMessage("Titlu: " + news.getTitle() + "\nAutor: " + news.getAuthor() + "\nLink: " + news.getLink()).queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (command.get(0).equalsIgnoreCase("!FeedMeMore")) {
            try {
                String msg = rssMessage.getNNews(url, 5).stream().reduce("",
                        (string, news) -> string + "Titlu: " + news.getTitle() + "\nAutor: " + news.getAuthor() + "\nLink: " + news.getLink() + "\n\n",
                        String::concat);
                event.getChannel().sendMessage(msg).queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (command.get(0).equalsIgnoreCase("!addFeed")) {
            try {
                rssMessage.getLatestNews(command.get(2)).toString();
                urls.put(command.get(1), command.get(2));
            } catch (Exception e) {
                e.printStackTrace();
                event.getChannel().sendMessage("Sintaxa incorecta, asigura-te ca linkul este corect si complet.").queue();
            }
        }
    }


}