package com.eulerity.hackathon.unsplash_all_categories;

import com.google.common.cache.CacheLoader;

import java.util.ArrayList;
import java.util.List;

public class UnsplashAllCategoryCacheLoader extends CacheLoader<String,List<String>> {
    public List<String> load(String url) throws Exception {

        UnsplashGetCategoriesFromHome unsplashCategories = new UnsplashGetCategoriesFromHome();
        List<String> categoriesList = unsplashCategories.getAllCategories();


        List<Thread> threadList = new ArrayList<>();

        int threadCounter = 0;
        for (String s : categoriesList) {
            if (threadCounter == categoriesList.size()) break;
            UnsplashAllCatergoryImageFinder scraper = new UnsplashAllCatergoryImageFinder(s, threadCounter);
            threadList.add(threadCounter, scraper.getThread());
            threadCounter++;
        }
        try {
            for(Thread t: threadList){
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> unsplashImages = new ArrayList<>(UnsplashAllCatergoryImageFinder.getImages());
        return unsplashImages;
    }
}
