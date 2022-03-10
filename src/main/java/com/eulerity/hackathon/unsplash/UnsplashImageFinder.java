package com.eulerity.hackathon.unsplash;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnsplashImageFinder {
    private final String url;
    private List<String> unsplashImagesList= new ArrayList<>();

    //constructor
    public UnsplashImageFinder(String url){
        this.url = url;
    }

    //scraper
    public void scrape(){
        try{
            Document doc = Jsoup.connect(url).get();
            String nav = doc.select("a.ztehP.KHq0c > div").text();
            List<String> allHref = doc.select("a.rEAWd").eachAttr("href");

            List<String> completeHref = allHref.stream().map(s->"https://unsplash.com"+s).collect(Collectors.toList());
            extractPhotosFromList(completeHref);
//            System.out.println(allHref);
//            System.out.println(completeHref);
//            System.out.println(completeHref.size());
//            System.out.println(nav);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void extractPhotosFromList(List<String> urlList) throws IOException {
        for(String url: urlList){
//            System.out.println("url "+ url);
            Document doc = Jsoup.connect(url).get();
            String imageUrl = doc.select("div.VQW0y.Jl9NH > img").attr("srcset");
            imageUrl = imageUrl.substring(0, imageUrl.indexOf(" "));
//            System.out.println("image url :"+ imageUrl);
            unsplashImagesList.add(imageUrl);

        }
    }
    public List<String> getList(){
        List<String> tempReturnList = new ArrayList<>(unsplashImagesList);
        unsplashImagesList.clear();
        return tempReturnList;
    }

}
