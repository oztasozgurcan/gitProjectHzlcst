package io.blaze.blazeApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("io.blaze.blazeApplication.repository")
@EntityScan("io.blaze.blazeApplication.model")
public class BlazeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlazeApplication.class, args);
	}

}

/*
 * String url = "https://api.github.com/users/oztasozgurcan/repos";
 * URL obj = new URL(url);
 * HTTPUrlConnection con = (HTTPUrlConnection) obj.openConnection();
 * con.setRequestMethod("GET");
 * con.setRequestProperty("User-Agent", "Mozilla/5.0");
 * BufferedReader in = new BufferedReader(
 * 		new InputStreamReader(con.getInputStream()));
 * String inputLine;	
 * StringBuffer Response = new StringBuffer();
 * while((inputLine = in.readLine()) != null){
 * 		response.append(inputLine);
 * } in.close();
 * 
 * System.out.println(response.toString());
 * 
*/