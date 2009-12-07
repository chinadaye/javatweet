package jtweet.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreTimeoutException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class SessionCleanupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7819922897125461099L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String numString = req.getParameter("num"); 
		int limit = numString == null ? 375 : Integer.parseInt(numString); 
		Query query = new Query("_ah_SESSION"); 
		query.addFilter("_expires", FilterOperator.LESS_THAN, 
		System.currentTimeMillis()-604800000/*7*24*3600*1000*/); 
		query.setKeysOnly(); 
		ArrayList<Key> killList = new ArrayList<Key>(); 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(query); 
		Iterable<Entity> entities = pq.asIterable(FetchOptions.Builder.withLimit(limit)); 
		for(Entity expiredSession : entities) { 
		        Key key = expiredSession.getKey(); 
		    killList.add(key); 
		} 

		try { 
		        datastore.delete(killList); 
		} catch (DatastoreTimeoutException e) { 

		        resp.setStatus(200); 
		    try { 
		        resp.getWriter().println((new StringBuilder()).append("DatastoreTimeoutException on ").append(killList.size()).append("expired sessions.").toString()); 
		    } 
		    catch(IOException ex) { } 
		    return; 
		} 

		resp.setStatus(200); 
		try { 
		    resp.getWriter().println((new StringBuilder()).append("Cleared ").append(killList.size()).append(" expired sessions.").toString()); 
		} 

		catch(IOException ex) { } 
	}
}
