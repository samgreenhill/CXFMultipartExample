package multipartspringclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultipartController {
	
	@Autowired
	private MultipartService service;

	@RequestMapping("/document")
	public String[] getDocuments() {
		return service.getDocuments();
	}
	
	@RequestMapping("/document/{id}")
	public String getDocument(@PathVariable("id") int id) {
		return service.getDocument(id);
	}
	
	@RequestMapping("/document/{id}/render")
	public String render(@PathVariable("id") int id) {
		return service.render(id);
	}	
}
