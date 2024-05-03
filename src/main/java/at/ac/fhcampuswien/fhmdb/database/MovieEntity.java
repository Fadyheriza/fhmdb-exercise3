package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "movies")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String genres; // Genres as a comma-separated string

    @DatabaseField
    private int releaseYear;

    @DatabaseField
    private String imgUrl;

    @DatabaseField
    private int lengthInMinutes;

    // ORMLite needs a no-arg constructor
    public MovieEntity() {
    }

    // Constructor with all fields
    public MovieEntity(String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genres='" + genres + '\'' +
                ", releaseYear=" + releaseYear +
                ", imgUrl='" + imgUrl + '\'' +
                ", lengthInMinutes=" + lengthInMinutes +
                '}';
    }
}
