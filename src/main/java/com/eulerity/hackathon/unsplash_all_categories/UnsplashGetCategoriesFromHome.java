package com.eulerity.hackathon.unsplash_all_categories;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.stream.Collectors;

public class UnsplashGetCategoriesFromHome {
    private final String url = "https://unsplash.com";
    private List<String> allCategories;

    public UnsplashGetCategoriesFromHome() {

    }

    // returns all the categories from the unsplash homepage
    public List<String> getAllCategories() {
        try {
            Document doc = Jsoup.connect(url).get();
            //returns an incomplete url without "https://unsplash.com" in the front
            List<String> incompleteHref = doc.select("a.p7ajO").eachAttr("href");
            //mapping each value to complete urls by adding "https://unsplash.com" in the front
            allCategories = incompleteHref.stream().map(s -> "https://unsplash.com" + s).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCategories;
    }
}
