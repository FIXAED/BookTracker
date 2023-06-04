package com.moc.booktracker.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "library")
public class Book {
    @PrimaryKey (autoGenerate = true)
    private Long id;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "author_id")
    private Long authorId;

    @ColumnInfo (name = "is_read")
    private Boolean is_read;

    @ColumnInfo (name = "genre")
    private String genre;

    @ColumnInfo (name = "isbn") // null
    private Long isbn;

    @ColumnInfo (name = "year_of_publication")
    private Integer year_of_publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public Integer getYear_of_publication() {
        return year_of_publication;
    }

    public void setYear_of_publication(Integer year_of_publication) {
        this.year_of_publication = year_of_publication;
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }
}
