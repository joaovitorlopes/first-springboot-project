package joaovitorlopes.com.github.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate releasedData;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.episodeNumber();

        try {
            this.rating = Double.valueOf(episodeData.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        try {
            this.releasedData = LocalDate.parse(episodeData.released());
        } catch (DateTimeParseException e) {
            this.releasedData = null;
        }
    }

    public LocalDate getReleasedData() {
        return releasedData;
    }

    public void setReleasedData(LocalDate releasedData) {
        this.releasedData = releasedData;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "season=" + season +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", rating=" + rating +
                ", releasedData=" + releasedData;
    }
}