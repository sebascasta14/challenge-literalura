package com.challenge.challenge_literalura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
