package joaovitorlopes.com.github.screenmatch.controller;

import joaovitorlopes.com.github.screenmatch.dto.SeriesDTO;
import joaovitorlopes.com.github.screenmatch.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeriesController {

    @Autowired
    private SeriesService service;

    @GetMapping("/series")
    public List<SeriesDTO> getSeries() {
        return service.getSeries();
    }
}
