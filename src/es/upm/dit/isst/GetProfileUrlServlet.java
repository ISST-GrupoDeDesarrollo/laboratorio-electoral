package es.upm.dit.isst;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import es.upm.dit.isst.lab.tools.Tools;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class GetProfileUrlServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		ResponseWrapper response = new ResponseWrapper();
		response.url = blobstoreService.createUploadUrl("/api/profile");
		
		Tools.sendJson(resp,response,ResponseWrapper.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(404);
	}
	
	public static class ResponseWrapper{
		String url;
	}
	
}
