package com.aluracursos.Challenge_Literatura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
