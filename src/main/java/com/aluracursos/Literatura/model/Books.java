package com.aluracursos.Literatura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private List<String> languages;
    private Double downloads;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authors> authorsList;

    public Books() {
    }

    public Books(DataBooks data) {
        this.title = data.title();
        this.downloads = data.downloads();
        this.languages = data.languages();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    public List<Authors> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Authors> authorsList) {
        authorsList.forEach(e-> e.setBook(this));
        this.authorsList = authorsList;
    }

    @Override
    public String toString() {
        return "Books{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", languages=" + languages +
                ", downloads=" + downloads +
                ", author=" + authorsList +
                '}';
    }
}
