package com.eulerity.hackathon.imagefinder;

import com.eulerity.hackathon.imdb.ImdbCacheLoader;
import com.eulerity.hackathon.imdb.ImdbImageFinder;
import com.eulerity.hackathon.imdb.ImdbLinkFromList;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
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
    LoadingCache<String, List<String>> imdbCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build(new ImdbCacheLoader());


    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/json");
        String path = req.getServletPath();
        String url = req.getParameter("url");
        String imdbString = "https://www.imdb.com/list/";

        if (url.length() >= imdbString.length()) {
            if (url.startsWith(imdbString)) {
                System.out.println("Got request of:" + path + " with query param:" + url);

                List<String> ImdbImages = null;
                try {
                    ImdbImages = imdbCache.get(url);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println(ImdbImages.size());
                //emptying the arraylist
                ImdbImageFinder.ImdbImages.clear();

                System.out.println(ImdbImageFinder.ImdbImages.size());
                System.out.println("Cache size : "+ imdbCache.size());
                System.out.println(imdbCache.asMap().keySet());

                resp.getWriter().print(GSON.toJson(ImdbImages));
            }else{
                System.out.println("incorrect url");
            }

        } else {
            System.out.println("enter a url");

        }
    }
}


