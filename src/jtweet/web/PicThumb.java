package jtweet.web;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Image.Format;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

@SuppressWarnings("serial")
public class PicThumb extends HttpServlet {
	protected static Logger logger = Logger.getLogger(PicThumb.class
			.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		
		String id = req.getParameter("id");
		if(id != null)
		{
			try{
			Image newImage = this.cacheGet(id);
			resp.setContentType(this.getContentType(newImage.getFormat()));
			resp.getOutputStream().write(newImage.getImageData());
			}catch(Exception e){
				logger.warning(e.getMessage());
				resp.sendError(404);
			}
		}
		else
		{
			resp.sendError(404);
			return;
		}
	}
	
	protected Image cacheGet(String id) throws Exception {
		Image newImage = (Image) GCache.get("twitpic_thumb:"+id);
		if (newImage == null) {
			newImage = this.getImage("http://twitpic.com/show/thumb/"+id);
			if(newImage!=null){
			GCache.put("twitpic_thumb:"+id,newImage);
			}
		}
		return newImage;
	}
	
	protected Image getImage(String url) throws Exception {
		HTTPResponse respon = URLFetchServiceFactory.getURLFetchService()
				.fetch(new URL(url));
		return ImagesServiceFactory.makeImage(respon.getContent());
	}
	
	protected String getContentType(Format format) {
		switch (format) {
		case JPEG:
			return "image/jpeg";
		case BMP:
			return "image/bmp";
		case GIF:
			return "image/gif";
		case ICO:
			return "image/x-icon";
		case PNG:
			return "image/png";
		case TIFF:
			return "image/tiff";
		default:
			return "image/jpeg";
		}
	}

}
