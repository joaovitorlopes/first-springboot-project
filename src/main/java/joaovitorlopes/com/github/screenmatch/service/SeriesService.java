package joaovitorlopes.com.github.screenmatch.service;

import joaovitorlopes.com.github.screenmatch.dto.SeriesDTO;
import joaovitorlopes.com.github.screenmatch.model.Series;
import joaovitorlopes.com.github.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository repository;

    private List<SeriesDTO> convertData(List<Series> series) {
        return series.stream()
                .map(s -> new SeriesDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRating(), s.getGenre(), s.getActors(), s.getPlot(), s.getPoster()))
                .collect(Collectors.toList());
    }

    public List<SeriesDTO> getSeries() {
        return convertData(repository.findAll());
    }

    public List<SeriesDTO> getTop5Series() {
        return convertData(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> getReleases() {
        return convertData(repository.findEpisodesByRecentlyReleasedDate());
    }
}
