package joaovitorlopes.com.github.screenmatch;

import joaovitorlopes.com.github.screenmatch.model.SeriesData;
import joaovitorlopes.com.github.screenmatch.service.ConsumeAPI;
import joaovitorlopes.com.github.screenmatch.service.DataConversion;
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
		var json = consumeAPI.getData("https://www.omdbapi.com/?t=fallout&apikey=5c08242");
//		System.out.println(json);
//		json = consumeAPI.getData("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		System.out.println("-------\n");
		DataConversion conversion = new DataConversion();
		SeriesData data = conversion.getData(json, SeriesData.class);
		System.out.println(data);
	}
}
