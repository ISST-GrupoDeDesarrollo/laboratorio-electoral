package es.upm.dit.isst;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;

@SuppressWarnings("serial")
public class GetBlobServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String blobKey = req.getParameter("blobkey");
		if(blobKey!=null){
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	        blobstoreService.serve(new BlobKey(blobKey), resp);
		}else{
			resp.sendError(404);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(404);
	}
	
}
