package joaovitorlopes.com.github.screenmatch.main;

import joaovitorlopes.com.github.screenmatch.model.EpisodeData;
import joaovitorlopes.com.github.screenmatch.model.Episode;
import joaovitorlopes.com.github.screenmatch.model.SeriesData;
import joaovitorlopes.com.github.screenmatch.service.ConsumeAPI;
import joaovitorlopes.com.github.screenmatch.service.DataConversion;
import joaovitorlopes.com.github.screenmatch.model.SeasonData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        System.out.println("\n----EPISODES NAME----");
        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));
        System.out.println("----EPISODES NAME----\n");

        List<EpisodeData> episodeData = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("----TOP 5 EPISODES----");
        // use peek to debug streams
        // .peek(e -> System.out.println("First filter(N/A) " + e))
        episodeData.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .map(e -> e.title().toUpperCase())
                .forEach(System.out::println);
        System.out.println("----TOP 5 EPISODES----\n");

        List<Episode> episodes = seasons.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(d -> new Episode(s.season(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

//        System.out.println("Enter a part of the title to search for an episode:");
//        var titleExcerpt = reading.nextLine();
//
//        // Optional is a container
//        Optional<Episode> searchedEpisode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(titleExcerpt.toUpperCase()))
//                .findFirst(); //Final operation (requires return)
//        if (searchedEpisode.isPresent()) {
//            System.out.println("Episode found!");
//            System.out.println("Title: " + searchedEpisode.get().getTitle());
//            System.out.println("Season: " + searchedEpisode.get().getSeason());
//        } else {
//            System.out.println("Episode not found!");
//        }

//        System.out.println("Enter a year would you like to view the episodes:");
//        var year = reading.nextInt();
//        reading.nextLine();
//
//        LocalDate searchDate = LocalDate.of(year, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes.stream()
//                .filter(e -> e.getReleasedData() != null && e.getReleasedData().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getSeason() +
//                                " Episode: " + e.getTitle() +
//                                " ReleasedData: " + e.getReleasedData().format(formatter)
//                ));

        System.out.println("\n-----Rating by Seasons-----");
        Map<Integer, Double> ratingBySeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));
        System.out.println(ratingBySeason);
        System.out.println("-----Rating by Seasons-----\n");

        // Creating statistics
        System.out.println("\n-----Summary Statistics-----");
        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("Average Rating: " + est.getAverage());
        System.out.println("Best Rating: " + est.getMax());
        System.out.println("Worst Rating: " + est.getMin());
        System.out.println("Total Episodes: " + est.getCount());
        System.out.println("-----Summary Statistics-----\n");
    }
}
