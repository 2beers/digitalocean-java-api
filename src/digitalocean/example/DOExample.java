package digitalocean.example;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import digitalocean.digitaloceanapi.DigitalOceanAPI;

public class DOExample {

	
	
	public static void main(String args[]){
		
		String clientKey="";
		String apiKey="";
		
		DigitalOceanAPI  api=new DigitalOceanAPI(clientKey,apiKey);
		try {
			api.showAllActiveDroplets();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
