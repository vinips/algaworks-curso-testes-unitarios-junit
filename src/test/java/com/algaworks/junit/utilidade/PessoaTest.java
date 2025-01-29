package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaTest {

    @Test
    public void testPessoaConstrutor(){
        Pessoa p = new Pessoa("Vini", "Silveira");

        //Verifica tudo e depois mostra as falhas, caso tenha.
        assertAll("Asserções de Pessoa",
                () -> assertEquals("Vini", p.getNome()),
                () -> assertEquals("Silveira", p.getSobrenome()));
    }

}