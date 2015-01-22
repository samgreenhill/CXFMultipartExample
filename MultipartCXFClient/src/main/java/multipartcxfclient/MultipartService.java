package multipartcxfclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class MultipartService {
	
	List<Object> providers = new ArrayList<Object>();

	public MultipartService() {
		providers.add(new JacksonJaxbJsonProvider());
	}

	public String[] getDocuments() {
		WebClient client = WebClient.create("http://localhost:9000/document", providers);
		Collection<? extends Document> docs = client.accept(MediaType.APPLICATION_JSON).getCollection(Document.class);

		List<String> titles = new ArrayList<String>();
		for (Document d : docs) {
			titles.add(d.getTitle());
		}
		return titles.toArray(new String[titles.size()]);
	}

	public String getDocument(int id) {
		WebClient client = WebClient.create("http://localhost:9000/document", providers);
		return client.accept(MediaType.APPLICATION_JSON).post(id, Document.class).getTitle();
	}

	public String render(int id) {
		WebClient client = WebClient.create("http://localhost:9000/document/render", providers);
		
		// Retrieve the MultipartBody in the response
		MultipartBody image = client.accept("multipart/mixed").post(id, MultipartBody.class);
		if(image.getRootAttachment().getContentType().isCompatible(MediaType.APPLICATION_OCTET_STREAM_TYPE)) {
			// The attachment a binary stream?
			try {
				// Just convert the InputStream to a string as a test
				return new BufferedReader(new InputStreamReader(image.getRootAttachment().getObject(InputStream.class))).readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			// Otherwise the attachment is a string
			return image.getRootAttachment().getObject(String.class);
		}
	}
}
