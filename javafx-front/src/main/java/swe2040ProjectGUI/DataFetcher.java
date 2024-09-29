package swe2040ProjectGUI;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataFetcher {

	private OkHttpClient client = new OkHttpClient();
	
	private ObjectMapper json = new ObjectMapper();
	
	
	public List<Music> fetchAllLyrics() {
	
		try {
			Properties properties = getProperties();
			String serverIp = properties.getProperty("server_ip");
			int serverPort = Integer.valueOf(properties.getProperty("server_port"));
			String url = "http://"+ serverIp+":"+ serverPort+ "/api/all";
			
			Request request = new Request.Builder().url(url).get().build();
			Response response = client.newCall(request).execute();
			String responseJson = response.body().string();
			Thread.sleep(1000);
			return Arrays.asList(json.readValue(responseJson, Music[].class));
		} 
		catch (Exception ex) {
		    ex.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	
	public Music postNewLyric(Music newMusic) {
		Properties properties = getProperties();
		String serverIp = properties.getProperty("server_ip");
		int serverPort = Integer.valueOf(properties.getProperty("server_port"));
		String url = "http://"+ serverIp+":"+ serverPort+ "/api/create";
		try {
			RequestBody body = RequestBody.create(json.writeValueAsString(newMusic), MediaType.get("application/json"));
			Request request = new Request.Builder().url(url).post(body).build();
			Response response = client.newCall(request).execute();
			response.close();
			if(response.isSuccessful())
				return newMusic;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Music editExistingLyric(Music newMusic) {
		Properties properties = getProperties();
		String serverIp = properties.getProperty("server_ip");
		int serverPort = Integer.valueOf(properties.getProperty("server_port"));
		String url = "http://"+ serverIp+":"+ serverPort+ "/api/edit/"+newMusic.getId();

		try {
			RequestBody body = RequestBody.create(json.writeValueAsString(newMusic), MediaType.get("application/json"));
			Request request = new Request.Builder().url(url).put(body).build();
			Response response  = client.newCall(request).execute();
			response.close();
			if(response.isSuccessful())
				return newMusic;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Music deleteLyric(Music newMusic) {
		Properties properties = getProperties();
		String serverIp = properties.getProperty("server_ip");
		int serverPort = Integer.valueOf(properties.getProperty("server_port"));
		String url = "http://"+ serverIp+":"+ serverPort+ "/api/deletemusic/"+newMusic.getId();

		try {
			RequestBody body = RequestBody.create(json.writeValueAsString(newMusic), MediaType.get("application/json"));
			Request request = new Request.Builder().url(url).delete(body).build();
			Response response  = client.newCall(request).execute();
			response.close();
			if(response.isSuccessful())
				return newMusic;
			return newMusic;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Properties getProperties() {
		Properties prop = new Properties();
		try {
			prop.load(DataFetcher.class.getClassLoader().getResourceAsStream("application.properties"));
			return prop;
		} 
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		return null;
	}

}
