package com.eulerity.hackathon.imdb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ImdbImageFinder  implements Runnable {
    public String url;
    public int num;
    Thread  thread;

    //Constructor
    public ImdbImageFinder(String movieUrl, int num){
        System.out.println("Crawler Created" + num);
        this.url = movieUrl;
        this.num = num;
        thread = new Thread(this);
        thread.start();

    }


    public void extractPhotosLink(String movieUrl) {
        String photosPage = "";
        try {
            Document doc = Jsoup.connect(movieUrl).get();
            photosPage = "https://imdb.com" + doc.select("a.ipc-button.ipc-button--single-padding.ipc-button--center-align-content.ipc-button--default-height.ipc-button--core-baseAlt.ipc-button--theme-baseAlt.ipc-button--on-onBase.ipc-secondary-button.Link__MediaLinkButton-sc-yyqs5y-3.cVnyJL:nth-of-type(2)").attr("href");
            scraper(photosPage);
            System.out.println(photosPage);


        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public String scraper(String url) {
        String link = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements pics = doc.select("div.media_index_thumb_list > a");
            for (Element element : pics) {
                String pic = element.getElementsByTag("a").attr("href");
                String fullLink = "https://imdb.com" + pic;

                System.out.println(fullLink);
                photoFromLink(fullLink);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return link;
    }

    public String photoFromLink(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String pic = doc.select("div.MediaViewerImagestyles__LandscapeContainer-sc-1qk433p-3.kXRNYt > img").attr("src");
            if(pic == "") {
                String pic2 = doc.select("div.MediaViewerImagestyles__PortraitContainer-sc-1qk433p-2.iUyzNI > img").attr("src");
                System.out.println("pic link: " + pic2);
            }else{
                System.out.println("pic link: " + pic);
            }



        } catch (IOException e) {

        }
        return "";
    }

    @Override
    public void run() {
        extractPhotosLink(url);
    }

    public Thread getThread() {
        return thread;
    }
}

