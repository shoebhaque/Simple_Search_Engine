package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    HashSet<String> urlSet;
    int MAX_DEPTH = 2;

    Crawler() {
        urlSet = new HashSet<String>();
    }

    public void getPageTextAndLinks(String url, int depth) {
        if (urlSet.contains(url)) {
            return;
        }
        //if depth is greater than max then return
        if (depth >= MAX_DEPTH) {
            return;
        }
        if(urlSet.add(url)){
            System.out.println(url);
        }
        depth++;
        try {
            //Parsing HTML object to Java Document object
            Document document = Jsoup.connect(url).timeout(5000).get();


            // Indexer Works Starts here
            Indexer indexer = new Indexer(document, url);
            System.out.println(document.title());
            Elements availableLinksOnPage = document.select("a[href]");
            //run method recursively for every link available on current page
            for (Element currentLink : availableLinksOnPage) {
                getPageTextAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextAndLinks("https://www.javatpoint.com",0);
    }
}