package io.blaze.blazeApplication.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.gson.*;

@RestController
public class Service {
	
	public String repositoryLoader(String name) {
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
			  return response.toString();
			  

		} catch(Exception e) {
			return "Error.";
		}
	}
}
