package com.eulerity.hackathon.imdb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImdbLinkFromList {
    private final String listUrl;
    public ImdbLinkFromList(String listUrl){
        this.listUrl = listUrl;
    }
    public List<String> getPageUrl() throws IOException {

        List<String> pageUrlList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(listUrl).get();
            Elements movies = doc.select("div.lister-item.mode-detail");
            for (Element movieElement : movies) {


                /*
                Web Scrapping using Jsoup
                 */
                String pageUrlString = movieElement.select("div.lister-item-image.ribbonize > a").attr("href");
                String pageUrl = "http://imdb.com" + pageUrlString;

                pageUrlList.add(pageUrl);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pageUrlList;
    }

}
