package com.bot.RSS;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;


public class FeedReader {

    public SyndFeed getfeed(String url) throws IOException, FeedException {

        URL feedUrl = new URL(url);

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        System.out.println(feed);

        return feed;

    }

}
