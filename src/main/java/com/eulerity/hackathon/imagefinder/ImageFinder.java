package com.eulerity.hackathon.imagefinder;

import com.eulerity.hackathon.imdb_single_page.ImdbPageCacheLoader;
import com.eulerity.hackathon.imdb_single_page.ImdbPageImageFinder;
import com.eulerity.hackathon.imdb_user_list.ImdbUserListImageFinder;
import com.eulerity.hackathon.imdb_user_list.ImdbListCacheLoader;
import com.eulerity.hackathon.unsplash.UnsplashCacheLoader;
import com.eulerity.hackathon.unsplash.UnsplashImageFinder;
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
import java.util.ArrayList;
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

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/json");
        String path = req.getServletPath();
        String url = req.getParameter("url");
        String imdbListString = "https://www.imdb.com/list/";
        String imdbPageString = "https://www.imdb.com/title/";
        String unsplashString = "https://unsplash.com";

        if (url.length() >=Math.min(unsplashString.length(),Math.min(imdbPageString.length(),imdbListString.length()))) {
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

            }else {
                System.out.println("incorrect url");
            }

        } else {
            System.out.println("enter a url");

        }
    }
}


