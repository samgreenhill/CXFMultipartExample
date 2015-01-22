package multipartserver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

public class MultipartService {

	private Document[] documents = new Document[2];
	private byte[] image = new byte[100];

	public MultipartService() throws IOException {
		documents[0] = new Document(1, "Document 1");
		documents[1] = new Document(2, "Document 2");
		
		// Represent the image as 100 random bytes
		new Random().nextBytes(image);
	}

	@GET
	@Path("/document/")
	@Produces(MediaType.APPLICATION_JSON)
	public Document[] getDocument() {
		return documents;
	}

	@POST
	@Path("/document/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDocument(int id) {
		if ((id >= 1) && (id <= documents.length)) {
			return Response.ok().entity(documents[id - 1]).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/document/render")
	@Produces("multipart/mixed")
	public Response render2(int id) {
		if ((id >= 1) && (id <= documents.length)) {
			// If the document can be found, create the attachment as a binary stream
			List<Attachment> atts = new LinkedList<Attachment>();
			atts.add(new Attachment("root", MediaType.APPLICATION_OCTET_STREAM, image));
			return Response.ok().entity(new MultipartBody(atts, true)).build();
		} else {
			// If the document cannot be found, create the attachment as a string
			List<Attachment> atts = new LinkedList<Attachment>();
			atts.add(new Attachment("root", MediaType.TEXT_PLAIN, "Cannot find document "+id));
			return Response.ok().entity(new MultipartBody(atts, true)).build();
		}
	}
}
