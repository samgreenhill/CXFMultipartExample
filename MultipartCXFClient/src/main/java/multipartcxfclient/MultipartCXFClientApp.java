package multipartcxfclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class MultipartCXFClientApp {
	
	private MultipartCXFClientApp() throws Exception {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(MultipartCXFClient.class);
		sf.setResourceProvider(MultipartCXFClient.class, new SingletonResourceProvider(new MultipartCXFClient()));
		sf.setAddress("http://localhost:8090/");
		
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());
		sf.setProviders(providers);

		sf.create();
	}

	public static void main(String[] args) throws Exception {
		new MultipartCXFClientApp();
		System.out.println("Server ready...");

		Thread.sleep(5 * 6000 * 1000);
		System.out.println("Server exiting");
		System.exit(0);
	}
}
