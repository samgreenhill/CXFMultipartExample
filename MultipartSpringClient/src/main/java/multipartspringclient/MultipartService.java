package multipartspringclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MultipartService {
	
	public MultipartService() {
		//
	}

	public String[] getDocuments() {
		RestTemplate restTemplate = new RestTemplate();
		Document[] docs = restTemplate.getForObject("http://localhost:9000/document", Document[].class);
		
		List<String> titles = new ArrayList<>();
		for(Document d : docs) {
			titles.add(d.getTitle());
		}
		return titles.toArray(new String[titles.size()]);
	}

	public String getDocument(int id) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject("http://localhost:9000/document", id, Document.class).getTitle();
	}

	public String render(int id) {
		RestTemplate restTemplate = new RestTemplate();
		// Retrieve the response as a string, but really want it as a multipart body to retrieve the attachments, although
		// it seems the spring restTemplate doesn't have a multipart reader.
		String ret = restTemplate.postForObject("http://localhost:9000/document/render", id, String.class);
		return ret;
	}
}
