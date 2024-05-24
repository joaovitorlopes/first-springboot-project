package joaovitorlopes.com.github.screenmatch.repository;

import joaovitorlopes.com.github.screenmatch.model.Category;
import joaovitorlopes.com.github.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);

    List<Series> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actorName, Double rating);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Category category);

    List<Series> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer totalSeasons, Double rating);
}
