package com.eulerity.hackathon.unsplash;

import com.google.common.cache.CacheLoader;

import java.util.List;

public class UnsplashCacheLoader extends CacheLoader<String, List<String>> {
    @Override
    public List<String> load(String url) throws Exception {
        UnsplashImageFinder scraper = new UnsplashImageFinder(url);
        return scraper.getList();
    }
}
