package multipartcxfclient;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class MultipartCXFClient {
	
	private MultipartService service = new MultipartService();

	public MultipartCXFClient() throws IOException {
	}

	@GET
	@Path("/document/")
	@Produces(MediaType.APPLICATION_JSON)
	public String[] getDocuments() {
		return service.getDocuments();
	}
	
	@GET
	@Path("/document/{id}/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDocument(@PathParam("id") int id) {
		return service.getDocument(id);
	}

	@GET
	@Path("/document/{id}/render")
	@Produces(MediaType.TEXT_PLAIN)
	public String render(@PathParam("id") int id) {
		return service.render(id);
	}
	
}
