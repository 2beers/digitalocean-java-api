package digitalocean.digitaloceanapi;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import digitalocean.droplet.Droplet;
import digitalocean.images.DropletImage;
import digitalocean.region.DropletRegion;
import digitalocean.size.DropletSize;

public class DigitalOceanAPI {

	public final static String API_HOST = "api.digitalocean.com";

	private String CLIENT_KEY;

	private String API_KEY;

	private DefaultHttpClient httpclient;

	public DigitalOceanAPI(String clientKey, String apiKey) {
		this.setCLIENT_KEY(clientKey);
		this.setAPI_KEY(apiKey);

		this.httpclient = new DefaultHttpClient();
	}

	public String getCLIENT_KEY() {
		return CLIENT_KEY;
	}

	public void setCLIENT_KEY(String cLIENT_KEY) {
		CLIENT_KEY = cLIENT_KEY;
	}

	public String getAPI_KEY() {
		return API_KEY;
	}

	public void setAPI_KEY(String aPI_KEY) {
		API_KEY = aPI_KEY;
	}

	public HashMap<Integer, Droplet> showAllActiveDroplets()
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);

		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();
		JsonArray droplets = jobject.getAsJsonArray("droplets");

		if (status.equals("OK")) {

			HashMap<Integer, Droplet> map = new HashMap<Integer, Droplet>();

			for (int i = 0; i < droplets.size(); i++) {
				jobject = droplets.get(i).getAsJsonObject();
				int id = jobject.get("id").getAsInt();
				String name = jobject.get("name").getAsString();
				int imageID = jobject.get("image_id").getAsInt();
				int sizeID = jobject.get("size_id").getAsInt();
				int regionID = jobject.get("region_id").getAsInt();
				String ip = jobject.get("ip_address").getAsString();
				String dropletStatus = jobject.get("status").getAsString();

				map.put(id, new Droplet(id, name, imageID, regionID, sizeID,
						ip, dropletStatus));
			}

			return map;

		} else {
			return null;
		}

	}

	public Integer newDroplet(String name, int sizeID, int imageID, int regionID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/new")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY)
				.setParameter("name", name)
				.setParameter("size_id", sizeID + "")
				.setParameter("region_id", regionID + "")
				.setParameter("image_id", imageID + "");
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();
		JsonObject droplet = jobject.getAsJsonObject("droplet");

		if (status.equals("OK")) {
			int id = droplet.get("id").getAsInt();
			return id;

		} else {
			return null;
		}

	}
	
	
	
	public Integer destroyDroplet(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/destroy")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	public Integer rebootDroplet(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/reboot")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public Integer powerCycleDroplet(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/power_cycle")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public Integer shutDownDroplet(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/shutdown")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	public Integer powerOffDroplet(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/power_off")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	public Integer powerOnDroplet(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/power_on")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	public Integer resetRootPassword(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/reset_root_password")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public Integer resizeDroplet(int ID,int sizeID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/resize")
				.setParameter("size_id", sizeID+"")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public Integer takeSnapshot(int ID,String name)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/snapshot")
				.setParameter("name", name)
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public Integer restoreDroplet(int ID,int imageID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/restore")
				.setParameter("image_id", imageID+"")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	
	public Integer rebuildDroplet(int ID,int imageID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/rebuild")
				.setParameter("image_id", imageID+"")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public Integer enableAutomaticBackups(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/enable_backups")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}


		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public Integer disableAutomaticBackups(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/droplets/"+ID+"/disable_backups")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}

		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int eventID = jobject.get("event_id").getAsInt();
			System.out.println(eventID);
			return eventID;
		} else {
			return null;
		}

	}
	
	
	
	public HashMap<Integer,DropletRegion> allRegions()
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/regions")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}

		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			HashMap<Integer, DropletRegion> map=new HashMap<Integer, DropletRegion>();
			JsonArray regions=jobject.get("regions").getAsJsonArray();
			for(int i=0;i<regions.size();i++){
				int id = regions.get(i).getAsJsonObject().get("id").getAsInt();
				String name = regions.get(i).getAsJsonObject().get("name").getAsString();
				map.put(id, new DropletRegion(id, name));
			}
			return map;
		} else {
			return null;
		}

	}
	
	
	
	public HashMap<Integer,DropletImage> allImages()
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/images")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}

		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			HashMap<Integer, DropletImage> map=new HashMap<Integer, DropletImage>();
			JsonArray images=jobject.get("images").getAsJsonArray();
			for(int i=0;i<images.size();i++){
				int id = images.get(i).getAsJsonObject().get("id").getAsInt();
				String name = images.get(i).getAsJsonObject().get("name").getAsString();
				String distribution = images.get(i).getAsJsonObject().get("distribution").getAsString();
				map.put(id, new DropletImage(id, name,distribution));
			}
			return map;
		} else {
			return null;
		}

	}
	
	
	
	public DropletImage showImage(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/images/"+ID)
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}

		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			int id = jobject.get("image").getAsJsonObject().get("id").getAsInt();
			String name = jobject.get("image").getAsJsonObject().get("name").getAsString();
			String distribution = jobject.get("image").getAsJsonObject().get("distribution").getAsString();
			return new DropletImage(id, name, distribution);
		} else {
			return null;
		}

	}
	
	
	public Boolean destroyImage(int ID)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/images/"+ID+"/destroy")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}

		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			return true;
		} else {
			return false;
		}

	}
	
	
	
	public HashMap<Integer,DropletSize> allSizes()
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(DigitalOceanAPI.API_HOST)
				.setPath("/sizes")
				.setParameter("client_id", this.CLIENT_KEY)
				.setParameter("api_key", this.API_KEY);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder content = new StringBuilder();

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						instream));
				try {
					String line;
					while ((line = bf.readLine()) != null) {
						content.append(line);
					}
				} finally {
					instream.close();
					bf.close();
				}
			}
		} finally {
			httpGet.releaseConnection();
		}

		JsonElement jelement = new JsonParser().parse(content.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		String status = jobject.get("status").getAsString();

		if (status.equals("OK")) {
			HashMap<Integer, DropletSize> map=new HashMap<Integer, DropletSize>();
			JsonArray sizes=jobject.get("sizes").getAsJsonArray();
			for(int i=0;i<sizes.size();i++){
				int id = sizes.get(i).getAsJsonObject().get("id").getAsInt();
				String name = sizes.get(i).getAsJsonObject().get("name").getAsString();
				map.put(id, new DropletSize(id, name));
			}
			return map;
		} else {
			return null;
		}

	}

}
