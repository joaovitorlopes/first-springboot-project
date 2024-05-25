package joaovitorlopes.com.github.screenmatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeriesController {

    @GetMapping("/series")
    public String getSeries() {
        return "Here are listed the series";
    }
}
