package com.eulerity.hackathon.imagefinder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(
    name = "ImageFinder",
    urlPatterns = {"/main"}
)
public class ImageFinder extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected static final Gson GSON = new GsonBuilder().create();

	//This is just a test array
	public static final String[] testImages = {
			"https://images.pexels.com/photos/545063/pexels-photo-545063.jpeg?auto=compress&format=tiny",
			"https://images.pexels.com/photos/464664/pexels-photo-464664.jpeg?auto=compress&format=tiny",
			"https://images.pexels.com/photos/406014/pexels-photo-406014.jpeg?auto=compress&format=tiny",
			"https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&format=tiny",
			"https://m.media-amazon.com/images/M/MV5BODA5ODY3NjY5OV5BMl5BanBnXkFtZTgwOTcyMzkxMzE@._V1_.jpg",
			"https://m.media-amazon.com/images/M/MV5BODM4NzkyNjcxOV5BMl5BanBnXkFtZTgwMDgyMzkxMzE@._V1_.jpg",
			"https://m.media-amazon.com/images/M/MV5BMTM5NTc3NTg2NV5BMl5BanBnXkFtZTgwODAwOTE4MDE@._V1_.jpg"
  };
	public static final String[] imdbImages = {
			"https://m.media-amazon.com/images/M/MV5BODA5ODY3NjY5OV5BMl5BanBnXkFtZTgwOTcyMzkxMzE@._V1_.jpg",
			"https://m.media-amazon.com/images/M/MV5BODM4NzkyNjcxOV5BMl5BanBnXkFtZTgwMDgyMzkxMzE@._V1_.jpg",
			"https://m.media-amazon.com/images/M/MV5BMTM5NTc3NTg2NV5BMl5BanBnXkFtZTgwODAwOTE4MDE@._V1_.jpg"
	};

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {







		resp.setContentType("text/json");
		String path = req.getServletPath();
		String url = req.getParameter("url");
		if("".equals(url)){
			System.out.println("enter a url");
//		}else if(url.equals("imdb")){
//			System.out.println("Got request of:" + path + " with query param:" + url);
//			resp.getWriter().print(GSON.toJson(imdbImages));
		}else {
			System.out.println("Got request of:" + path + " with query param:" + url);
			resp.getWriter().print(GSON.toJson(imdbImages));
		}
	}
}

