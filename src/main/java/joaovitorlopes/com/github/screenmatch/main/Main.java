package joaovitorlopes.com.github.screenmatch.main;

import joaovitorlopes.com.github.screenmatch.model.Episode;
import joaovitorlopes.com.github.screenmatch.model.Series;
import joaovitorlopes.com.github.screenmatch.model.SeriesData;
import joaovitorlopes.com.github.screenmatch.repository.SeriesRepository;
import joaovitorlopes.com.github.screenmatch.service.ConsumeAPI;
import joaovitorlopes.com.github.screenmatch.service.DataConversion;
import joaovitorlopes.com.github.screenmatch.model.SeasonData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner reading = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private DataConversion conversion = new DataConversion();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_APIKEY");

    private List<SeriesData> seriesData = new ArrayList<>();

    private SeriesRepository repository;

    private List<Series> series = new ArrayList<>();

    public Main(SeriesRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search Series
                    2 - Search Episodes
                    3 - List Searched Series
                    4 - Search Series by Title
                                
                    0 - Exit
                    """;

            System.out.println(menu);
            option = reading.nextInt();
            reading.nextLine();

            switch (option) {
                case 1:
                    searchSeriesWeb();
                    break;
                case 2:
                    searchEpisodeBySeries();
                    break;
                case 3:
                    listSearchedSeries();
                    break;
                case 4:
                    searchSeriesByTitle();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void searchSeriesWeb() {
        SeriesData data = getSeriesData();
        Series series = new Series(data);
        repository.save(series);
        System.out.println(data);
    }

    private SeriesData getSeriesData() {
        System.out.println("Enter a series name to search:");
        var seriesName = reading.nextLine();
        var json = consumeAPI.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        SeriesData seriesData = conversion.getData(json, SeriesData.class);
        return seriesData;
    }

    private void searchEpisodeBySeries() {
        listSearchedSeries();
        System.out.println("Choice a series by name: ");
        var seriesName = reading.nextLine();

        Optional<Series> serie = repository.findByTitleContainingIgnoreCase(seriesName);

        if (serie.isPresent()) {

            var foundSeries = serie.get();
            List<SeasonData> seasons = new ArrayList<>();

            for (int i = 1; i <= foundSeries.getTotalSeasons(); i++) {
                var json = consumeAPI.getData(ADDRESS + foundSeries.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData seasonData = conversion.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(s -> s.episodes().stream()
                            .map(e -> new Episode(s.season(), e)))
                    .collect(Collectors.toList());

            foundSeries.setEpisodes(episodes);
            repository.save(foundSeries);
        } else {
            System.out.println("Series not found!");
        }
    }

    private void listSearchedSeries() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }

    private void searchSeriesByTitle() {
        System.out.println("Choice a series by name: ");
        var seriesName = reading.nextLine();
        Optional<Series> searchedSeries = repository.findByTitleContainingIgnoreCase(seriesName);

        if (searchedSeries.isPresent()) {
            System.out.println("Series Data: " + searchedSeries.get());

        } else {
            System.out.println("Series not found!");
        }

    }
}