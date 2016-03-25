package es.upm.dit.isst;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(404);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String username = null;
			InputStream profilePic = null;
			String password = null;
			String completeName = null;
			String role = null;
			String email = null;
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
	                switch (fieldName){
		                case "username":
		                	username = fieldValue;
		                	break;
		                case "password":
		                	password = fieldValue;
		                	break;
		                case "completeName":
		                	completeName = fieldValue;
		                	break;
		                case "role":
		                	role = fieldValue;
		                	break;
		                case "email":
		                	email = fieldValue;
		                	break;
	                }
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                //String fileName = FilenameUtils.getName(item.getName());
	                InputStream fileContent = item.getInputStream();
	                switch (fieldName){
	                case "profilePic":
	                	profilePic = fileContent;
	                	break;
	                }
	            }
	        }
	        if(username!=null&&profilePic!=null&&password!=null&&completeName!=null&&role!=null&&email!=null){
	        	
	        }else{
	        	resp.sendError(400);
	        }
	    } catch (FileUploadException e) {
	        throw new ServletException("Cannot parse multipart request.", e);
	    }
	}
	
}
