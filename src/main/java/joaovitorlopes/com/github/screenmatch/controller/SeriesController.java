package joaovitorlopes.com.github.screenmatch.controller;

import joaovitorlopes.com.github.screenmatch.dto.EpisodeDTO;
import joaovitorlopes.com.github.screenmatch.dto.SeriesDTO;
import joaovitorlopes.com.github.screenmatch.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesService service;

    @GetMapping
    public List<SeriesDTO> getSeries() {
        return service.getSeries();
    }

    @GetMapping("/top5")
    public List<SeriesDTO> getTop5Series() {
        return service.getTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SeriesDTO> getReleases() {
        return service.getReleases();
    }

    @GetMapping("/{id}")
    public SeriesDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id) {
        return service.getAllSeasons(id);
    }

    @GetMapping("/{id}/temporadas/{seasonNumber}")
    public List<EpisodeDTO> getSeasonNumber(@PathVariable Long id, @PathVariable Long seasonNumber) {
        return service.getSeasonNumber(id, seasonNumber);
    }

    @GetMapping("/categoria/{categoryName}")
    public List<SeriesDTO> getSeriesByGenre(@PathVariable String categoryName) {
        return service.getSeriesByGenre(categoryName);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodeDTO> getTopEpisodes(@PathVariable Long id){
        return service.getTopEpisodesBySeries(id);
    }
}
