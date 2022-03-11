package com.eulerity.hackathon.imdb_single_page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImdbPageImageFinder {
    private String url;
    private Set<String> ImdbImagesSet = new HashSet<>();

    //Constructor
    public ImdbPageImageFinder(String movieUrl) {
        this.url = movieUrl;
        extractPhotosLink(url);
    }

     private void extractPhotosLink(String movieUrl) {
        System.out.println("Scraping from Movie Page : " + movieUrl);
        String photosPage = "";
        try {
            Document doc = Jsoup.connect(movieUrl).get();
            photosPage = "https://imdb.com" + doc.select("a.ipc-button.ipc-button--single-padding.ipc-button--center-align-content.ipc-button--default-height.ipc-button--core-baseAlt.ipc-button--theme-baseAlt.ipc-button--on-onBase.ipc-secondary-button.Link__MediaLinkButton-sc-yyqs5y-3.cVnyJL:nth-of-type(2)").attr("href");

            scrape(photosPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private String scrape(String url) {
        String link = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements pics = doc.select("div.media_index_thumb_list > a");
            int counter = 0;
            for (Element element : pics) {
                String pic = element.getElementsByTag("a").attr("href");
                String fullLink = "https://imdb.com" + pic;
                photoFromLink(fullLink, counter);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return link;
    }

     private String photoFromLink(String url, int counter) {
        try {
            Document doc = Jsoup.connect(url).get();
            String pic = doc.select("div.MediaViewerImagestyles__LandscapeContainer-sc-1qk433p-3.kXRNYt > img").attr("src");
            if (pic == "") {
                String pic2 = doc.select("div.MediaViewerImagestyles__PortraitContainer-sc-1qk433p-2.iUyzNI > img").attr("src");
                System.out.println("Scraping pic # " + counter + " : " + pic2);
                ImdbImagesSet.add(pic2);
            } else {
                ImdbImagesSet.add(pic);
                System.out.println("Scraping pic # " + counter + " : " + pic);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Converts Hashset to ArrayList, return ArrayList and clears hashset
    public List<String> getImages() {
        List<String> ImdbImages = new ArrayList<>(ImdbImagesSet);
        ImdbImagesSet.clear();
        return ImdbImages;
    }
}
