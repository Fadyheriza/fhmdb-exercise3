package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Movie {
    private final String id;
    private final String title;
    private final String description;
    private final List<Genre> genres;
    private final int releaseYear;
    private final String imgUrl;
    private final int lengthInMinutes; // in minutes
    private final List<String> directors = new ArrayList<>();
    private final List<String> writers = new ArrayList<>();
    private final List<String> mainCast = new ArrayList<>();
    private double rating; // 0-10

    @Override
    public String toString() {
        return this.id;
    }

    public Movie(String title, String description, List<Genre> genres) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = 0;
        this.imgUrl = "";
        this.lengthInMinutes = 0;
        this.rating = 0;
    }
    public Movie(String id, String title, String description, List<Genre> genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        if(id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public Movie(MovieEntity movieEntity){
        this.id=movieEntity.getId();
        this.title=movieEntity.getTitle();
        this.description=movieEntity.getDescription();
        this.genres=stringToGenres(movieEntity.getGenres());
        this.releaseYear=movieEntity.getReleaseYear();
        this.imgUrl=movieEntity.getImgUrl();
        this.lengthInMinutes=movieEntity.getLengthInMinutes();
        this.rating=movieEntity.getRating();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }

    private List<Genre> stringToGenres(String string){
        List<String> list = List.of(string.substring(1,string.length()-1).split(", "));
        List<Genre> genres = new ArrayList<>();
        for(String s : list) genres.add(Genre.valueOf(s));
        return genres;
    }

    public MovieEntity toMovieEntity(){
        return new MovieEntity(this);
    }


}
