package com.eulerity.hackathon.unsplash_all_categories;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UnsplashAllCatergoryImageFinder implements Runnable{
    private static Set<String> unsplashImagesSet = new HashSet<>();
    Thread thread;
    String url;


    //Constructor
    public UnsplashAllCatergoryImageFinder(String url, int num) {
        System.out.println("New Thread : Crawler # " + num);
        this.url = url;
        thread = new Thread(this);
        thread.start();

    }

    public static List<String> getImages() {
        List<String> ImdbImages = new ArrayList<>(unsplashImagesSet);
        unsplashImagesSet.clear();
        return ImdbImages;
    }

    @Override
    public void run() {
        scrap(url);
    }

    public void scrap(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            List<String> allHref = doc.select("a.rEAWd").eachAttr("href");
            List<String> completeHref = allHref.stream().map(s -> "https://unsplash.com" + s).collect(Collectors.toList());
            extractPhotosFromList(completeHref);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void extractPhotosFromList(List<String> urlList) throws IOException {
        for(String url: urlList){
            System.out.println("url "+ url);
            Document doc = Jsoup.connect(url).get();
            String imageUrl = doc.select("div.VQW0y.Jl9NH > img").attr("srcset");
            imageUrl = imageUrl.substring(0, imageUrl.indexOf(" "));
            System.out.println("image url :"+ imageUrl);
            unsplashImagesSet.add(imageUrl);

        }
    }

    public Thread getThread() {
        return thread;
    }
}
