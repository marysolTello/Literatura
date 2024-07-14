package com.aluracursos.Literatura.dto;

import com.aluracursos.Literatura.model.Authors;

import java.util.List;

public record BookDTO(
        String title,
        List<String> languages,
        Double downloads,
        List<Authors> authorsList) {
}
