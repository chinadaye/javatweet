package jtweet.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class CleanupSessionsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("_ah_SESSION");
		query.setKeysOnly();
		PreparedQuery results = datastore.prepare(query);

		resp.getOutputStream().println("Deleting " + results.countEntities() + " sessions from datastore");
		int cleared = 0;
		try {
			for (Entity session : results.asIterable(FetchOptions.Builder.withLimit(400))) {				
				datastore.delete(session.getKey());
				cleared++;
			}
		} catch (Throwable e) {
			resp.getOutputStream().println(e.getMessage());
		}
	}
}
