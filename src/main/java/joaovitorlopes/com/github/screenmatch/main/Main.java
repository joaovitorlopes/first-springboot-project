package joaovitorlopes.com.github.screenmatch.main;

import joaovitorlopes.com.github.screenmatch.model.EpisodeData;
import joaovitorlopes.com.github.screenmatch.model.SeriesData;
import joaovitorlopes.com.github.screenmatch.service.ConsumeAPI;
import joaovitorlopes.com.github.screenmatch.service.DataConversion;
import joaovitorlopes.com.github.screenmatch.model.SeasonData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner reading = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private DataConversion conversion = new DataConversion();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=5c08242";

    public void showMenu() {
        System.out.println("Enter a series name to search:");
        var seriesName = reading.nextLine();
        var json = consumeAPI.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        SeriesData seriesData = conversion.getData(json, SeriesData.class);
        System.out.println(seriesData);

        List<SeasonData> seasons = new ArrayList<>();

		for (int i = 1; i <= seriesData.totalSeasons(); i++) {
			json = consumeAPI.getData(ADDRESS + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
			SeasonData seasonData = conversion.getData(json, SeasonData.class);
			seasons.add(seasonData);
		}

		seasons.forEach(System.out::println);
        System.out.println("----EPISODES NAME----");
        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));
        System.out.println("----EPISODES NAME----");

        List<EpisodeData> episodeData = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("----TOP 5 EPISODES----");
        episodeData.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);
        System.out.println("----TOP 5 EPISODES----");
    }
}