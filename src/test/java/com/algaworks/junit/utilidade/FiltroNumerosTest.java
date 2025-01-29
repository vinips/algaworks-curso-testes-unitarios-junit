package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FiltroNumerosTest {


    @Test
    public void testNumerosPares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosParesEsperados = Arrays.asList(2, 4);

        List<Integer> result = FiltroNumeros.numerosPares(numeros);

        assertIterableEquals(result, numerosParesEsperados);
//        assertEquals(result, numerosParesEsperados);

    }

}