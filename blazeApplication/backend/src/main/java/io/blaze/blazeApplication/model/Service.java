package io.blaze.blazeApplication.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class Service {
	
	public Repository[] repositoryLoader(String name) {
		try {

			  String url = "https://api.github.com/users/" + name + "/repos";
			  URL obj = new URL(url);
			  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			  con.setRequestMethod("GET");
			  con.setRequestProperty("User-Agent", "Mozilla/5.0");
			  BufferedReader in = new BufferedReader(
			  		new InputStreamReader(con.getInputStream()));
			  String inputLine;
			  StringBuffer response = new StringBuffer();
			  while((inputLine = in.readLine()) != null){
			  		response.append(inputLine);
			  } in.close();
			  
			  Gson gson = new Gson();
			  Repository[] repolist = gson.fromJson(response.toString(), Repository[].class);
			  return repolist;
			  
		} catch(Exception e) {
			return null;
		}
	}
}
