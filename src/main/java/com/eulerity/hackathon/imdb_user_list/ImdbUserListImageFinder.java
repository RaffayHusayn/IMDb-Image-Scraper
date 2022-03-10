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
    public String url;
    public int num;
    public static Set<String> ImdbImagesSet = new HashSet<>();
    Thread  thread;


    //Constructor
    public ImdbUserListImageFinder(String movieUrl, int num){
        System.out.println("Crawler Created" + num);
        this.url = movieUrl;
        this.num = num;
        thread = new Thread(this);
        thread.start();

    }


    public void extractPhotosLink(String movieUrl) {
        System.out.println("movie url : "+ movieUrl);
        String photosPage = "";
        try {
            Document doc = Jsoup.connect(movieUrl).get();
            photosPage = "https://imdb.com" + doc.select("a.ipc-button.ipc-button--single-padding.ipc-button--center-align-content.ipc-button--default-height.ipc-button--core-baseAlt.ipc-button--theme-baseAlt.ipc-button--on-onBase.ipc-secondary-button.Link__MediaLinkButton-sc-yyqs5y-3.cVnyJL:nth-of-type(2)").attr("href");

            System.out.println(photosPage);

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
                ImdbImagesSet.add(pic2);
            }else{
                System.out.println("pic link: " + pic);
                ImdbImagesSet.add(pic);
            }



        } catch (IOException e) {

        }
        return "";
    }

    @Override
    public void run() {
        System.out.println("thread is starting, calling extractPhotosLInks");
        extractPhotosLink(url);
    }

    public Thread getThread() {
        return thread;
    }

    public static List<String> getImages(){
        List<String> ImdbImages= new ArrayList<>(ImdbImagesSet);
        ImdbImagesSet.clear();
        return ImdbImages;
    }
}

