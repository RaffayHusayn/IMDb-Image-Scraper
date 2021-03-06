package com.eulerity.hackathon.imagefinder;

import com.eulerity.hackathon.imdb_single_page.ImdbPageCacheLoader;
import com.eulerity.hackathon.imdb_user_list.ImdbListCacheLoader;
import com.eulerity.hackathon.unsplash.UnsplashCacheLoader;
import com.eulerity.hackathon.unsplash_all_categories.UnsplashAllCategoryCacheLoader;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@WebServlet(
        name = "ImageFinder",
        urlPatterns = {"/main"}
)
public class ImageFinder extends HttpServlet {
    protected static final Gson GSON = new GsonBuilder().create();
    private static final long serialVersionUID = 1L;

    /*
       * Initializing all the Guava Caches.
       * Each cache has the capacity of 50 entries.
       * Entries expire after 60 minute.
     */
    LoadingCache<String, List<String>> imdbListCache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new ImdbListCacheLoader());
    LoadingCache<String, List<String>> imdbPageCache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new ImdbPageCacheLoader());
    LoadingCache<String, List<String>> unsplashCache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new UnsplashCacheLoader());
    LoadingCache<String, List<String>> unsplashAllCategoryCache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new UnsplashAllCategoryCacheLoader());

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/json");
        String path = req.getServletPath();
        String url = req.getParameter("url");
        String imdbListString = "https://www.imdb.com/list/";
        String imdbPageString = "https://www.imdb.com/title/";
        String unsplashString = "https://unsplash.com/t/";
        String unsplashAllCatString = "https://unsplash.com";

        /*
            * Conditions to call the right cache object
            * if cache doesn't have the entry already then scrape
            * if cache has an unexpired entry then just return without scraping again

         */
        if (url.length() >=Math.min(unsplashAllCatString.length(),Math.min(imdbPageString.length(),imdbListString.length()))) {
            if (url.startsWith(imdbListString)) {
                System.out.println("Got request of:" + path + " with query param:" + url);

                List<String> ImdbListImages = null;
                try {
                    ImdbListImages = imdbListCache.get(url);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                resp.getWriter().print(GSON.toJson(ImdbListImages));
            }else if(url.startsWith(imdbPageString)) {

                List<String> ImdbPageImages = null;
                try {
                    ImdbPageImages = imdbPageCache.get(url);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                resp.getWriter().print(GSON.toJson(ImdbPageImages));

            }else if(url.startsWith(unsplashString)){


                List<String> unsplashImages = null;
                try {
                unsplashImages = unsplashCache.get(url);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                resp.getWriter().print(GSON.toJson(unsplashImages));

            }else if(url.startsWith(unsplashAllCatString)){
                List<String> unsplashAllCatImages = null;
                try {
                    unsplashAllCatImages = unsplashAllCategoryCache.get(url);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                resp.getWriter().print(GSON.toJson(unsplashAllCatImages));

            } else {
                System.out.println("==============Incorrect url==============");
            }

        } else {
            System.out.println("============Enter a url=============");

        }
    }
}


