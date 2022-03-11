package com.eulerity.hackathon.imdb_user_list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImdbUserListImageFinder implements Runnable {
    private static Set<String> ImdbImagesSet = new HashSet<>();
    Thread thread;
    private String url;


    //Constructor
    public ImdbUserListImageFinder(String movieUrl, int num) {
        System.out.println("New Thread : Crawler # " + num);
        this.url = movieUrl;
        thread = new Thread(this);
        thread.start();

    }

    public static List<String> getImages() {
        List<String> ImdbImages = new ArrayList<>(ImdbImagesSet);
        ImdbImagesSet.clear();
        return ImdbImages;
    }

    @Override
    public void run() {
        extractPhotosLink(url);
    }

    public Thread getThread() {
        return thread;
    }

    private void extractPhotosLink(String movieUrl) {
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
            for (Element element : pics) {
                String pic = element.getElementsByTag("a").attr("href");
                String fullLink = "https://imdb.com" + pic;

                photoFromLink(fullLink);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return link;
    }

    private String photoFromLink(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String pic = doc.select("div.MediaViewerImagestyles__LandscapeContainer-sc-1qk433p-3.kXRNYt > img").attr("src");
            if (pic == "") {
                String pic2 = doc.select("div.MediaViewerImagestyles__PortraitContainer-sc-1qk433p-2.iUyzNI > img").attr("src");
                System.out.println("Scraping pics : " + pic2);
                ImdbImagesSet.add(pic2);
            } else {
                ImdbImagesSet.add(pic);
                System.out.println("Scraping pics : " + pic);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}

