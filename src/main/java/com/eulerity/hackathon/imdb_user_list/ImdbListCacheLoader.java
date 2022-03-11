package com.eulerity.hackathon.imdb_user_list;

import com.google.common.cache.CacheLoader;

import java.util.ArrayList;
import java.util.List;
/*
Cache for IMDb User List
 */
public class ImdbListCacheLoader extends CacheLoader<String, List<String>> {
    @Override
    public List<String> load(String movieUrl) throws Exception {
        ImdbGetPagesFromList list = new ImdbGetPagesFromList(movieUrl);
        List<String> pageList = list.getPageUrl();
        int totalMoviesScraped= 0;
        List<Thread> threadList = new ArrayList<>();

        /*
         * scrapes the first 10 movies of the userList with 2 threads in 5 iterations
         *  ------------------------------------------------------------------------------------------------------------------
         * | NOTE : scraping IMDb with more than 2 threads at a time sends too many requests resulting in a temporary ban   |
         * |         Here I am only using 2 threads for that reason, which makes the scraping slow but site friendly        |
         * ------------------------------------------------------------------------------------------------------------------
         */
        for(int i =0 ; i<5; i++) {
            System.out.println("=======> Iteration "+ (i+1) + "/5");
            int threadCounter = 0;
            while(totalMoviesScraped < 10) {
                if (threadCounter == 2) break;
                ImdbUserListImageFinder scraper = new ImdbUserListImageFinder(pageList.get(totalMoviesScraped), threadCounter);
                threadList.add(threadCounter, scraper.getThread());
                threadCounter++;
                totalMoviesScraped++;
            }
            try {
                threadList.get(0).join();
                threadList.get(1).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<String> ImdbImages = new ArrayList<>(ImdbUserListImageFinder.getImages());
        return ImdbImages;
    }
}
