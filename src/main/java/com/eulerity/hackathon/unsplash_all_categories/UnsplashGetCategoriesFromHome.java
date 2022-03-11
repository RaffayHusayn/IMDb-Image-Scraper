package com.eulerity.hackathon.unsplash_all_categories;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.stream.Collectors;

public class UnsplashGetCategoriesFromHome {
    private List<String> allCategories;
    private final String url = "https://unsplash.com";

    public UnsplashGetCategoriesFromHome(){

    }
    public List<String> getAllCategories(){
        try {
            Document doc = Jsoup.connect(url).get();
            List<String> incompleteHref = doc.select("a.p7ajO").eachAttr("href");
            allCategories = incompleteHref.stream().map(s -> "https://unsplash.com" + s).collect(Collectors.toList());

        }catch (Exception e){
            e.printStackTrace();
        }
        return  allCategories;
    }
}
