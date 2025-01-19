package com.challenge.challenge_literalura.model;

public enum Idiomas {
    Español("es", "español"),
    Inglés("en", "inglés"),
    Francés("fr", "francés"),
    Portugués("pt", "portugués");

    private String idiomaGutendex;
    private String idiomaEspanol;

    Idiomas (String idiomaGutendex, String idiomaEspanol){
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idiomas fromString(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    public static Idiomas fromEspanol(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.idiomaEspanol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }


}
