package com.aluracursos.Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBooks(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DataAuthors> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Double downloads
) {
}
