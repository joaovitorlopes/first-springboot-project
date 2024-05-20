package joaovitorlopes.com.github.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(@JsonAlias("Title") String title, @JsonAlias("totalSeasons") Integer totalSeasons, @JsonAlias("imdbRating") String rating,
                         @JsonAlias("Genre") String genre, @JsonAlias("Actors") String actors, @JsonAlias("Plot") String plot,
                         @JsonAlias("Poster") String poster){
}
