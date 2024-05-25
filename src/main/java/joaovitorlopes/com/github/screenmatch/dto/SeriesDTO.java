package joaovitorlopes.com.github.screenmatch.dto;

import joaovitorlopes.com.github.screenmatch.model.Category;

public record SeriesDTO(Long id, String title, Integer totalSeasons, Double rating, Category genre, String actors,
                        String plot, String poster) {

}
