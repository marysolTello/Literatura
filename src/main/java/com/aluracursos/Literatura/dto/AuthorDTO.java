package com.aluracursos.Literatura.dto;

import com.aluracursos.Literatura.model.Books;

public record AuthorDTO(
        String name,
        String birth_year,
        String death_year,
        Books book
) {
}
