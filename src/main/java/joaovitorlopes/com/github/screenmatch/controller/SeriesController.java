package joaovitorlopes.com.github.screenmatch.controller;

import joaovitorlopes.com.github.screenmatch.dto.SeriesDTO;
import joaovitorlopes.com.github.screenmatch.model.Series;
import joaovitorlopes.com.github.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeriesController {

    @Autowired
    private SeriesRepository repository;

    @GetMapping("/series")
    public List<SeriesDTO> getSeries() {
        return repository.findAll()
                .stream()
                .map(s -> new SeriesDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRating(), s.getGenre(), s.getActors(), s.getPlot(), s.getPoster()))
                .collect(Collectors.toList());
    }
}
