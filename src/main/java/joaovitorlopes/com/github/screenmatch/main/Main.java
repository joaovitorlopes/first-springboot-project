package joaovitorlopes.com.github.screenmatch.main;

import io.github.cdimascio.dotenv.Dotenv;
import joaovitorlopes.com.github.screenmatch.model.Series;
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

    Dotenv dotenv = Dotenv.load();
    String omdbApiKey = dotenv.get("OMDB_API_KEY");

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey="+omdbApiKey;

    private List<SeriesData> seriesData = new ArrayList<>();

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search Series
                    2 - Search Episodes
                    3 - List Searched Series
            
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
        seriesData.add(data);
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
        SeriesData seriesData = getSeriesData();
        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            var json = consumeAPI.getData(ADDRESS + seriesData.title().replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = conversion.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }
        seasons.forEach(System.out::println);
    }

    private void listSearchedSeries() {

        List<Series> seriesList = new ArrayList<>();
        seriesList = seriesData.stream()
                        .map(s -> new Series(s))
                                .collect(Collectors.toList());
        seriesList.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }
}