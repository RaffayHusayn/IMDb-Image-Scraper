package com.eulerity.hackathon.imdb_user_list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImdbGetPagesFromList {
    private final String listUrl;

    //constructor
    public ImdbGetPagesFromList(String listUrl) {
        this.listUrl = listUrl;
    }

    /*
        provided a url to an IMDb user list, it scrapes the urls of all the movie pages in the user list.
     */
    public List<String> getPageUrl() throws IOException {

        List<String> pageUrlList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(listUrl).get();
            Elements movies = doc.select("div.lister-item.mode-detail");
            for (Element movieElement : movies) {
                //Web Scrapping using Jsoup
                String pageUrlString = movieElement.select("div.lister-item-image.ribbonize > a").attr("href");
                //completeing the string by appending "http://imdb.com" to the front
                String pageUrl = "http://imdb.com" + pageUrlString;
                pageUrlList.add(pageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageUrlList;
    }

}
