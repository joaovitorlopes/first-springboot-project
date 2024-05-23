package joaovitorlopes.com.github.screenmatch.repository;

import joaovitorlopes.com.github.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);
}
