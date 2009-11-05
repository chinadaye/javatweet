package jtweet.gae;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import jtweet.ShortUrl;




public final class GStore {
	
	private static Logger log = Logger.getLogger(GStore.class.getName());
	
	@SuppressWarnings("unchecked")
	public static ShortUrl getShortUrlByUrl(String url) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(ShortUrl.class);
		query.setFilter("longUrl == Param");
		query.declareParameters("String Param");
		query.setRange(0, 1);
		try {
	        List<ShortUrl> results = (List<ShortUrl>) query.execute(url);
	        if(results!=null&&results.size()>0){
	        	return results.get(0);
	        }
	        return null;
	    }catch (Exception e) {
	    	log.warning(e.getMessage());
			return null;
		} finally {
	        query.closeAll();
	    }
	}
	
	public static ShortUrl getShortUrl(long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			return pm.getObjectById(ShortUrl.class, new Long(id));
		} catch (JDOObjectNotFoundException e) {
			log.warning(e.getMessage());
			return null;
		} catch (Exception e) {
	    	log.warning(e.getMessage());
			return null;
		}finally {
			pm.close();
		}
	}
	
	public static void Save(Object obj) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			pm.makePersistent(obj);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}
	
	public static Object SaveAndGet(Object obj) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			obj = pm.makePersistent(obj);
			tx.commit();
			return obj;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}

}
