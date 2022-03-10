package com.eulerity.hackathon.imdb;

import com.google.common.cache.CacheLoader;

import java.util.ArrayList;
import java.util.List;

public class ImdbListCacheLoader extends CacheLoader<String, List<String>> {
    @Override
    public List<String> load(String movieUrl) throws Exception {
        ImdbLinkFromList list = new ImdbLinkFromList(movieUrl);
        List<String> pageList = list.getPageUrl();
        int threadCounter = 0;
        List<Thread> threadList = new ArrayList<>();
        for (String s : pageList) {
            if (threadCounter == 2) break;
            ImdbImageFinder scraper = new ImdbImageFinder(s, threadCounter);
            threadList.add(threadCounter, scraper.getThread());
            threadCounter++;
        }
        try {
            threadList.get(0).join();
            threadList.get(1).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> ImdbImages = new ArrayList<>(ImdbImageFinder.getImages());
        return ImdbImages;
    }
}
