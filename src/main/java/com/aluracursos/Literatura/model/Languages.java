package com.aluracursos.Literatura.model;

public enum Languages {

    EN("Inglés"),
    ES("Español"),
    FR("Francés");

    private String completeLanguage;

    Languages(String completeLanguage){
        this.completeLanguage = completeLanguage;
    }

    public static Languages fromString (String text){
        for (Languages languages : Languages.values()){
            if (languages.completeLanguage.equalsIgnoreCase(text)) {
                return languages;
            }
        }
        throw new IllegalArgumentException("No existe la categoria: " + text);
    }
}
