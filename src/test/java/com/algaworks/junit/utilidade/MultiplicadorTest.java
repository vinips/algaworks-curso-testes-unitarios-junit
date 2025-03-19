package com.algaworks.junit.utilidade;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicadorTest {

    @ParameterizedTest
    @EnumSource(value = Multiplicador.class, names = {"DOBRO", "TRIPLO"})
    void aplicarMultiplicador(Multiplicador multiplicador) {
        assertNotNull(multiplicador.aplicarMultiplicador((10.0)));
    }

    //Se não passar os names, ele faz com todos os valores do Enum
    @ParameterizedTest
    @EnumSource(value = Multiplicador.class)
    void aplicarMultiplicadorTodos(Multiplicador multiplicador) {
        assertNotNull(multiplicador.aplicarMultiplicador((10.0)));
    }
}