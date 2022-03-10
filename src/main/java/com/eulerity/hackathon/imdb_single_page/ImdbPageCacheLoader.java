package com.eulerity.hackathon.imdb_single_page;

import com.google.common.cache.CacheLoader;

import java.util.List;

public class ImdbPageCacheLoader extends CacheLoader<String, List<String>> {
    @Override
    public List<String> load(String pageUrl){

        ImdbPageImageFinder scraper = new ImdbPageImageFinder(pageUrl);
        return scraper.getImages();
    }
}
