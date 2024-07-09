package com.aluracursos.literalura.service;

public interface InterConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
