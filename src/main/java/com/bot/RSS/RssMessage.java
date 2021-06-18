package com.bot.RSS;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RssMessage {
    FeedReader feedReader = new FeedReader();
    String title;
    String description;
    String link;
    String author;
    String guid;
    String category;

    public SyndEntry getLatestNews(String url) throws IOException, FeedException {
        return feedReader.getfeed(url).getEntries().get(0);
    }

    public List<SyndEntry> getNNews(String url, int limit) throws IOException, FeedException {

        return feedReader.getfeed(url).getEntries().stream().limit(limit).collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link + ", author=" + author + ", guid=" + guid
                + ", category= " + category + "]";
    }

}
