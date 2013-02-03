package example;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import digitaloceanapi.DigitalOceanAPI;

public class ShowAllActiveDroplets {

	
	
	public static void main(String args[]){
		
		String clientKey="zywZubN7MaAnbcE9zuaAO";
		String apiKey="cPt5AvhWMZxLTHeZ9USeoEs8wrd8GkKVGEyYyCQjI";
		
		DigitalOceanAPI  api=new DigitalOceanAPI(clientKey,apiKey);
		
		try {
			api.showAllActiveDroplets();
			api.destroyDroplet(94820);
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
