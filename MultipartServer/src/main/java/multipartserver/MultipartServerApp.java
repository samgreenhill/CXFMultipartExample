package multipartserver;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class MultipartServerApp {
	
	private MultipartServerApp() throws Exception {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(MultipartService.class);
		sf.setResourceProvider(MultipartService.class, new SingletonResourceProvider(new MultipartService()));
		sf.setAddress("http://localhost:9000/");
		
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());
		sf.setProviders(providers);

		sf.create();
	}

	public static void main(String[] args) throws Exception {
		new MultipartServerApp();
		System.out.println("Server ready...");

		Thread.sleep(5 * 6000 * 1000);
		System.out.println("Server exiting");
		System.exit(0);
	}
}
