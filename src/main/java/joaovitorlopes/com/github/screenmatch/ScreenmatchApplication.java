package joaovitorlopes.com.github.screenmatch;

import joaovitorlopes.com.github.screenmatch.service.ConsumeAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumeAPI = new ConsumeAPI();
		var json = consumeAPI.getData("https://www.omdbapi.com/?t=fallout&Season=1&apikey=5c08242");
		System.out.println(json);
		json = consumeAPI.getData("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
	}
}
