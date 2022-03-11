package com.eulerity.hackathon.unsplash_all_categories;

import com.google.common.cache.CacheLoader;

import java.util.ArrayList;
import java.util.List;
/*
Cache For Unsplash all Categories
 */
public class UnsplashAllCategoryCacheLoader extends CacheLoader<String, List<String>> {
    public List<String> load(String url) throws Exception {

        //getting all the categories in Unsplash homepage
        UnsplashGetCategoriesFromHome unsplashCategories = new UnsplashGetCategoriesFromHome();
        List<String> categoriesList = unsplashCategories.getAllCategories();

        //List to keep track of all the threads
        List<Thread> threadList = new ArrayList<>();
        int threadCounter = 0;

        //Creating a new thread for every Category in list
        for (String s : categoriesList) {
            if (threadCounter == categoriesList.size()) break;
            UnsplashAllCatergoryImageFinder scraper = new UnsplashAllCatergoryImageFinder(s, threadCounter);
            threadList.add(threadCounter, scraper.getThread());
            threadCounter++;
        }
        try {
            //joining all the threads before returning the shared ArrayList
            for (Thread t : threadList) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> unsplashImages = new ArrayList<>(UnsplashAllCatergoryImageFinder.getImages());
        return unsplashImages;
    }
}
