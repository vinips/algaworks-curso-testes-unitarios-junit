package com.algaworks.junit.utilidade;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalUtilsTest {

    //Le no formato CSV, dividido as colunas por virgula dentro das aspas
    // e as linhas por virgula fora das aspas
    @ParameterizedTest
    @CsvSource({
            "10.00,10",
            "9.00, 9.00"
    })
    public void iguais(BigDecimal x, BigDecimal y) {
        assertTrue(BigDecimalUtils.iguais(x, y));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/numeros.csv")
    public void diferentes(BigDecimal x, BigDecimal y) {
        assertFalse(BigDecimalUtils.iguais(x, y));
    }

}