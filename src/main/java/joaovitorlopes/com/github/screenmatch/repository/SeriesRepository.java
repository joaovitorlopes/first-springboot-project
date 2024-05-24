package joaovitorlopes.com.github.screenmatch.repository;

import joaovitorlopes.com.github.screenmatch.model.Category;
import joaovitorlopes.com.github.screenmatch.model.Episode;
import joaovitorlopes.com.github.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    // Using Derived Queries
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);
    List<Series> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actorName, Double rating);
    List<Series> findTop5ByOrderByRatingDesc();
    List<Series> findByGenre(Category category);
    List<Series> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer totalSeasons, Double rating);

    // Using JPQL
    @Query("SELECT s FROM Series s WHERE s.totalSeasons <= :totalSeasons AND s.rating >= :rating")
    List<Series> seriesByTotalSeasonsAndRating(Integer totalSeasons, Double rating);
    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:episodeName%")
    List<Episode> episodesByName(String episodeName);
    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s = :series ORDER BY e.rating DESC LIMIT 5")
    List<Episode> top5EpisodesBySeries(Series series);
    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s = :series AND YEAR(e.releasedDate) >= :releasedYear")
    List<Episode> findEpisodesBySeriesAndYear(Series series, int releasedYear);
}
