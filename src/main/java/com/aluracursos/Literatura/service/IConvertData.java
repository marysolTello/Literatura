package com.aluracursos.Literatura.service;

public interface IConvertData {
    <T> T getData (String json, Class<T> tClass);
}
