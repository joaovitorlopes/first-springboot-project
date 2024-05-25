package joaovitorlopes.com.github.screenmatch.service;

import joaovitorlopes.com.github.screenmatch.dto.SeriesDTO;
import joaovitorlopes.com.github.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository repository;

    public List<SeriesDTO> getSeries() {
        return repository.findAll()
                .stream()
                .map(s -> new SeriesDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRating(), s.getGenre(), s.getActors(), s.getPlot(), s.getPoster()))
                .collect(Collectors.toList());
    }
}
