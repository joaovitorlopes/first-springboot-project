package joaovitorlopes.com.github.screenmatch.main;

import joaovitorlopes.com.github.screenmatch.model.*;
import joaovitorlopes.com.github.screenmatch.repository.SeriesRepository;
import joaovitorlopes.com.github.screenmatch.service.ConsumeAPI;
import joaovitorlopes.com.github.screenmatch.service.DataConversion;

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

    private Optional<Series> searchSeries;

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
                    5 - Search Series by Actors
                    6 - TOP 5 Series
                    7 - Search Series by Category
                    8 - Search Series by Total Seasons
                    9 - Search Episode by Name
                    10 - TOP 5 Episodes By Series
                    11 - Search Episodes By Date
                                
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
                case 5:
                    searchSeriesByActor();
                    break;
                case 6:
                    searchTop5Series();
                    break;
                case 7:
                    searchSeriesByCategory();
                    break;
                case 8:
                    searchSeriesByTotalSeasons();
                    break;
                case 9:
                    searchEpisodeByName();
                    break;
                case 10:
                    searchTop5EpisodesBySeries();
                    break;
                case 11:
                    searchEpisodesByDate();
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
        searchSeries = repository.findByTitleContainingIgnoreCase(seriesName);

        if (searchSeries.isPresent()) {
            System.out.println("Series Data: " + searchSeries.get());

        } else {
            System.out.println("Series not found!");
        }
    }

    private void searchSeriesByActor() {
        System.out.println("Enter the actor's name:");
        var actorName = reading.nextLine();
        System.out.println("Enter a minimum rating to search series: ");
        var rating = reading.nextDouble();
        List<Series> seriesFound = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actorName, rating);
        System.out.println("Series in which the actor " + actorName + " worked: ");
        seriesFound.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void searchTop5Series() {
        List<Series> topSeries = repository.findTop5ByOrderByRatingDesc();
        topSeries.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void searchSeriesByCategory() {
        System.out.println("Enter a category/genre to search for series: ");
        var genreName = reading.nextLine();
        Category category = Category.fromPortuguese(genreName) ;
        List<Series> seriesByCategory = repository.findByGenre(category);
        System.out.println(genreName + " category/genre series: ");
        seriesByCategory.forEach(System.out::println);
    }

    private void searchSeriesByTotalSeasons() {
        System.out.println("Total seasons: ");
        var seasons = reading.nextInt();
        System.out.println("Enter a minimum rating to search series: ");
        var rating = reading.nextDouble();
        List<Series> seriesFound = repository.seriesByTotalSeasonsAndRating(seasons, rating);
        System.out.println("Series with " + seasons + " seasons and " + rating + " rating: ");
        seriesFound.forEach(s -> System.out.println(s.getTitle() + ", Total seasons: " + s.getTotalSeasons() + ", Rating: " + s.getRating()));
    }

    private void searchEpisodeByName() {
        System.out.println("Enter a name or fragment of the episode name: ");
        var episodeName = reading.nextLine();
        List<Episode> episodesFound = repository.episodesByName(episodeName);
        episodesFound.forEach(e ->
                System.out.printf("Series: %s, Season: %s, Episode: %s - %s\n",
                        e.getSeries().getTitle(), e.getSeason(),
                        e.getEpisodeNumber(), e.getTitle()));
    }

    private void searchTop5EpisodesBySeries() {
        searchSeriesByTitle();
        if (searchSeries.isPresent()) {
            Series series = searchSeries.get();
            List<Episode> topEpisodes = repository.top5EpisodesBySeries(series);
            topEpisodes.forEach(e ->
                    System.out.printf("Series: %s, Season: %s, Episode: %s - %s, Rating: %s\n",
                            e.getSeries().getTitle(), e.getSeason(),
                            e.getEpisodeNumber(), e.getTitle(), e.getRating()));
        }
    }

    private void searchEpisodesByDate() {
        searchSeriesByTitle();
        if (searchSeries.isPresent()) {
            Series series = searchSeries.get();
            System.out.println("Enter the year limit of the episode's release date: ");
            var releasedYear = reading.nextInt();
            reading.nextLine();

            List<Episode> episodesByYear = repository.findEpisodesBySeriesAndYear(series, releasedYear);
            episodesByYear.forEach(System.out::println);

        }
    }
}