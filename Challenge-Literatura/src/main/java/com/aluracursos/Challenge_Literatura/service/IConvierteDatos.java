package com.aluracursos.Challenge_Literatura.service;

// se puede tomar como un contrato o listas de promesas, comvierte un texto json en cualquier tipo de objeto que se le pida
public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
